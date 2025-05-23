package com;

import java.io.OutputStreamWriter;

public class InterpreterMain {

    public static void main(String[] args) throws Exception {
        String input = new String("""
          {
            PRINT 4 + 3 - 2;
          }
                """);
        com.compiler.CompileEnv compileEnv = new com.compiler.CompileEnv(input, false);

        compileEnv.compile();
        OutputStreamWriter outStream = new OutputStreamWriter(System.out, "UTF-8");
        System.out.println("AST:");
        compileEnv.dumpAst(System.out);
        System.out.println("\n\nPROGRAM:");
        compileEnv.dump(System.out);
        System.out.println("EXECUTE:");
        compileEnv.execute(outStream);
        outStream.flush();
    }

}