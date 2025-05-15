package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf.Type;

import java.io.OutputStreamWriter;

public class InstrAndOr extends com.compiler.InstrIntf {
    protected com.compiler.TokenIntf.Type m_operator;
    protected com.compiler.InstrIntf m_operand0;
    protected com.compiler.InstrIntf m_operand1;

    public InstrAndOr(Type operator, InstrIntf operand0,
            InstrIntf operand1) {
        m_operator = operator;
        m_operand0 = operand0;
        m_operand1 = operand1;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        if (m_operator == Type.AND) {
            m_value = m_operand0.getValue() != 0 && m_operand1.getValue() != 0 ? 1 : 0 ;
        } else {
            m_value = m_operand0.getValue() != 0 || m_operand1.getValue() != 0 ? 1 : 0 ;
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
