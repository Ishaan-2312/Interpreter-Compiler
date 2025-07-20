package Lexer;

public class Token {

    public final TokenType type;
    public final String lexeme;
    public final Object literal;
    public final int line;

    Token(TokenType type, String lexeme, int line,Object literal) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.literal=literal;
    }
    // Add this constructor inside Token.java
    public Token(TokenType type, String lexeme, int line) {
        this(type, lexeme, line, null);
    }


    public String toString() {
        return type + " '" + lexeme + "' (line " + line + ")";
    }
}
