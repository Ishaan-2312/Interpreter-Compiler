import Lexer.Lexer;

import java.util.List;
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
        String source = "var x = 5 + 3;\nx + 2;";



        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        StmtParser parser = new StmtParser(tokens);
        List<Stmt> statements = parser.parse();

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(statements);
    }

}
