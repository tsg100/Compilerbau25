package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class InstrPopStack extends InstrIntf {

    public InstrPopStack() {}

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        m_value = env.pop();
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("POP_INTO " + m_value);
    }
}
