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
        String source="var a = 10;\n" +
                "{\n" +
                "  var a = 20;\n" +
                "  var b = a + 5;\n" +
                "}\n" +
                "var c = a + 1;\n";


        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        StmtParser parser = new StmtParser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(statements);
    }

}
