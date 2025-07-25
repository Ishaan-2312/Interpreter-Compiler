package Interpreter;

import ExprParser.Expr;
import Lexer.Token;
import Lexer.TokenType;
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
        Object value = null;

        if (stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }

        environment.define(stmt.name.lexeme, value);
        System.out.println("var " + stmt.name.lexeme + " = " + value);  // ✅ Debug line
        return null;


    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        executeBlock(stmt.statements, new Environment(environment)); // Pass current as enclosing
        return null;
    }


    @Override
    public Void visitIfBlock(Stmt.IfBlock stmt) {
        
         Object result=evaluate(stmt.conditionExpr);
        if(isTruthy(result)){
            execute(stmt.ifStmt);
        }
        else if(stmt.elseStmt!=null)execute(stmt.elseStmt);
        return null;
    }

    @Override
    public Void visitWhileBlock(Stmt.WhileBlock stmt) {
        while (isTruthy(evaluate(stmt.condition))) {
            if (stmt.body instanceof Stmt.Block) {
                // New environment nested in current
                executeBlock(((Stmt.Block) stmt.body).statements, new Environment(environment));
            } else {
                // No new env needed
                execute(stmt.body);
            }
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.PrintStmt stmt) {
        Object expression=evaluate(stmt.expression);
        System.out.println(expression);
        return null;
    }


    private boolean isTruthy(Object result) {
        if (result == null) return false;
        if (result instanceof Boolean) return (boolean) result;
        return true;

    }

    public void executeBlock(List<Stmt> statements, Environment newEnv) {
        Environment previous = this.environment;
        try {
            this.environment = newEnv;
            for (Stmt stmt : statements) {
                execute(stmt);
            }
        } finally {
            this.environment = previous; // Restore outer environment
        }
    }




    private void execute(Stmt stmt) {
        stmt.accept(this);
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

            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double) left > (double) right;

            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left < (double) right;

            case MODULO:
                return (double) left % (double) right;
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case BANG_EQUAL:
                return !isEqual(left, right);



        }

        return null;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
        return a.equals(b);
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

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = evaluate(expr.value);
//        System.out.println("ENV: " + environment.memoryMap);

        environment.assign(expr.name.lexeme, value);
        return value;
    }

}
