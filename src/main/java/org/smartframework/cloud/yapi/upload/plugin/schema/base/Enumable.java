package org.smartframework.cloud.yapi.upload.plugin.schema.base;

import java.util.List;

@SuppressWarnings("unused")
public interface Enumable {

    List<String> getEnum();

    String getEnumDesc();
}
