package org.smartframework.cloud.yapi.upload.plugin.parser;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.base.DeprecatedAssert;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiUtils;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;
import org.smartframework.cloud.yapi.upload.plugin.base.impl.DeprecatedAssertImpl;
import org.smartframework.cloud.yapi.upload.plugin.constant.NotificationConstants;
import org.smartframework.cloud.yapi.upload.plugin.constant.YApiConstants;
import org.smartframework.cloud.yapi.upload.plugin.parser.impl.PsiClassParserImpl;
import org.smartframework.cloud.yapi.upload.plugin.parser.impl.PsiMethodParserImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author aqiu
 * @date 2019-06-15 11:46
 * @description 接口信息解析
 **/
public class YApiParser {

    private final DeprecatedAssert deprecatedAssert = new DeprecatedAssertImpl();

    private final YApiProjectProperty property;
    private final Project project;

    public YApiParser(YApiProjectProperty property, Project project) {
        this.property = property;
        this.project = project;
    }

    public Set<YApiParam> parse(AnActionEvent e) {
        String selectedText = PsiUtils.getSelectedText(e);
        PsiClass selectedClass = PsiUtils.currentClass(e);
        //获取该类是否已经过时
        if (Objects.isNull(selectedClass) || deprecatedAssert.isDeprecated(selectedClass)) {
            NotificationConstants.NOTIFICATION_GROUP
                    .createNotification(YApiConstants.name, "该类已过时",
                            "该类(或注释中)含有@Deprecated注解，如需上传，请删除该注解", NotificationType.WARNING)
                    .notify(project);
            return null;
        }
        Set<YApiParam> yApiParams = new HashSet<>();
        if (Strings.isBlank(selectedText) || selectedText.equals(selectedClass.getName())) {
            List<YApiParam> params = new PsiClassParserImpl(property, project).parse(selectedClass);
            yApiParams.addAll(params);
        } else {//如果用户选中的是方法
            PsiMethod[] psiMethods = selectedClass.getMethods();
            PsiMethod psiMethodTarget = null;
            for (PsiMethod psiMethod : psiMethods) {
                if (psiMethod.getName().equals(selectedText)) {
                    psiMethodTarget = psiMethod;
                    break;
                }
            }
            if (Objects.nonNull(psiMethodTarget)) {
                //带有 @Deprecated 注解的方法跳过
                if (deprecatedAssert.isDeprecated(psiMethodTarget)) {
                    NotificationConstants.NOTIFICATION_GROUP
                            .createNotification(YApiConstants.name, "接口已过时",
                                    "该方法或者类(或注释中)含有@Deprecated注解，如需上传，请删除该注解:" + selectedText,
                                    NotificationType.WARNING).notify(project);
                }
                try {
                    YApiParam param = new PsiMethodParserImpl(property, project)
                            .parse(selectedClass, psiMethodTarget);
                    yApiParams.add(param);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    NotificationConstants.NOTIFICATION_GROUP
                            .createNotification(YApiConstants.name, "接口信息解析失败",
                                    "失败原因：" + ex.getMessage(),
                                    NotificationType.ERROR).notify(project);
                }
            }
        }
        return yApiParams;
    }

}
