package org.smartframework.cloud.yapi.upload.plugin.api.abs;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import org.smartframework.cloud.yapi.upload.plugin.api.BasePathResolver;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPathResolver implements BasePathResolver {

    @Override
    public String getPathByAnnotation(@NotNull PsiAnnotation psiAnnotation) {
        PsiAnnotationMemberValue element = psiAnnotation.findAttributeValue("path");
        if (element == null) {
            return "";
        }
        String value = element.getText();
        if ("{}".equals(value)) {
            value = Objects.requireNonNull(psiAnnotation.findAttributeValue("value")).getText();
        }
        return "{}".equals(value) ? "" : value.replace("\"", "");
    }

}
