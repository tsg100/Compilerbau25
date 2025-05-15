package com.compiler.instr;

import java.io.OutputStreamWriter;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf.Type;
import com.compiler.ast.ASTExprNode;

public class InstrShift extends InstrIntf {

	protected com.compiler.TokenIntf.Type m_operator;
    protected com.compiler.InstrIntf m_operand0; 
    protected com.compiler.InstrIntf m_operand1;
	
	public InstrShift(Type operator, InstrIntf operand0, InstrIntf operand1) {
		m_operator = operator;
        m_operand0 = operand0;
        m_operand1 = operand1;
	}

	@Override
	public void execute(ExecutionEnvIntf env) throws Exception {
		if (m_operator == Type.SHIFTLEFT) {
            m_value = m_operand0.getValue() << m_operand1.getValue();
        } else {
            m_value = m_operand0.getValue() >> m_operand1.getValue();
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
