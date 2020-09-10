package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.api.BaseInfoSetter;
import org.smartframework.cloud.yapi.upload.plugin.constant.DocCommentConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.support.YApiSupportHolder;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiDocUtils;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class BaseInfoSetterImpl implements BaseInfoSetter {

    @Override
    public void set(@NotNull PsiClass c, @NotNull PsiMethod m, @NotNull YApiParam target) {
        String classDesc = m.getText().replace(
                Objects.nonNull(m.getBody()) ? m.getBody().getText()
                        : "", "");
        if (!Strings.isEmpty(classDesc)) {
            classDesc = classDesc.replace("<", "&lt;").replace(">", "&gt;");
        }
        target.setDesc("<pre><code>" + classDesc + "</code></pre>");
        PsiDocComment docComment = m.getDocComment();
        if (Objects.nonNull(docComment)) {
            String title = PsiDocUtils.getTagDescription(docComment);
            if (Strings.isBlank(title)) {
                title = PsiDocUtils.getTagValueByName(docComment, DocCommentConstants.TAG_DESCRIPTION);
            }
            target.setTitle(title);
        }
        YApiSupportHolder.supports.handleMethod(m, target);
    }
}
