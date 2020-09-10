package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.req.RequestParamResolver;
import org.smartframework.cloud.yapi.upload.plugin.req.RequestResolver;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class RequestResolverImpl implements RequestResolver {

    private final List<RequestParamResolver> paramResolvers;

    public RequestResolverImpl(YApiProjectProperty property, Project project) {
        RequestParamResolver queryResolver = new RequestQueryResolverImpl(project);
        RequestParamResolver bodyResolver = new RequestBodyResolverImpl(property, project);
        RequestParamResolver headerResolver = new RequestHeaderResolverImpl();
        RequestParamResolver pathVariableResolver = new RequestPathVariableResolverImpl();
        RequestParamResolver formResolver = new RequestFormResolverImpl(project);
        paramResolvers = Arrays.asList(pathVariableResolver, formResolver, queryResolver,
                bodyResolver, headerResolver);
    }

    @Override
    public void resolve(@NotNull PsiMethod m, @NotNull YApiParam target) {
        this.paramResolvers.forEach(r -> r.resolve(m, target));
    }

}
