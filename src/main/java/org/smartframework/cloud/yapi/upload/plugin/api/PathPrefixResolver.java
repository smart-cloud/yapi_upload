package org.smartframework.cloud.yapi.upload.plugin.api;

import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.Nullable;

public interface PathPrefixResolver {

    String resolve(@Nullable PsiDocComment c, @Nullable PsiDocComment m);

}
