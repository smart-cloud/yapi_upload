package org.smartframework.cloud.yapi.upload.plugin.model;

import java.io.Serializable;

/**
 * query
 *
 * @date 2019/2/11 5:05 PM
 */
@SuppressWarnings("unused")
public class YApiQuery extends ValueWrapper implements Serializable {

    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public YApiQuery() {
    }
}
