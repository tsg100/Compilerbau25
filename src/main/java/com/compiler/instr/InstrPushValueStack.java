package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class InstrPushValueStack extends InstrIntf {
    InstrIntf m_instruction;
    public InstrPushValueStack(InstrIntf instruction) {
        m_instruction = instruction;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.push(m_instruction.getValue());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("PUSH %%%d\n",
                m_instruction.getId()
        ));
    }
}
