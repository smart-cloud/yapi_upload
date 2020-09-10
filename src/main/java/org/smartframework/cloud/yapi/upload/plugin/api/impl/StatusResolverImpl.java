package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.javadoc.PsiDocComment;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.api.StatusResolver;
import org.smartframework.cloud.yapi.upload.plugin.constant.DocCommentConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiStatus;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiDocUtils;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StatusResolverImpl implements StatusResolver {

    @Override
    public void resolve(@Nullable PsiDocComment classDoc, @Nullable PsiDocComment methodDoc,
            @NotNull YApiParam target) {
        String status = YApiStatus.undone.name();
        if (Objects.nonNull(classDoc)) {
            String value = PsiDocUtils.getTagValueByName(classDoc, DocCommentConstants.TAG_STATUS);
            if (Strings.isNotBlank(value)) {
                status = value;
            }
        }
        if (Objects.nonNull(methodDoc)) {
            String value = PsiDocUtils.getTagValueByName(methodDoc, DocCommentConstants.TAG_STATUS);
            if (Strings.isNotBlank(value)) {
                status = value;
            }
        }
        target.setStatus(status);
    }
}
