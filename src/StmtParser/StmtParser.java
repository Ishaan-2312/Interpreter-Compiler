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
            current = exprParser.getCurrent();
        }

        consume(TokenType.SEMICOLON, "Expect ';' after variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    private Stmt parseStatement() {
        if(match(TokenType.WHILE))return parseWhileBlock();
        if (match(TokenType.IF)) return parseIfBlock();
        if(match(TokenType.LEFT_BRACE))return new Stmt.Block(block());
        ExprParser exprParser = new ExprParser(tokens, current);
        Expr expr = exprParser.parse();
        current = exprParser.getCurrent();
        consume(TokenType.SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(expr);
    }

    private Stmt parseWhileBlock() {
        consume(TokenType.LEFT_PAREN,"Expect '(' after 'while'.");
        ExprParser exprParser=new ExprParser(tokens,current);
        Expr condition=exprParser.parse();
        current=exprParser.getCurrent();
        consume(TokenType.RIGHT_PAREN,"Expect ')' after condition.");
        Stmt whileBody=parseStatement();
        return new Stmt.WhileBlock(condition,whileBody);
    }

    private Stmt parseIfBlock() {
        consume(TokenType.LEFT_PAREN, "Expect '(' after 'if'.");


        ExprParser exprParser = new ExprParser(tokens, current);
        Expr condition = exprParser.parse();
        current = exprParser.getCurrent();

        consume(TokenType.RIGHT_PAREN, "Expect ')' after if condition.");

        Stmt thenBranch = parseStatement();
        Stmt elseBranch = null;

        if (match(TokenType.ELSE)) {
            elseBranch = parseStatement();
        }

        return new Stmt.IfBlock(condition, thenBranch, elseBranch);
    }




    private List<Stmt> block() {
        List<Stmt> statements=new ArrayList<>();

        while(!check(TokenType.RIGHT_BRACE) && !isAtEnd()){
            statements.add(parseDeclaration());
        }
        consume(TokenType.RIGHT_BRACE, "Expect '}' after block.");
        return statements;
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
