package org.smartframework.cloud.yapi.upload.plugin.config.impl;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.smartframework.cloud.yapi.upload.plugin.config.ProjectConfigurationReader;
import org.smartframework.cloud.yapi.upload.plugin.config.YApiProjectPersistentState;
import org.smartframework.cloud.yapi.upload.plugin.constant.NotificationConstants;
import org.smartframework.cloud.yapi.upload.plugin.constant.YApiConstants;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiPropertyConvertHolder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.jdom.Element;

public class ProjectConfigReader {

    private ProjectConfigReader() {
    }

    private final static ProjectConfigurationReader<YApiProjectProperty> reader = (project -> {
        YApiProjectPersistentState projectState = ServiceManager
                .getService(Objects.requireNonNull(project), YApiProjectPersistentState.class);
        Element element = projectState.getState();
        if (element == null) {
            YApiProjectProperty property = new YApiProjectProperty();
            try {
                String projectConfig = new String(
                        Objects.requireNonNull(project.getProjectFile()).contentsToByteArray(),
                        StandardCharsets.UTF_8);
                if (projectConfig.contains("yapi")) {
                    NotificationConstants.NOTIFICATION_GROUP
                            .createNotification(YApiConstants.name, "配置信息更新",
                                    "读取到旧版本的配置信息，配置信息已经更新", NotificationType.INFORMATION)
                            .notify(project);
                    property.setToken(projectConfig.split("projectToken\">")[1].split("</")[0]);
                    property.setProjectId(
                            Integer.parseInt(
                                    projectConfig.split("projectId\">")[1].split("</")[0]));
                    property.setUrl(projectConfig.split("yapiUrl\">")[1].split("</")[0]);
                    property.setEnableBasicScope(projectConfig.contains("basicScope\">") && "true"
                            .equals(projectConfig.split("basicScope\">")[1].split("</")[0]
                                    .toLowerCase()));
                }
            } catch (Exception ignored) {
            }
            projectState.loadState(YApiPropertyConvertHolder.getConvert().serialize(property));
            return property;
        } else {
            return YApiPropertyConvertHolder.getConvert().deserialize(element);
        }
    });

    public static YApiProjectProperty read(Project project) {
        return reader.read(project);
    }

}
