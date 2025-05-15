package com.compiler.instr;

import java.io.OutputStreamWriter;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrBlock;
import com.compiler.InstrIntf;

public class InstrCondJump extends InstrIntf {
    InstrIntf m_cond;
    InstrBlock m_trueBlock;
    InstrBlock m_falseBlock;


    public InstrCondJump(InstrIntf m_cond, InstrBlock m_trueBlock, InstrBlock m_falseBlock) {
        this.m_cond = m_cond;
        this.m_trueBlock = m_trueBlock;
        this.m_falseBlock = m_falseBlock;
    }

    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        int cond = m_cond.getValue();
        if (cond != 0) {
            env.setInstrIter(m_trueBlock.getIterator());
        } else {
            env.setInstrIter(m_falseBlock.getIterator());
        }
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("JUMPCOND ");
        os.write(m_trueBlock.getName());
        os.write(", ");
        os.write(m_falseBlock.getName());
        os.write("\n");
    }
}
