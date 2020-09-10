package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.parser.ObjectParser;
import org.smartframework.cloud.yapi.upload.plugin.req.PsiParamFilter;
import org.smartframework.cloud.yapi.upload.plugin.req.SimpleRequestBodyParamResolver;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;
import org.smartframework.cloud.yapi.upload.plugin.parser.impl.Json5ParserImpl;
import org.smartframework.cloud.yapi.upload.plugin.parser.impl.JsonSchemaParserImpl;
import org.jetbrains.annotations.NotNull;

public class RequestBodyResolverImpl implements SimpleRequestBodyParamResolver {

    private final ObjectParser objectParser;

    private final int dataMode;

    public RequestBodyResolverImpl(YApiProjectProperty property, Project project) {
        this.dataMode = property.getDataMode();
        if (this.dataMode == 0) {
            this.objectParser = new JsonSchemaParserImpl(property, project);
        } else {
            this.objectParser = new Json5ParserImpl(property, project);
        }
    }

    @NotNull
    @Override
    public PsiParamFilter getPsiParamFilter(@NotNull PsiMethod m,
                                            @NotNull YApiParam target) {
        return this.hasRequestBody(m.getParameterList().getParameters()) ? p -> PsiAnnotationUtils
                .isAnnotatedWith(p, SpringMVCConstants.RequestBody)
                : p -> false;
    }

    @Override
    public void doResolverItem(@NotNull PsiMethod m, @NotNull PsiParameter param,
            @NotNull YApiParam target) {
        target.setRequestBody(this.objectParser.getJsonResponse(param.getType()));
        target.setReq_body_is_json_schema(this.dataMode == 0);
    }

}
