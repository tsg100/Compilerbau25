package com.compiler.instr;

import java.io.OutputStreamWriter;

import com.compiler.ExecutionEnvIntf;

public class InstrPrint extends com.compiler.InstrIntf {
    private com.compiler.InstrIntf m_expr;

    public InstrPrint(com.compiler.InstrIntf expr) {
        m_expr = expr;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.getOutputStream().write(Integer.toString(m_expr.getValue()));
        env.getOutputStream().write("\n");
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("PRINT %%%d\n", m_expr.getId()));
    }

}
