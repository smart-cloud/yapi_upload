package org.smartframework.cloud.yapi.upload.plugin.base.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import org.smartframework.cloud.yapi.upload.plugin.base.DeprecatedAssert;
import org.smartframework.cloud.yapi.upload.plugin.constant.DocCommentConstants;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiDocUtils;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class DeprecatedAssertImpl implements DeprecatedAssert {

    @Override
    public boolean isDeprecated(@NotNull PsiClass c) {
        PsiDocComment classDoc;
        return PsiAnnotationUtils.hasDeprecated(c)
                || (Objects.nonNull(classDoc = c.getDocComment()) &&
                PsiDocUtils.hasTag(classDoc, DocCommentConstants.TAG_DEPRECATED));
    }

    @Override
    public boolean isDeprecated(@NotNull PsiMethod m) {
        PsiDocComment methodDoc;
        return PsiAnnotationUtils.hasDeprecated(m)
                || (Objects.nonNull(methodDoc = m.getDocComment()) &&
                PsiDocUtils.hasTag(methodDoc, DocCommentConstants.TAG_DEPRECATED));
    }

    @Override
    public boolean isDeprecated(@NotNull PsiClass c, @NotNull PsiMethod m) {
        return isDeprecated(c) || isDeprecated(m);
    }
}
