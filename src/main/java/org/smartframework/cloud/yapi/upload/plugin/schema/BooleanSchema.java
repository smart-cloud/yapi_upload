package org.smartframework.cloud.yapi.upload.plugin.schema;

import org.smartframework.cloud.yapi.upload.plugin.schema.base.ItemJsonSchema;
import org.smartframework.cloud.yapi.upload.plugin.schema.base.SchemaType;

public final class BooleanSchema extends ItemJsonSchema {

    public BooleanSchema() {
        super(SchemaType.bool);
    }
}
