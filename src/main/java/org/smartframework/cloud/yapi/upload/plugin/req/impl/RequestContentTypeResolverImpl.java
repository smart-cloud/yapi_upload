package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.base.ContentTypeResolver;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiParamUtils;
import org.jetbrains.annotations.NotNull;

public class RequestContentTypeResolverImpl implements ContentTypeResolver {

    @Override
    public void resolve(@NotNull PsiClass c, @NotNull PsiMethod m, @NotNull YApiParam target) {
        if (PsiParamUtils.noBody(target.getMethod())) {
            return;
        }
//        if (isResponseJson(c, m)) {
        //GET和DELETE方法没有body默认请求是json格式，否则报错
        if (PsiParamUtils.noBody(target.getMethod())) {
            target.setReq_body_type(JSON_VALUE);
            //有body但是没有@RequestBody注解，设置为form
        } else if (!PsiParamUtils.hasRequestBody(m.getParameterList().getParameters())) {
            target.setReq_body_type(FORM_VALUE);
        }
//        } else {
//            target.setReq_body_type(ROW_VALUE);
//        }
//        String consumes = target.getConsumes();
//        if (Strings.isNotBlank(consumes)) {
//            if (JSON.equals(consumes)) {
//                target.setConsumes(consumes);
//            }
//        }
    }

}
