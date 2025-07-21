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
        String source = ""
                + "var a = 10;\n"
                + "var b;\n"
                + "\n"
                + "if (a > 5) {\n"
                + "  b = 1;\n"
                + "} else {\n"
                + "  b = 2;\n"
                + "}\n"
                + "\n"
                + "var i = 0;\n"
                + "while (i < 3) {\n"
                + "  i = i + 1;\n"
                + "  b = b + 1;\n"
                + "}\n"
                + "\n"
                + "var res = b;\n";


        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        StmtParser parser = new StmtParser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(statements);
    }

}
