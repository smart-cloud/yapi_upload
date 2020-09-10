package org.smartframework.cloud.yapi.upload.plugin.req.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.search.GlobalSearchScope;
import org.smartframework.cloud.yapi.upload.plugin.constant.SpringMVCConstants;
import org.smartframework.cloud.yapi.upload.plugin.constant.TypeConstants;
import org.smartframework.cloud.yapi.upload.plugin.model.ValueWrapper;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiQuery;
import org.smartframework.cloud.yapi.upload.plugin.req.PsiParamFilter;
import org.smartframework.cloud.yapi.upload.plugin.req.SimpleRequestBodyParamResolver;
import org.smartframework.cloud.yapi.upload.plugin.util.DesUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.JsonPropertyUtil;
import org.smartframework.cloud.yapi.upload.plugin.util.PsiAnnotationUtils;
import org.smartframework.cloud.yapi.upload.plugin.util.ValidUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class RequestQueryResolverImpl implements SimpleRequestBodyParamResolver {

    private final Project project;

    public RequestQueryResolverImpl(Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public PsiParamFilter getPsiParamFilter(@NotNull PsiMethod m,
                                            @NotNull YApiParam target) {
        if (this.noBody(target)) {
            return p -> true;
        }
        PsiParameter[] parameters = m.getParameterList().getParameters();
        return this.hasRequestBody(parameters) ? (psiParameter -> PsiAnnotationUtils
                .isNotAnnotatedWith(psiParameter, SpringMVCConstants.RequestBody))
                : (p -> false);
    }

    @Override
    public void doResolverItem(@NotNull PsiMethod m, @NotNull PsiParameter param,
            @NotNull YApiParam target) {
        Set<YApiQuery> results = new LinkedHashSet<>();
        String typeClassName = param.getType().getCanonicalText();
        String typeName = param.getType().getPresentableText();
        //如果是基本类型
        if (TypeConstants.isNormalType(typeName)) {
            PsiAnnotation psiAnnotation = PsiAnnotationUtils
                    .findAnnotation(param, SpringMVCConstants.RequestParam);
            YApiQuery yapiQuery = new YApiQuery();
            if (psiAnnotation != null) {
                ValueWrapper valueWrapper = this.handleParamAnnotation(param, psiAnnotation);
                yapiQuery.full(valueWrapper);
            } else {//没有注解
                yapiQuery.setRequired(ValidUtils.notNullOrBlank(param) ? "1" : "0");
                yapiQuery.setName(param.getName());
                yapiQuery.setExample(TypeConstants.normalTypes.get(typeName)
                        .toString());
            }
            yapiQuery.setDesc(DesUtils.getParamDesc(m, param.getName()) + "("
                    + typeName + ")");
            results.add(yapiQuery);
        } else {
            PsiClass psiClass = JavaPsiFacade.getInstance(project)
                    .findClass(typeClassName, GlobalSearchScope.allScope(project));
            for (PsiField field : Objects.requireNonNull(psiClass).getAllFields()) {
                if (
                        Objects.requireNonNull(field.getModifierList())
                                .hasModifierProperty(PsiModifier.STATIC)) {
                    continue;
                }
                YApiQuery query = new YApiQuery();
                query.setRequired(ValidUtils.notNullOrBlank(field) ? "1" : "0");
//                query.setName(field.getName());
                query.setName(JsonPropertyUtil.getName(field));
                query.setDesc(DesUtils.getLinkRemark(field, project));
                String typePkName = field.getType().getCanonicalText();
                if (TypeConstants.isBaseType(typePkName)) {
                    query.setExample(
                            TypeConstants.normalTypesPackages.get(typePkName)
                                    .toString());
                }
                results.add(query);
            }
        }
        Set<YApiQuery> apiQueries = target.getParams();
        if (Objects.isNull(apiQueries)) {
            apiQueries = new HashSet<>();
            target.setParams(apiQueries);
        }
        apiQueries.addAll(results);
    }
}
