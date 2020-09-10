package org.smartframework.cloud.yapi.upload.plugin.req;

import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

public interface RequestResolver {

    void resolve(@NotNull PsiMethod m, @NotNull YApiParam target);
}
