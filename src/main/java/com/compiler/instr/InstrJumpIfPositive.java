package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrBlock;

import java.io.OutputStreamWriter;

public class InstrJumpIfPositive extends com.compiler.InstrIntf {

    private final int result;
    private final InstrBlock m_targetBlock;


    public InstrJumpIfPositive(final int result, final com.compiler.InstrBlock targetBlock) {
        this.result = result;
        m_targetBlock = targetBlock;
    }

    @Override
    public void execute(final ExecutionEnvIntf env) throws Exception {
        if(result > 0) {
            env.setInstrIter(m_targetBlock.getIterator());
        }
    }

    @Override
    public void trace(final OutputStreamWriter os) throws Exception {
        os.write(String.format("JUMP_IF_POSITIVE %s\n", m_targetBlock.getName()));
    }
}
