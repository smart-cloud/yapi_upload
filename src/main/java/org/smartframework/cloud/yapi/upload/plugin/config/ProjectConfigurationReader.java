package org.smartframework.cloud.yapi.upload.plugin.config;

import com.intellij.openapi.project.Project;

public interface ProjectConfigurationReader<T> {

    T read(Project project);
}
