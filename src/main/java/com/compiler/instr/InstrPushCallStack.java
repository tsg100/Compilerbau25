package com.compiler.instr;

import com.compiler.ExecutionEnvIntf;
import com.compiler.InstrIntf;

import java.io.OutputStreamWriter;

public class InstrPushCallStack extends InstrIntf {
    @Override
    public void execute(ExecutionEnvIntf env) throws Exception {
        env.pushReturnAddr(env.getInstrIter());
    }

    @Override
    public void trace(OutputStreamWriter os) throws Exception {
        os.write("PUSH_RETURN_ADDR\n");

    }
}
