package com.compiler.instr;

import java.io.OutputStreamWriter;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;

public class QuestionMarkInstr extends InstrIntf {
    protected InstrIntf m_predicateInstr,  m_op0Instr, m_op1Instr;


    public QuestionMarkInstr(InstrIntf predicateInstr, InstrIntf op0Instr, InstrIntf op1Instr){
        this.m_predicateInstr = predicateInstr;
        this.m_op0Instr = op0Instr;
        this.m_op1Instr = op1Instr;
    }


    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        if(m_predicateInstr.getValue() != 0) {
            m_value = m_op0Instr.getValue();
        } else {
            m_value = m_op1Instr.getValue();
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
       os.write(
            String.format("%%%d = QuestionMark %%%d, %%%d, %%%d\n",
            m_id, m_predicateInstr.getId(), m_op0Instr.getId(), m_op1Instr.getId()
        ));
    }

}
