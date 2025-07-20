package Interpreter;

import ExprParser.Expr;
import Lexer.Token;
import MemoryModel.Environment;
import StmtParser.Stmt;

import java.util.List;

public class Interpreter implements Expr.Visitor<Object> , Stmt.Visitor<Void> {

    Environment environment=new Environment();


    public void interpret(List<Stmt> statements) {
        for (Stmt stmt : statements) {
            stmt.accept(this);
        }
    }



    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(value);
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
       Object value=null;

       if(stmt.initializer!=null){
           value = evaluate(stmt.initializer);
       }
       environment.define(stmt.name.lexeme,value);

       return null;


    }
    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }


    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case PLUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left + (double) right;
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left - (double) right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double) left * (double) right;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double) left / (double) right;
        }

        return null;
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeException("Operands must be numbers for '" + operator.lexeme + "'");
    }


    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return environment.getValue(expr.name.lexeme);
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }
}
