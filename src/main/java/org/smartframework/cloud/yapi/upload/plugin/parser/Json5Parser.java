package org.smartframework.cloud.yapi.upload.plugin.parser;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import org.smartframework.cloud.yapi.upload.plugin.json5.Json;
import org.smartframework.cloud.yapi.upload.plugin.json5.JsonArray;
import org.smartframework.cloud.yapi.upload.plugin.json5.JsonObject;

public interface Json5Parser extends ObjectParser {

    JsonArray<?> getJsonArray(String typePkName);

    JsonObject getJsonObject(String typePkName);

    Json<?> getJson(PsiType psiType);

    Json<?> getOtherJson(PsiType psiType);

    Json<?> getJsonByField(PsiField psiField);
}
