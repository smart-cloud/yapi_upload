package org.smartframework.cloud.yapi.upload.plugin.json5;

import com.jgoodies.common.base.Strings;

public class Json<T> {

    public final static String INTENT = "  ";
    protected final static int COMMENT_MODE_SINGLE = 1;
    protected final static int COMMENT_MODE_MULTIPLE = 2;
    protected int commentMode = COMMENT_MODE_SINGLE;

    protected T value;

    protected int level = 0;

    public Json() {
    }

    public Json(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return value instanceof String ? ("\"" + value + "\"") : this.value.toString();
    }

    protected void preStr() {
    }

    public String toString(String description) {
        String vs = value instanceof String ? ("\"" + value + "\"") : this.value.toString();
        if (Strings.isBlank(description)) {
            return vs;
        }
        if (commentMode == COMMENT_MODE_SINGLE) {
            vs += ", // " + description;
        } else if (commentMode == COMMENT_MODE_MULTIPLE) {
            vs += "/* " + description + " */";
        }
        return vs;
    }

    protected String intent(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append(INTENT);
        }
        return builder.toString();
    }

}
