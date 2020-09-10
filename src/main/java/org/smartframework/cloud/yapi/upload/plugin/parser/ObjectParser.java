package org.smartframework.cloud.yapi.upload.plugin.parser;

import com.intellij.psi.PsiType;
import org.smartframework.cloud.yapi.upload.plugin.res.DocTagValueHandler;
import org.smartframework.cloud.yapi.upload.plugin.res.ResponseFieldNameHandler;

public interface ObjectParser extends ObjectRawParser, ResponseFieldNameHandler,
        DocTagValueHandler {

    String getJsonResponse(PsiType psiType);

}
