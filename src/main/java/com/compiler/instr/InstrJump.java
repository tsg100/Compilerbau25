package com.compiler.instr;

import java.io.OutputStreamWriter;

import com.compiler.ExecutionEnvIntf;

public class InstrJump extends com.compiler.InstrIntf {
    private com.compiler.InstrBlock m_targetBlock;

    public InstrJump(com.compiler.InstrBlock targetBlock) {
        m_targetBlock = targetBlock;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.setInstrIter(m_targetBlock.getIterator());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write(String.format("JUMP %s\n", m_targetBlock.getName()));
    }

}
