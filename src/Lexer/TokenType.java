package Lexer;

public enum TokenType {

    LEFT_PAREN, RIGHT_PAREN, PLUS, MINUS, STAR, SLASH, EQUAL, SEMICOLON,LEFT_BRACE, RIGHT_BRACE,MODULO,


    // Literals
    IDENTIFIER, NUMBER,

    // Keywords
    VAR, IF, ELSE, FOR, WHILE,

    //Conditionals
    GREATER,
    LESS,
    EQUAL_EQUAL,BANG_EQUAL,

    //Print
    PRINT,

    //String
    STRING,


    EOF
}
