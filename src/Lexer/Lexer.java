package Lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<Character, TokenType> singleCharTokens = new HashMap<>();

    static {
        singleCharTokens.put('(', TokenType.LEFT_PAREN);
        singleCharTokens.put(')', TokenType.RIGHT_PAREN);
        singleCharTokens.put('+', TokenType.PLUS);
        singleCharTokens.put('-', TokenType.MINUS);
        singleCharTokens.put('*', TokenType.STAR);
        singleCharTokens.put('/', TokenType.SLASH);
        singleCharTokens.put('=', TokenType.EQUAL);
        singleCharTokens.put(';', TokenType.SEMICOLON);
    }

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(TokenType.EOF, "", line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();

        // Map lookup for single-character tokens
        if (singleCharTokens.containsKey(c)) {
            addToken(singleCharTokens.get(c));
            return;
        }

        // Whitespace handling
        switch (c) {
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace
                break;
            case '\n':
                line++;
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    System.out.println("Unexpected character: " + c + " at line " + line);
                }
        }
    }

    private void number() {
        while (!isAtEnd() && isDigit(peek())) advance();
        String text = source.substring(start, current);
        addToken(TokenType.NUMBER, Double.parseDouble(text)); // ✅ Send numeric literal
    }


    private void identifier() {
        while (!isAtEnd() && isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        TokenType type = keywords.getOrDefault(text, TokenType.IDENTIFIER);
        addToken(type);
    }

    // Keywords Map
    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("var", TokenType.VAR);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("for", TokenType.FOR);
        keywords.put("while", TokenType.WHILE);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z');
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void addToken(TokenType type) {
        addToken(type, source.substring(start, current));
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, line, literal));
    }

}
