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
        singleCharTokens.put('{', TokenType.LEFT_BRACE);
        singleCharTokens.put('}', TokenType.RIGHT_BRACE);
        singleCharTokens.put('%',TokenType.MODULO);



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
            case '>':
                addToken(TokenType.GREATER);
                break;

            case '<':
                addToken(TokenType.LESS);
                break;
            case '"': string(); break;
            case '=':
                addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
                break;



            case '!':
                if (match('=')) {
                    addToken(TokenType.BANG_EQUAL);
                } else {
                    throw new RuntimeException("Unexpected character '!'");
                }
                break;



            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                }


                else {
                    System.out.println("Unexpected character: " + c + " at line " + line);
                }
        }
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
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

//    private void identifier() {
//        while (isAlphaNumeric(peek())) advance();
//
//        String text = source.substring(start, current);
//        TokenType type = keywords.getOrDefault(text, TokenType.IDENTIFIER);
//        addToken(type);
//    }


    // Keywords Map
    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("var", TokenType.VAR);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("for", TokenType.FOR);
        keywords.put("while", TokenType.WHILE);
        keywords.put("print",TokenType.PRINT);
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
    private Token string() {
        while (peek() != '"' && !isAtEnd()) {
            advance();
        }

        if (isAtEnd()) {
            throw new RuntimeException("Unterminated string.");
        }

        // closing "
        advance();

        // trim the surrounding quotes
        String value = source.substring(start + 1, current - 1);
        return new Token(TokenType.STRING, value, line);
    }


}
