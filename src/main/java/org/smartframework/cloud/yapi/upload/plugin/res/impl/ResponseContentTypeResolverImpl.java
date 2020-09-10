package org.smartframework.cloud.yapi.upload.plugin.res.impl;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.smartframework.cloud.yapi.upload.plugin.base.ContentTypeResolver;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.ValidUtils;
import org.jetbrains.annotations.NotNull;

public class ResponseContentTypeResolverImpl implements ContentTypeResolver {

    @Override
    public void resolve(@NotNull PsiClass c, @NotNull PsiMethod m, @NotNull YApiParam target) {
        if (this.isResponseJson(c, m)) {
            target.setRes_body_type(JSON_VALUE);
        } else {
            target.setRes_body_type(ROW_VALUE);
        }
//        String consumes = target.getConsumes();
//        if (Strings.isNotBlank(consumes)) {
//            if (json.equals(consumes)) {
//                target.setConsumes(consumes);
//            }
//        }
    }

    private boolean isResponseJson(PsiClass psiClass, PsiMethod psiMethod) {
        return ValidUtils.hasAnnotation(psiClass, SpringMVCConstants.RestController) ||
                ValidUtils.hasAnnotation(psiClass, SpringMVCConstants.ResponseBody) ||
                ValidUtils.hasAnnotation(psiMethod, SpringMVCConstants.ResponseBody);
    }

}
