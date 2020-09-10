package org.smartframework.cloud.yapi.upload.plugin.config.impl;

import org.smartframework.cloud.yapi.upload.plugin.config.ConfigurationReader;
import org.smartframework.cloud.yapi.upload.plugin.config.YApiApplicationPersistentState;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiApplicationProperty;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiPropertyConvertHolder;
import org.jdom.Element;

public class ApplicationConfigReader {

    private ApplicationConfigReader() {
    }

    private final static ConfigurationReader<YApiApplicationProperty> reader = () -> {
        Element element = YApiApplicationPersistentState.getInstance().getState();
        YApiApplicationProperty property = null;
        if (element != null) {
            property = YApiPropertyConvertHolder.getApplicationConvert().deserialize(element);
        }
        return property;
    };

    public static YApiApplicationProperty read() {
        return reader.read();
    }
}
