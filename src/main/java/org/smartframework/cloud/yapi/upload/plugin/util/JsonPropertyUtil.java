package org.smartframework.cloud.yapi.upload.plugin.util;

import com.intellij.psi.PsiField;

/**
 * @author collin
 * @date 2020-07-16
 */
public interface JsonPropertyUtil {

    /**
     * 处理JsonProperty注解
     *
     * @param field
     * @return
     */
    static String getName(PsiField field) {
        String fieldName = PsiAnnotationUtils
                .getPsiParameterAnnotationValue(field, "com.fasterxml.jackson.annotation.JsonProperty");
        if (fieldName != null && fieldName.length() > 0) {
            fieldName = fieldName.replaceAll("\\\"", "");
            return fieldName;
        }
        return field.getName();
    }

    /**
     * 处理JsonProperty注解
     *
     * @param field
     * @return
     */
    static String getName(PsiField field, String name) {
        String fieldName = PsiAnnotationUtils
                .getPsiParameterAnnotationValue(field, "com.fasterxml.jackson.annotation.JsonProperty");
        if (fieldName != null && fieldName.length() > 0) {
            fieldName = fieldName.replaceAll("\\\"", "");
            return fieldName;
        }
        return name;
    }

}