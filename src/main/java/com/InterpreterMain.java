package com;

import java.io.OutputStreamWriter;

public class InterpreterMain {

    public static void main(String[] args) throws Exception {
        String input = new String("""
          {
          
            FUNCTION minüß(x){
                RETURN x - 1;
            };
          
            FUNCTION add(a,b){
                PRINT 42;
                DECLARE c;
                c = 2;
                RETURN CALL minüß(CALL minüß(a+b + c));
            };
            
            PRINT CALL add(1,2);
            PRINT 1;
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
