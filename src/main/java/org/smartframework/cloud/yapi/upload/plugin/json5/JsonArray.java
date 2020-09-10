package org.smartframework.cloud.yapi.upload.plugin.json5;

import com.jgoodies.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;

public class JsonArray<T> extends Json<Collection<Json<T>>> {

    @SafeVarargs
    public JsonArray(Json<T>... items) {
        this.value = new ArrayList<>(Arrays.asList(items));
    }

    public JsonArray(Collection<Json<T>> items) {
        this.value = items;
    }

    @Override
    public String toString() {
        this.preStr();
        String intent = this.intent(this.level);
        StringJoiner joiner = new StringJoiner(",\n", intent + "[\n", "\n" + intent + "]");
        for (Json<T> json : this.value) {
            joiner.add(json.toString());
        }
        return joiner.toString();
    }

    @Override
    public String toString(String description) {
        StringJoiner joiner;
        String intent = this.intent(this.level);
        String desc;
        if (commentMode == COMMENT_MODE_SINGLE) {
            desc = Strings.isBlank(description) ? "" : "// " + description;
            joiner = new StringJoiner(",\n", "[ " + desc + "\n",
                    "\n" + intent + "]");
        } else {
            desc = Strings.isBlank(description) ? "" : "/* " + description + " */";
            joiner = new StringJoiner(",\n",
                    intent + "[ " + desc + "\n",
                    "\n" + intent + "]");
        }
        for (Json<T> json : this.value) {
            joiner.add(json.toString());
        }
        return joiner.toString();
    }

    @Override
    protected void preStr() {
        this.value.forEach(i -> {
            i.setLevel(this.level + 1);
            i.preStr();
        });
    }
}
