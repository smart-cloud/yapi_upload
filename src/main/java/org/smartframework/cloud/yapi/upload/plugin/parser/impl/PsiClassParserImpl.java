package org.smartframework.cloud.yapi.upload.plugin.parser.impl;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import org.smartframework.cloud.yapi.upload.plugin.base.DeprecatedAssert;
import org.smartframework.cloud.yapi.upload.plugin.model.YApiParam;
import org.smartframework.cloud.yapi.upload.plugin.parser.PsiClassParser;
import org.smartframework.cloud.yapi.upload.plugin.parser.PsiMethodParser;
import org.smartframework.cloud.yapi.upload.plugin.xml.YApiProjectProperty;
import org.smartframework.cloud.yapi.upload.plugin.base.impl.DeprecatedAssertImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class PsiClassParserImpl implements PsiClassParser {

    private final DeprecatedAssert deprecatedAssert = new DeprecatedAssertImpl();
    private final PsiMethodParser methodParser;

    public PsiClassParserImpl(YApiProjectProperty property, Project project) {
        methodParser = new PsiMethodParserImpl(property, project);
    }

    @Override
    public List<YApiParam> parse(@NotNull PsiClass c) {
        return this.getApiMethods(c).stream().map(m -> methodParser.parse(c, m))
                .collect(Collectors.toList());
    }

    @Override
    public List<PsiMethod> getApiMethods(@NotNull PsiClass c) {
        return Stream.of(c.getMethods()).filter(m -> !(m.getName().equals(c.getName()) ||
                m.getModifierList().hasModifierProperty(PsiModifier.PRIVATE) ||
                deprecatedAssert.isDeprecated(m))).collect(Collectors.toList());
    }
}
