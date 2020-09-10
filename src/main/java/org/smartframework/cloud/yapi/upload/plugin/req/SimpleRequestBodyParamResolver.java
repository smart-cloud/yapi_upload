package org.smartframework.cloud.yapi.upload.plugin.req;

import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

public interface SimpleRequestBodyParamResolver extends
        PsiParamListWithBodyFilter, RequestParamResolver,
        FilterableRequestParamResolver {

    @Override
    default void resolve(@NotNull PsiMethod m, @NotNull YApiParam target) {
        this.doResolve(m, this.filter(m, target), target);
    }

}
