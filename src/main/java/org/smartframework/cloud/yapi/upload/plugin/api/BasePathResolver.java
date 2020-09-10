package org.smartframework.cloud.yapi.upload.plugin.api;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifierListOwner;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

public interface BasePathResolver {

    void resolve(@NotNull PsiModifierListOwner psiModifierListOwner, @NotNull YApiParam target);

    String getPathByAnnotation(@NotNull PsiAnnotation psiAnnotation);

}
