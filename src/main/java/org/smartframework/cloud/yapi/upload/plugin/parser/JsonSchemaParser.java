package org.smartframework.cloud.yapi.upload.plugin.parser;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import org.smartframework.cloud.yapi.upload.plugin.schema.ArraySchema;
import org.smartframework.cloud.yapi.upload.plugin.schema.base.ItemJsonSchema;
import org.smartframework.cloud.yapi.upload.plugin.schema.base.SchemaType;

public interface JsonSchemaParser extends ObjectParser {

    ItemJsonSchema getPojoSchema(String typePkName);

    ItemJsonSchema getOtherTypeSchema(PsiType psiType);

    ArraySchema getArraySchema(String typePkName);

    ItemJsonSchema getOtherFieldSchema(PsiField psiField);

    ItemJsonSchema getBaseFieldSchema(SchemaType schemaType, PsiField psiField);

    ItemJsonSchema getFieldSchema(PsiField psiField);

}
