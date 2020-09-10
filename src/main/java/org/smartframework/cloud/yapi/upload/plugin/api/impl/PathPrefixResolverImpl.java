package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.javadoc.PsiDocComment;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.api.PathPrefixResolver;
import org.smartframework.cloud.yapi.upload.plugin.constant.DocCommentConstants;
import org.smartframework.cloud.yapi.upload.plugin.util.PathUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiDocUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class PathPrefixResolverImpl implements PathPrefixResolver {

    private final static String PATH_ROOT = "/";

    @Override
    public String resolve(@Nullable PsiDocComment c, @Nullable PsiDocComment m) {
        final List<String> prefixes = new ArrayList<>();
        if (Objects.nonNull(c)) {
            String prefix = PsiDocUtils.getTagValueByName(c, DocCommentConstants.TAG_PREFIX);
            if (Strings.isNotBlank(prefix) && !PATH_ROOT.equals(prefix)) {
                prefixes.add(PathUtils.pathFormat(prefix));
            }
        }
        if (Objects.nonNull(m)) {
            String prefix = PsiDocUtils.getTagValueByName(m, DocCommentConstants.TAG_PREFIX);
            if (Strings.isNotBlank(prefix) && !PATH_ROOT.equals(prefix)) {
                prefixes.add(PathUtils.pathFormat(prefix));
            }
        }
        return prefixes.isEmpty() ? null : String.join("", prefixes);
    }

}
