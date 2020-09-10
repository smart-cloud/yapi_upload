package org.smartframework.cloud.yapi.upload.plugin.parser.impl;

import com.google.gson.GsonBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.smartframework.cloud.yapi.upload.plugin.constant.PropertyNamingStrategy;
import org.smartframework.cloud.yapi.upload.plugin.constant.TypeConstants;
import org.smartframework.cloud.yapi.upload.plugin.parser.ObjectParser;
import org.smartframework.cloud.yapi.upload.plugin.util.JsonPropertyUtil;
import org.smartframework.cloud.yapi.upload.plugin.util.PropertyNamingUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiUtils;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractObjectParser implements ObjectParser {

    protected final YApiProjectProperty property;
    protected final Project project;

    protected AbstractObjectParser(YApiProjectProperty property, Project project) {
        this.property = property;
        this.project = project;
    }

    @Override
    public String getRawResponse(PsiType psiType) {
        Object obj;
        String typePkName = psiType.getCanonicalText();
        String[] types = typePkName.split("<");
        String type = types[0];
        //如果是基本类型
        if (TypeConstants.isBaseType(typePkName)) {
            return TypeConstants.normalTypesPackages.get(typePkName).toString();
        } else {
            //如果是Map类型
            if (PsiUtils.isMap(psiType)) {
                obj = this.getMapRaw();
                //如果是集合类型（List Set）
            } else if (TypeConstants.arrayTypeMappings.containsKey(type)) {
                obj = this.getArrayRaw(typePkName);
            } else if (typePkName.endsWith("[]")) {
                //数组形式的返回值（且不是集合类型前缀）
                List<Object> tmp = new ArrayList<>();
                Object object = this.getObjectRaw(typePkName.replace("[]", ""));
                tmp.add(object);
                obj = tmp;
            } else {
                //其他情况 object
                obj = this.getObjectRaw(typePkName);
            }
            return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
        }
    }

    @Override
    public String handleFieldName(String fieldName) {
        return PropertyNamingUtils.convert(fieldName, PropertyNamingStrategy.of(String.
                valueOf(this.property.getStrategy())));
    }

    private Map<String, Object> getMapRaw() {
        Map<String, Object> m = new HashMap<>();
        m.put("key", "value");
        return m;
    }

    private List<Object> getArrayRaw(String typePkName) {
        List<Object> result = new ArrayList<>();
        String[] types = typePkName.split("<");
        //如果有泛型
        if (types.length > 1) {
            String childrenType = types[1].split(">")[0];
            childrenType = childrenType.replace("? extends ", "")
                    .replace("? super ", "");
            boolean isWrapArray = childrenType.endsWith("[]");
            //是否是数组类型
            if (isWrapArray) {
                childrenType = childrenType.replace("[]", "");
            }
            //如果是基本类型
            List<Object> tmp = new ArrayList<>();
            if (TypeConstants.isBaseType(childrenType)) {
                tmp.add(TypeConstants.normalTypesPackages.get(childrenType));
            } else {
                //如果是其他类型
                tmp.add(this.getObjectRaw(childrenType));
            }
            if (isWrapArray) {
                result.add(tmp);
            } else {
                result = tmp;
            }
        } else {
            //如果没有泛型
            result.add(this.getMapRaw());
        }
        return result;
    }

    private Object getObjectRaw(String typePkName) {
        Map<String, Object> result = new HashMap<>();
        PsiClass psiClass = JavaPsiFacade.getInstance(this.project)
                .findClass(typePkName, GlobalSearchScope.allScope(this.project));
        if (Objects.nonNull(psiClass)) {
            for (PsiField field : psiClass.getAllFields()) {
                if (
                        Objects.requireNonNull(field.getModifierList())
                                .hasModifierProperty(PsiModifier.STATIC)) {
                    continue;
                }
//                String fieldName = field.getName();
//                result.put(fieldName, this.getRawResponse(field.getType()));
                result.put(JsonPropertyUtil.getName(field), this.getRawResponse(field.getType()));
            }
        }
        return result;
    }
}
