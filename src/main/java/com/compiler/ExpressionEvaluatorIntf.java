package com.compiler;

public interface ExpressionEvaluatorIntf {
    
    /**
     * evaluates the expression val and returns result as integer
     */
    int eval(String val) throws Exception;

}