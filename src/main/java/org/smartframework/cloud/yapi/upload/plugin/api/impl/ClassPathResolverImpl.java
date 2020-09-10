package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifierListOwner;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PathUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.smartframework.cloud.yapi.upload.plugin.api.abs.AbstractPathResolver;
import org.jetbrains.annotations.NotNull;

public class ClassPathResolverImpl extends AbstractPathResolver {

    @Override
    public void resolve(@NotNull PsiModifierListOwner m, @NotNull YApiParam target) {
        //获取类上面的RequestMapping 中的value
        PsiAnnotation psiAnnotation = PsiAnnotationUtils
                .findAnnotation(m, SpringMVCConstants.RequestMapping);
        if (psiAnnotation != null) {
            String consumes = PsiAnnotationUtils
                    .getPsiAnnotationAttributeValue(psiAnnotation, "consumes");
            target.setConsumes(consumes);
            target.setPath(PathUtils.pathFormat(this.getPathByAnnotation(psiAnnotation), false));
        }
    }

}
