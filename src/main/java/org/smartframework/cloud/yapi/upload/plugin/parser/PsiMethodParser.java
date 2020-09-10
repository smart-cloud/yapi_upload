package org.smartframework.cloud.yapi.upload.plugin.parser;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

/**
 * <b>方法解析</b>
 * @author aqiu
 * @date 2020/5/12 11:02 上午
 **/
public interface PsiMethodParser {

    YApiParam parse(@NotNull PsiClass c, @NotNull PsiMethod m);

}
