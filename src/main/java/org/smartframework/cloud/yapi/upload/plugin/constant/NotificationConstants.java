package org.smartframework.cloud.yapi.upload.plugin.constant;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;

public interface NotificationConstants {

    NotificationGroup NOTIFICATION_GROUP = new NotificationGroup(YApiConstants.name,
            NotificationDisplayType.BALLOON, true);
}
