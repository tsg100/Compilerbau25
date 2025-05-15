package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;

import java.io.OutputStreamWriter;

public class InstrTDash extends com.compiler.InstrIntf {
    protected com.compiler.TokenIntf.Type m_operator;
    protected com.compiler.InstrIntf m_operand0;
    protected com.compiler.InstrIntf m_operand1;

    public InstrTDash(
        com.compiler.TokenIntf.Type operator, com.compiler.InstrIntf operand0, 
        com.compiler.InstrIntf operand1) {
        m_operator = operator;
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        m_value = (int) Math.pow(m_operand0.getValue(),m_operand1.getValue());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(
            String.format("%%%d = %s %%%d, %%%d\n",
            m_id, m_operator.toString(), m_operand0.getId(), m_operand1.getId()
        ));
    }
    
}
