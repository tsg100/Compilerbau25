package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.TokenIntf;

import java.io.OutputStreamWriter;

public class InstrBitOr extends com.compiler.InstrIntf {

    protected com.compiler.TokenIntf.Type m_operator;
    protected com.compiler.InstrIntf m_operand0;
    protected com.compiler.InstrIntf m_operand1;


    public InstrBitOr(
            final com.compiler.TokenIntf.Type operator, final com.compiler.InstrIntf operand0,
            final com.compiler.InstrIntf operand1) {
        m_operator = operator;
        m_operand0 = operand0;
        m_operand1 = operand1;
    }


    @Override
    public void execute(final ExecutionEnvIntf env) throws Exception {
        m_value = m_operand0.getValue() | m_operand1.getValue();
    }


    @Override
    public void trace(final OutputStreamWriter os) throws Exception {
        os.write(
            String.format("%%%d = %s %%%d, %%%d\n",
            m_id, m_operator.toString(), m_operand0.getId(), m_operand1.getId()
        ));
    }
}
