package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.javadoc.PsiDocComment;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.api.MenuSetter;
import org.smartframework.cloud.yapi.upload.plugin.constant.DocCommentConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiDocUtils;
import org.jetbrains.annotations.NotNull;

public class MenuSetterImpl implements MenuSetter {

    @Override
    public void set(@NotNull PsiDocComment docComment, @NotNull YApiParam target) {
        String value = PsiDocUtils.getTagDescription(docComment);
        //以前的取值方法（@menuDesc替换为@description）
        String descValue = PsiDocUtils.getTagValueByName(docComment, DocCommentConstants.TAG_DESCRIPTION);
        //如果没有描述注释
        if (Strings.isBlank(value)) {
            //菜单默认读取@menu注释
            value = PsiDocUtils.getTagValueByName(docComment, DocCommentConstants.TAG_MENU);
        }
        //如果没有@description注释
        if (Strings.isBlank(descValue)) {
            //描述默认读取描述信息
            descValue = value;
        }
        target.setMenu(value);
        target.setMenuDesc(descValue);
    }

}
