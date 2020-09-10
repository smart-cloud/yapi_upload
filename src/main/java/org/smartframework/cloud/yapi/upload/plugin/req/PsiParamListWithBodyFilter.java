package org.smartframework.cloud.yapi.upload.plugin.req;

import com.intellij.psi.PsiParameter;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiParamUtils;
import org.jetbrains.annotations.NotNull;

public interface PsiParamListWithBodyFilter extends PsiParamListFilter {

    default boolean hasRequestBody(PsiParameter[] psiParameters) {
        return PsiParamUtils.hasRequestBody(psiParameters);
    }

    default boolean noBody(@NotNull YApiParam target) {
        return PsiParamUtils.noBody(target.getMethod());
    }
}
