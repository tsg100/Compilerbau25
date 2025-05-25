package com;

import java.io.OutputStreamWriter;

public class InterpreterMain {

    public static void main(String[] args) throws Exception {
        String input = new String("""
          {
          DECLARE a;
          a = 1;
            LOOP{
            PRINT(a);
            a = a + 1;
            IF(a > 10){
            BREAK
            }
            }ENDLOOP
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