package com;
import java.io.OutputStreamWriter;

public class StmtParserMain {

    public static void main(String[] args) throws Exception {
        com.compiler.Lexer lexer = new com.compiler.Lexer();
        com.compiler.StmtParser parser = new com.compiler.StmtParser(lexer);
        String program = """
        {
          DECLARE a;
          DECLARE b;
          a = 1 + 2;
          b = 5;
          PRINT a ? b + 1 : -1;
          PRINT 1 + 2;
          PRINT 3 + 4;
        }
        """;
        com.compiler.ast.ASTStmtNode rootNode = parser.parseProgram(program);
        OutputStreamWriter outputWriter = new OutputStreamWriter(System.out);
        rootNode.execute(outputWriter);
        outputWriter.flush();
    }

}
