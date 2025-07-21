package Lexer;

public enum TokenType {

    LEFT_PAREN, RIGHT_PAREN, PLUS, MINUS, STAR, SLASH, EQUAL, SEMICOLON,PRINT, RETURN,LEFT_BRACE, RIGHT_BRACE,


    // Literals
    IDENTIFIER, NUMBER,

    // Keywords
    VAR, IF, ELSE, FOR, WHILE,

    EOF
}
