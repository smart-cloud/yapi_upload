package org.smartframework.cloud.yapi.upload.plugin.api.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.NotNull;
import org.smartframework.cloud.yapi.upload.plugin.api.*;
import org.smartframework.cloud.yapi.upload.plugin.base.ContentTypeResolver;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiStatus;
import org.smartframework.cloud.yapi.upload.plugin.req.RequestResolver;
import org.smartframework.cloud.yapi.upload.plugin.req.impl.SmartCloudRequestHeaderResolver;
import org.smartframework.cloud.yapi.upload.plugin.req.impl.RequestContentTypeResolverImpl;
import org.smartframework.cloud.yapi.upload.plugin.req.impl.RequestResolverImpl;
import org.smartframework.cloud.yapi.upload.plugin.res.DocTagValueHandler;
import org.smartframework.cloud.yapi.upload.plugin.res.ResponseResolver;
import org.smartframework.cloud.yapi.upload.plugin.res.impl.ResponseContentTypeResolverImpl;
import org.smartframework.cloud.yapi.upload.plugin.res.impl.ResponseResolverImpl;
import org.smartframework.cloud.yapi.upload.plugin.util.CollectionUtils;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;

import java.util.Objects;
import java.util.function.Consumer;

public class ApiResolverImpl implements ApiResolver, DocTagValueHandler {

    private final PathResolver pathResolver = new PathResolverImpl();
    private final HttpMethodResolver httpMethodResolver = new HttpMethodResolverImpl();
    private final MenuSetter menuSetter = new MenuSetterImpl();
    private final StatusResolver statusResolver = new StatusResolverImpl();
    private final ContentTypeResolver requestContentTypeResolver = new RequestContentTypeResolverImpl();
    private final ContentTypeResolver responseContentTypeResolver = new ResponseContentTypeResolverImpl();
    private final BaseInfoSetter baseInfoSetter = new BaseInfoSetterImpl();
    private final RequestResolver requestResolver;
    private final ResponseResolver responseResolver;
    private final RequestResolver smartCloudRequestHeaderResolver = new SmartCloudRequestHeaderResolver();

    private final Consumer<YApiParam> docTagValueResolver = (param) -> {
        param.setTitle(this.handleDocTagValue(param.getTitle()));
        param.setMenu(this.handleDocTagValue(param.getMenu()));
        param.setMenuDesc(this.handleDocTagValue(param.getMenuDesc()));
        param.setStatus(YApiStatus.getStatus(this.handleDocTagValue(param.getStatus())));

        if (CollectionUtils.isNotEmpty(param.getParams())) {
            param.getParams()
                    .forEach(query -> query.setDesc(this.handleDocTagValue(query.getDesc())));
        }
        if (CollectionUtils.isNotEmpty(param.getReq_body_form())) {
            param.getReq_body_form()
                    .forEach(form -> form.setDesc(this.handleDocTagValue(form.getDesc())));
        }
        if (CollectionUtils.isNotEmpty(param.getReq_params())) {
            param.getReq_params().forEach(pathVariable -> pathVariable
                    .setDesc(this.handleDocTagValue(pathVariable.getDesc())));
        }
    };

    public ApiResolverImpl(YApiProjectProperty property, Project project) {
        requestResolver = new RequestResolverImpl(property, project);
        responseResolver = new ResponseResolverImpl(property, project);
    }

    @Override
    public void resolve(@NotNull PsiClass c, @NotNull PsiMethod m, @NotNull YApiParam target) {
        pathResolver.resolve(c, m, target);
        baseInfoSetter.set(c, m, target);
        httpMethodResolver.resolve(m, target);
        requestContentTypeResolver.resolve(c, m, target);
        responseContentTypeResolver.resolve(c, m, target);
        PsiDocComment classDoc = c.getDocComment();
        PsiDocComment methodDoc = m.getDocComment();
        statusResolver.resolve(classDoc, methodDoc, target);
        if (Objects.nonNull(classDoc)) {
            menuSetter.set(classDoc, target);
        }
        requestResolver.resolve(m, target);
        docTagValueResolver.accept(target);
        responseResolver.resolve(m.getReturnType(), target);
        smartCloudRequestHeaderResolver.resolve(m, target);
    }

}