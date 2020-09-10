package org.smartframework.cloud.yapi.upload.plugin.req;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface FilterableRequestParamResolver extends RequestParamResolver, PsiParamListFilter {

    default void doResolve(@NotNull PsiMethod m,
            @NotNull List<PsiParameter> parameterList,
            @NotNull YApiParam target) {
        parameterList.forEach(p -> this.doResolverItem(m, p, target));
    }

    default void doResolverItem(@NotNull PsiMethod m, @NotNull PsiParameter param,
            @NotNull YApiParam target) {

    }
}
