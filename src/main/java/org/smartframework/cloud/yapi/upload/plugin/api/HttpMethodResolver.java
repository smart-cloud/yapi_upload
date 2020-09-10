package org.smartframework.cloud.yapi.upload.plugin.api;

import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.jetbrains.annotations.NotNull;

/**
 * <b>HttpMethod解析</b>
 * @author aqiu
 * @date 2020/5/12 11:02 上午
 **/
public interface HttpMethodResolver {

    void resolve(@NotNull PsiMethod method, @NotNull YApiParam target);

}
