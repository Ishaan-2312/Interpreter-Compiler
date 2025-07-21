import Lexer.Lexer;

import java.util.List;
import java.util.Scanner;

import ExprParser.ExprParser;
import Lexer.Token;
import ExprParser.Expr;
import ExprParser.ASTPrinter;
import StmtParser.StmtPrinter;
import StmtParser.StmtParser;
import StmtParser.Stmt;
import Interpreter.Interpreter;


public class Main {
    public static void main(String[] args) {
        String source =
                "var i = 0;\n" +
                        "for (var j = 0; j < 2; j = j + 1) {\n" +
                        "  while (i < 3) {\n" +
                        "    if (i < 2) {\n" +
                        "      var low = i;\n" +
                        "    } else {\n" +
                        "      var high = i;\n" +
                        "    }\n" +
                        "    i = i + 1;\n" +
                        "  }\n" +
                        "  var step = j;\n" +
                        "}\n" +
                        "var done = i + 1;";





        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        StmtParser parser = new StmtParser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(statements);
    }

}
