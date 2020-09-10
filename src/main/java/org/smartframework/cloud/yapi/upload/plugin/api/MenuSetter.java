package org.smartframework.cloud.yapi.upload.plugin.api;

import com.intellij.psi.javadoc.PsiDocComment;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

public interface MenuSetter {

    void set(@NotNull PsiDocComment docComment, @NotNull YApiParam target);

}
