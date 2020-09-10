package org.smartframework.cloud.yapi.upload.plugin.res;

import com.intellij.psi.PsiType;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResponseResolver {

    void resolve(@Nullable PsiType returnType, @NotNull YApiParam target);
}
