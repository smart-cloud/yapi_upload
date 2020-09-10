package org.smartframework.cloud.yapi.upload.plugin.support.swagger;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.jgoodies.common.base.Strings;
import org.smartframework.cloud.yapi.upload.plugin.constant.SwaggerConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.ValueWrapper;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.support.YApiSupport;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.jetbrains.annotations.NonNls;

public class YApiSwaggerSupport implements YApiSupport {


    private YApiSwaggerSupport() {
    }

    public static final YApiSwaggerSupport INSTANCE = new YApiSwaggerSupport();

    @Override
    public boolean isImportant() {
        return true;
    }

    @Override
    public void handleMethod(PsiMethod psiMethod, @NonNls YApiParam apiDTO) {
        if (Strings.isBlank(apiDTO.getTitle())) {
            String title = PsiAnnotationUtils
                    .getPsiParameterAnnotationValue(psiMethod, SwaggerConstants.API_OPERATION);
            apiDTO.setTitle(title);
        }
    }

    @Override
    public void handleParam(PsiParameter psiParameter, @NonNls ValueWrapper wrapper) {
        if (Strings.isBlank(wrapper.getDesc())) {
            String desc = PsiAnnotationUtils
                    .getPsiParameterAnnotationValue(psiParameter, SwaggerConstants.API_PARAM);
            wrapper.setDesc(desc);
        }
    }

    @Override
    public void handleField(PsiField psiField, @NonNls ValueWrapper wrapper) {
        if (Strings.isBlank(wrapper.getDesc())) {
            String desc = PsiAnnotationUtils
                    .getPsiParameterAnnotationValue(psiField, SwaggerConstants.API_MODEL_PROPERTY);
            wrapper.setDesc(desc);
        }
    }
}
