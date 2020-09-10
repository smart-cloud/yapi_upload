package org.smartframework.cloud.yapi.upload.plugin.util;

import com.jgoodies.common.base.Strings;
import org.jetbrains.annotations.NotNull;

public final class PathUtils {

    private PathUtils() {
    }

    public static String pathFormat(@NotNull String path) {
        return pathFormat(path, true);
    }

    public static String pathFormat(@NotNull String path, boolean defaultRoot) {
        String pathStr = path.trim();
        if (!defaultRoot && Strings.isEmpty(pathStr)) {
            return "";
        }
        String split = "/";
        pathStr = pathStr.startsWith(split) ? pathStr : (split + pathStr);
        if (pathStr.endsWith("/") && pathStr.length() > 1) {
            pathStr = pathStr.substring(0, pathStr.length() - 1);
        }
        return pathStr;
    }
}
