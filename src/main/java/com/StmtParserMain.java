package com;
import java.io.OutputStreamWriter;

public class StmtParserMain {

    public static void main(String[] args) throws Exception {
        com.compiler.Lexer lexer = new com.compiler.Lexer();
        com.compiler.StmtParser parser = new com.compiler.StmtParser(lexer);
        String program = """
        {
          DECLARE index0;
          DECLARE sum;
          DECLARE index1;
          index0 = 10;
          sum = 0;
          WHILE(index0) {
              index1 = 5;
              WHILE(index1) {
              sum = sum + index1;
                  index1 = index1 - 1;
              };
              index0 = index0 - 1;
          };
          PRINT sum;

        }
,
        """;
        com.compiler.ast.ASTStmtNode rootNode = parser.parseProgram(program);
        OutputStreamWriter outputWriter = new OutputStreamWriter(System.out);
        rootNode.execute(outputWriter );
        outputWriter.flush();
    }

}
