package org.smartframework.cloud.yapi.upload.plugin.support;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.smartframework.cloud.yapi.upload.plugin.model.ValueWrapper;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;

public interface YApiSupport {

    default boolean isImportant() {
        return false;
    }

    default int getOrder() {
        return 0;
    }

    void handleMethod(PsiMethod psiMethod, YApiParam apiDTO);

    void handleParam(PsiParameter psiParameter, ValueWrapper wrapper);

    void handleField(PsiField psiField, ValueWrapper wrapper);
}
