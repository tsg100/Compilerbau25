package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class InstrPopValueStack extends InstrIntf {

    public InstrPopValueStack() {}

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        m_value = env.pop();
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("%%%d = POP\n",
                m_id
                ));
    }
}
