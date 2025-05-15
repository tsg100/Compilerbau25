package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class InstrUnaryExpr extends InstrIntf {
    InstrIntf m_operand;
    TokenIntf.Type m_operator;

    public InstrUnaryExpr(TokenIntf.Type m_operator, InstrIntf operand) {
        this.m_operand = operand;
        this.m_operator = m_operator;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        int operandValue = m_operand.getValue();
        switch (m_operator) {
            case MINUS -> m_value = -operandValue;
            case NOT -> m_value = operandValue == 0 ? 1 : 0;
            default -> throw new RuntimeException("Unknown unary operator: " + m_operator);
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("%%%d = %s %%%d\n", m_id, m_operator.toString(), m_operand.getId()));
    }
}
