package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifierListOwner;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.util.PathUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.smartframework.cloud.yapi.upload.plugin.api.abs.AbstractPathResolver;
import org.jetbrains.annotations.NotNull;

public class MethodPathResolverImpl extends AbstractPathResolver {

    @Override
    public void resolve(@NotNull PsiModifierListOwner m, @NotNull YApiParam target) {
        //获取方法上面的RequestMapping 中的value
        PsiAnnotation psiAnnotation = PsiAnnotationUtils
                .findAnnotation(m, SpringMVCConstants.RequestMapping, SpringMVCConstants.GetMapping,
                        SpringMVCConstants.PostMapping, SpringMVCConstants.PutMapping,
                        SpringMVCConstants.DeleteMapping, SpringMVCConstants.PatchMapping);
        if (psiAnnotation != null) {
            String consumes = PsiAnnotationUtils
                    .getPsiAnnotationAttributeValue(psiAnnotation, "consumes");
            target.setConsumes(consumes);
            String path = target.getPath();
            if (Strings.isNotBlank(path)) {
                path = path + PathUtils.pathFormat(this.getPathByAnnotation(psiAnnotation), false);
            } else {
                path = PathUtils.pathFormat(this.getPathByAnnotation(psiAnnotation));
            }
            target.setPath(path);
        }
    }

}
