package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;

import java.io.OutputStreamWriter;

public class InstrVariableExpr extends com.compiler.InstrIntf {

    private com.compiler.Symbol m_variable;

    public InstrVariableExpr(com.compiler.Symbol variable) {
        this.m_variable = variable;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        m_value = m_variable.m_number;
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("%%%d = LOAD %s\n", m_id, this.m_variable.m_name));    }
}
