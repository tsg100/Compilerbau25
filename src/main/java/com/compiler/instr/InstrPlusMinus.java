package com.compiler.instr;

import java.io.OutputStreamWriter;
import com.compiler.ExecutionEnvIntf;

public class InstrPlusMinus extends com.compiler.InstrIntf {
    protected com.compiler.TokenIntf.Type m_operator;
    protected com.compiler.InstrIntf m_operand0; 
    protected com.compiler.InstrIntf m_operand1; 

    public InstrPlusMinus(
        com.compiler.TokenIntf.Type operator, com.compiler.InstrIntf operand0, 
        com.compiler.InstrIntf operand1) {
        m_operator = operator;
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        if (m_operator == com.compiler.TokenIntf.Type.PLUS) {
            m_value = m_operand0.getValue() + m_operand1.getValue();
        } else {
            m_value = m_operand0.getValue() - m_operand1.getValue();
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(
            String.format("%%%d = %s %%%d, %%%d\n",
            m_id, m_operator.toString(), m_operand0.getId(), m_operand1.getId()
        ));
    }
    
}
