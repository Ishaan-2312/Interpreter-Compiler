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
                "var a = 5;\n" +
                        "var b = 0;\n" +
                        "\n" +
                        "for (var i = 0; i < 3; i = i + 1) {\n" +
                        "  b = b + i;\n" +
                        "}\n" +
                        "\n" +
                        "while (b < 10) {\n" +
                        "  b = b + 1;\n" +
                        "}\n" +
                        "\n" +
                        "if (b > 9) {\n" +
                        "  print(b);\n" +
                        "} else {\n" +
                        "  print(0);\n" +
                        "}";











        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        StmtParser parser = new StmtParser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(statements);
    }

}
