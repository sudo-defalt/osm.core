package org.defalt.core.controller.exception;

import com.fasterxml.jackson.databind.node.TextNode;

public class ExceptionResponseBuilder {
    public static ExceptionResponse build(Exception exception) {
        return ExceptionResponse.builder().error(TextNode.valueOf(exception.getMessage())).build();
    }
}
