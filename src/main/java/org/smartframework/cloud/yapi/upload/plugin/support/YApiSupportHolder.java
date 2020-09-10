package org.smartframework.cloud.yapi.upload.plugin.support;

import org.smartframework.cloud.yapi.upload.plugin.support.swagger.YApiSwaggerSupport;

public interface YApiSupportHolder {

    YApiSupport supports = new YApiSupports(YApiSwaggerSupport.INSTANCE);
}
