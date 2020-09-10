package org.smartframework.cloud.yapi.upload.plugin.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiApplicationProperty;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiPropertyConvertHolder;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiPropertyXmlConvert;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

@State(name = "ApplicationRedsoftYApiUpload", storages = @Storage("redsoft-yapi.xml"))
public class YApiApplicationPersistentState implements PersistentStateComponent<Element> {

    private final YApiPropertyXmlConvert<YApiApplicationProperty> convert = YApiPropertyConvertHolder
            .getApplicationConvert();
    private YApiApplicationProperty yApiApplicationProperty;

    YApiApplicationPersistentState() {
    }

    public static YApiApplicationPersistentState getInstance() {
        return ServiceManager.getService(YApiApplicationPersistentState.class);
    }

    @Override
    public Element getState() {
        return yApiApplicationProperty == null ? null
                : this.convert.serialize(yApiApplicationProperty);
    }

    @Override
    public void loadState(@NotNull Element element) {
        this.yApiApplicationProperty = this.convert.deserialize(element);
    }

}
