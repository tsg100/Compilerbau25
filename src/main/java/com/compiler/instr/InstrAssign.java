package com.compiler.instr;

import java.io.OutputStreamWriter;

import com.compiler.ExecutionEnvIntf;

public class InstrAssign extends com.compiler.InstrIntf {
    com.compiler.Symbol m_lhs;
    com.compiler.InstrIntf m_rhs;

    public InstrAssign(com.compiler.Symbol lhs, com.compiler.InstrIntf rhs) {
        m_lhs = lhs;
        m_rhs = rhs;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        m_lhs.m_number = m_rhs.getValue();
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("STORE %s, %%%d\n", m_lhs.m_name, m_rhs.getId()));
    }

}
