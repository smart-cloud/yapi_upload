package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.smartframework.cloud.yapi.upload.plugin.constant.SmartCloudConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiHeader;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.req.RequestResolver;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 处理header参数
 *
 * @author liyulin
 * @date 2020-08-11
 */
public class SmartCloudRequestHeaderResolver implements RequestResolver {

    /**
     * SmartApiAC注解属性tokenCheck
     */
    private static final String TOKENCHECK = "tokenCheck";

    @Override
    public void resolve(@NotNull PsiMethod m, @NotNull YApiParam target) {
        // @martApiAC注解
        PsiAnnotation psiAnnotation = PsiAnnotationUtils.findAnnotation(m, SmartCloudConstants.SmartApiAc);
        if (psiAnnotation != null) {
            fillToken(psiAnnotation, target);
        }
    }

    /**
     * 处理smart-token
     *
     * @param psiAnnotation
     * @param target
     */
    private void fillToken(PsiAnnotation psiAnnotation, YApiParam target) {
        PsiAnnotationMemberValue psiAnnotationMemberValue = psiAnnotation.findAttributeValue(TOKENCHECK);
        String tokenCheck = psiAnnotationMemberValue.getText();
        if (Boolean.parseBoolean(tokenCheck)) {
            YApiHeader header = new YApiHeader();
            header.setName(HttpHeaderName.TOKEN.getName());
            header.setRequired("1");
            header.setDesc("请求token");

            Set<YApiHeader> headers = target.getHeaders();
            if (Objects.isNull(headers)) {
                headers = new HashSet<>();
                target.setHeaders(headers);
            }
            headers.add(header);
        }
    }

    enum HttpHeaderName {
        TOKEN("smart-token");

        private HttpHeaderName(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }

}