package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiPathVariable;
import org.smartframework.cloud.yapi.upload.plugin.req.PsiParamFilter;
import org.smartframework.cloud.yapi.upload.plugin.req.SimpleRequestParamResolver;
import org.smartframework.cloud.yapi.upload.plugin.util.DesUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class RequestPathVariableResolverImpl implements SimpleRequestParamResolver {

    @NotNull
    @Override
    public PsiParamFilter getPsiParamFilter(@NotNull PsiMethod m,
                                            @NotNull YApiParam target) {
        return (psiParameter -> PsiAnnotationUtils
                .isAnnotatedWith(psiParameter, SpringMVCConstants.PathVariable));
    }

    @Override
    public void doResolverItem(@NotNull PsiMethod m, @NotNull PsiParameter param,
            @NotNull YApiParam target) {
        PsiAnnotation annotation = PsiAnnotationUtils
                .findAnnotation(param, SpringMVCConstants.PathVariable);
        if (Objects.nonNull(annotation)) {
            YApiPathVariable pathVariable = new YApiPathVariable();
            pathVariable.full(this.handleParamAnnotation(param, annotation));
            pathVariable.setDesc(DesUtils.getParamDesc(m, param.getName()));
            Set<YApiPathVariable> pathVariables = target.getReq_params();
            if (Objects.isNull(pathVariables)) {
                pathVariables = new HashSet<>();
                target.setReq_params(pathVariables);
            }
            pathVariables.add(pathVariable);
        }
    }

}
