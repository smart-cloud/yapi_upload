package org.smartframework.cloud.yapi.upload.plugin.api;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

/**
 * 路由相关信息解析（包括路由前缀添加）
 * @author aqiu
 * @date 2020/5/12 10:59 上午
 **/
public interface PathResolver {

    void resolve(@NotNull PsiClass c, @NotNull PsiMethod m, @NotNull YApiParam target);

}
