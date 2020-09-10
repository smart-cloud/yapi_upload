package org.smartframework.cloud.yapi.upload.plugin.schema;

import org.smartframework.cloud.yapi.upload.plugin.schema.base.ItemJsonSchema;
import org.smartframework.cloud.yapi.upload.plugin.schema.base.SchemaType;

public final class SchemaHelper {

    public static ItemJsonSchema parse(SchemaType type) {
        switch (type) {
            case integer:
                return new NumberSchema();
            case number:
                return new IntegerSchema();
            case object:
                return new ObjectSchema();
            case array:
                return new ArraySchema();
            case bool:
                return new BooleanSchema();
            default:
                return new StringSchema();
        }
    }
}
