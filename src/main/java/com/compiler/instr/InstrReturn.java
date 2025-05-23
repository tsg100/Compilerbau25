package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class InstrReturn extends InstrIntf {
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.setInstrIter(env.popReturnAddr());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("JUMP POP_RETURN_ADDR");
    }
}
