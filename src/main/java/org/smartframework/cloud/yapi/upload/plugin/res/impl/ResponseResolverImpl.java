package org.smartframework.cloud.yapi.upload.plugin.res.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiType;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.parser.ObjectParser;
import org.smartframework.cloud.yapi.upload.plugin.parser.impl.Json5ParserImpl;
import org.smartframework.cloud.yapi.upload.plugin.parser.impl.JsonSchemaParserImpl;
import org.smartframework.cloud.yapi.upload.plugin.res.ResponseResolver;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResponseResolverImpl implements ResponseResolver {

    private final YApiProjectProperty property;
    private final ObjectParser objectParser;

    public ResponseResolverImpl(YApiProjectProperty property, Project project) {
        this.property = property;
        if (property.getDataMode() == 0) {
            this.objectParser = new JsonSchemaParserImpl(property, project);
        } else {
            this.objectParser = new Json5ParserImpl(property, project);
        }
    }

    @Override
    public void resolve(@Nullable PsiType returnType, @NotNull YApiParam target) {
        if (Objects.isNull(returnType)) {
            return;
        }
        int dataMode = property.getDataMode();
        boolean isJsonSchema = dataMode == 0;
        target.setRes_body_is_json_schema(isJsonSchema);
        if ("raw".equals(target.getRes_body_type())) {
            target.setResponse(objectParser.getRawResponse(returnType));
        } else {
            target.setResponse(objectParser.getJsonResponse(returnType));
        }
    }
}
