package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.api.HttpMethodResolver;
import org.smartframework.cloud.yapi.upload.plugin.constant.HttpMethodConstants;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.jetbrains.annotations.NotNull;

public class HttpMethodResolverImpl implements HttpMethodResolver {

    @Override
    public void resolve(@NotNull PsiMethod method, @NotNull YApiParam target) {
        String httpMethod = null;
        //获取方法上的RequestMapping注解
        PsiAnnotation annotation = PsiAnnotationUtils
                .findAnnotation(method, SpringMVCConstants.RequestMapping);
        if (annotation != null) {
            PsiAnnotationMemberValue m = annotation.findAttributeValue("method");
            if (m != null) {
                httpMethod = m.getText().toUpperCase();
            }
        } else if (PsiAnnotationUtils
                .isAnnotatedWith(method, SpringMVCConstants.GetMapping)) {
            httpMethod = HttpMethodConstants.GET;
        } else if (PsiAnnotationUtils
                .isAnnotatedWith(method, SpringMVCConstants.PostMapping)) {
            httpMethod = HttpMethodConstants.POST;
        } else if (PsiAnnotationUtils
                .isAnnotatedWith(method, SpringMVCConstants.PutMapping)) {
            httpMethod = HttpMethodConstants.PUT;
        } else if (PsiAnnotationUtils
                .isAnnotatedWith(method, SpringMVCConstants.DeleteMapping)) {
            httpMethod = HttpMethodConstants.DELETE;
        } else if (PsiAnnotationUtils
                .isAnnotatedWith(method, SpringMVCConstants.PatchMapping)) {
            httpMethod = HttpMethodConstants.PATCH;
        }
        target.setMethod(httpMethod);
    }
}
