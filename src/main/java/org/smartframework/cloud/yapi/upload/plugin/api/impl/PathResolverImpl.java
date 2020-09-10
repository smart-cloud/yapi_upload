package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.api.BasePathResolver;
import org.smartframework.cloud.yapi.upload.plugin.api.PathPrefixResolver;
import org.smartframework.cloud.yapi.upload.plugin.api.PathResolver;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class PathResolverImpl implements PathResolver {

    private final PathPrefixResolver pathPrefixResolver = new PathPrefixResolverImpl();
    private final BasePathResolver clzPathResolver = new ClassPathResolverImpl();
    private final BasePathResolver methodPathResolver = new MethodPathResolverImpl();

    @Override
    public void resolve(@NotNull PsiClass c, @NotNull PsiMethod m, @NotNull YApiParam target) {
        PsiDocComment classDoc = c.getDocComment();
        PsiDocComment methodDoc = m.getDocComment();
        clzPathResolver.resolve(c, target);
        methodPathResolver.resolve(m, target);
        String prefix = pathPrefixResolver.resolve(classDoc, methodDoc);
        if (Objects.nonNull(prefix)) {
            String path = target.getPath();
            if (Strings.isNotBlank(path)) {
                path = prefix + path;
            } else {
                path = prefix;
            }
            target.setPath(path);
        }
    }

}
