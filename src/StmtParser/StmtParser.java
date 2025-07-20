package StmtParser;

import ExprParser.Expr;
import Lexer.Token;
import Lexer.TokenType;
import ExprParser.ExprParser;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class StmtParser {
    private final List<Token> tokens;
    private int current;

    public StmtParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(parseDeclaration());
        }
        return statements;
    }

    private Stmt parseDeclaration() {
        if (match(TokenType.VAR)) return parseVarDeclaration();
        return parseStatement();
    }

    private Stmt parseVarDeclaration() {
        Token name = consume(TokenType.IDENTIFIER, "Expect variable name.");
        Expr initializer = null;

        if (match(TokenType.EQUAL)) {
            ExprParser exprParser = new ExprParser(tokens, current);
            initializer = exprParser.parse();
            current = exprParser.getCurrent(); // update current position
        }

        consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    private Stmt parseStatement() {
        ExprParser exprParser = new ExprParser(tokens, current);
        Expr expr = exprParser.parse();
        current = exprParser.getCurrent();
        consume(TokenType.SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(expr);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw error(peek(), message);
    }

    private ParseError error(Token token, String message) {
        System.err.println("[line " + token.line + "] Error at '" + token.lexeme + "': " + message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {}
}
