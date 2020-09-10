package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.search.GlobalSearchScope;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.constant.TypeConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.ValueWrapper;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiForm;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.req.PsiParamFilter;
import org.smartframework.cloud.yapi.upload.plugin.req.SimpleRequestBodyParamResolver;
import org.smartframework.cloud.yapi.upload.plugin.support.YApiSupportHolder;
import org.smartframework.cloud.yapi.upload.plugin.util.DesUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.JsonPropertyUtil;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.ValidUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class RequestFormResolverImpl implements SimpleRequestBodyParamResolver {

    private final Project project;

    public RequestFormResolverImpl(Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public PsiParamFilter getPsiParamFilter(@NotNull PsiMethod m,
                                            @NotNull YApiParam target) {
        PsiParameter[] parameters = m.getParameterList().getParameters();
        if (this.noBody(target) || this.hasRequestBody(parameters)) {
            return p -> false;
        }
        return p -> true;
    }

    @Override
    public void doResolverItem(@NotNull PsiMethod m, @NotNull PsiParameter param,
            @NotNull YApiParam target) {
        Set<YApiForm> requestForm = new LinkedHashSet<>();
        String paramName = param.getName();
        String typeName = param.getType().getPresentableText();
        String required = ValidUtils.notNullOrBlank(param) ? "1" : "0";
        String typeClassName = param.getType().getCanonicalText();
        if (typeClassName.endsWith("[]")) {
            typeClassName = typeClassName.replace("[]", "");
        }
        //如果是基本类型或者文件
        String remark =
                DesUtils.getParamDesc(m, paramName) + "(" + param.getType().getPresentableText()
                        + ")";
        if (TypeConstants.isNormalType(typeName) || SpringMVCConstants.MultipartFile
                .equals(typeClassName)) {
            PsiAnnotation psiAnnotation = PsiAnnotationUtils
                    .findAnnotation(param, SpringMVCConstants.RequestParam);
            YApiForm form = new YApiForm();
            //如果参数是文件类型
            if (SpringMVCConstants.MultipartFile.equals(typeClassName)) {
                form.setType("file");
            }
            form.setDesc(remark);
            if (psiAnnotation == null) {//没有@RequestParam注解
                form.setName(paramName);
                form.setRequired(required);
                form.setExample(
                        TypeConstants.normalTypes.get(typeName).toString());
            } else {//处理@RequestParam注解
                ValueWrapper valueWrapper = handleParamAnnotation(param, psiAnnotation);
                form.full(valueWrapper);
            }
            YApiSupportHolder.supports.handleParam(param, form);
            requestForm.add(form);
        } else {//非基本类型
            PsiClass psiClass = JavaPsiFacade.getInstance(project)
                    .findClass(typeClassName, GlobalSearchScope.allScope(project));
            for (PsiField field : Objects.requireNonNull(psiClass).getAllFields()) {
                if (
                        Objects.requireNonNull(field.getModifierList())
                                .hasModifierProperty(PsiModifier.STATIC)) {
                    continue;
                }
                String fieldType = field.getType().getCanonicalText();
                YApiForm form = new YApiForm();
                form.setRequired(ValidUtils.notNullOrBlank(field) ? "1" : "0");
                form.setType(SpringMVCConstants.MultipartFile.equals(fieldType) ? "file" : "text");
//                form.setName(field.getName());
                form.setName(JsonPropertyUtil.getName(field));
                form.setDesc(DesUtils.getLinkRemark(field, project));
                Object obj = TypeConstants.normalTypes
                        .get(field.getType().getPresentableText());
                if (Objects.nonNull(obj)) {
                    form.setExample(
                            TypeConstants.normalTypes.get(field.getType().getPresentableText())
                                    .toString());
                }
                YApiSupportHolder.supports.handleField(field, form);
                requestForm.add(form);
            }
        }
        Set<YApiForm> apiForms = target.getReq_body_form();
        if (Objects.isNull(apiForms)) {
            apiForms = new HashSet<>();
            target.setReq_body_form(apiForms);
        }
        apiForms.addAll(requestForm);
    }
}
