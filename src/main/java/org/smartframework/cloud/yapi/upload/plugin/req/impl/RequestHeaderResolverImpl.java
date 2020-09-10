package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiHeader;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.req.PsiParamFilter;
import org.smartframework.cloud.yapi.upload.plugin.req.SimpleRequestParamResolver;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RequestHeaderResolverImpl implements SimpleRequestParamResolver {

    private final PsiParamFilter psiParamFilter = (param) ->
            PsiAnnotationUtils.isAnnotatedWith(param, SpringMVCConstants.RequestHeader);

    @NotNull
    @Override
    public PsiParamFilter getPsiParamFilter(@NotNull PsiMethod m,
                                            @NotNull YApiParam target) {
        return this.psiParamFilter;
    }

    @Override
    public void doResolverItem(@NotNull PsiMethod m, @NotNull PsiParameter param,
                               @NotNull YApiParam target) {
        PsiAnnotation annotation = PsiAnnotationUtils
                .findAnnotation(param, SpringMVCConstants.RequestHeader);
        if (Objects.nonNull(annotation)) {
            YApiHeader header = new YApiHeader();
            header.full(this.handleParamAnnotation(param, annotation));
            Set<YApiHeader> headers = target.getHeaders();
            if (Objects.isNull(headers)) {
                headers = new HashSet<>();
                target.setHeaders(headers);
            }
            headers.add(header);
        }
    }

}