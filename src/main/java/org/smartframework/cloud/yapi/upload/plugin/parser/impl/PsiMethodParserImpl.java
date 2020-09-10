package org.smartframework.cloud.yapi.upload.plugin.parser.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.api.ApiResolver;
import org.smartframework.cloud.yapi.upload.plugin.api.impl.ApiResolverImpl;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.parser.PsiMethodParser;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;
import org.jetbrains.annotations.NotNull;

public class PsiMethodParserImpl implements PsiMethodParser {

    private final ApiResolver apiResolver;

    public PsiMethodParserImpl(YApiProjectProperty property, Project project) {
        apiResolver = new ApiResolverImpl(property, project);
    }

    @Override
    public YApiParam parse(@NotNull PsiClass c, @NotNull PsiMethod m) {
        YApiParam param = new YApiParam();
        apiResolver.resolve(c, m, param);
        return param;
    }
}
