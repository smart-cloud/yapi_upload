package org.smartframework.cloud.yapi.upload.plugin.req;

import com.intellij.psi.PsiParameter;
import java.util.function.Predicate;

@FunctionalInterface
public interface PsiParamFilter extends Predicate<PsiParameter> {

}
