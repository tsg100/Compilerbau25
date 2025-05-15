package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;
import com.compiler.TokenIntf;
import com.compiler.TokenIntf.Type;
import java.io.OutputStreamWriter;

public class InstrCompare extends InstrIntf {

    protected TokenIntf.Type m_operator;
    protected InstrIntf m_operand0;
    protected InstrIntf m_operand1;

    public InstrCompare(final Type m_operator, final InstrIntf m_operand0, final InstrIntf m_operand1) {
        this.m_operator = m_operator;
        this.m_operand0 = m_operand0;
        this.m_operand1 = m_operand1;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        //ergebnis : m_value

        switch (m_operator) {
            case GREATER:
                if(m_operand0.getValue() > m_operand1.getValue()) {
                    m_value = 1;
                    break;
                }
                m_value = 0;
                break;
            case LESS:
                if(m_operand0.getValue() < m_operand1.getValue()) {
                    m_value = 1;
                    break;
                }
                m_value = 0;
                break;
            case EQUAL:
                if(m_operand0.getValue() == m_operand1.getValue()) {
                    m_value = 1;
                    break;
                }
                m_value = 0;
                break;
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
