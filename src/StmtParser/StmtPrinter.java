package StmtParser;

import ExprParser.ASTPrinter;

public class StmtPrinter implements Stmt.Visitor<String> {
    private final ASTPrinter exprPrinter = new ASTPrinter();

    public String print(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public String visitVarStmt(Stmt.Var stmt) {
        StringBuilder sb = new StringBuilder();
        sb.append("(var ").append(stmt.name.lexeme);
        if (stmt.initializer != null) {
            sb.append(" = ").append(exprPrinter.print(stmt.initializer));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String visitBlockStmt(Stmt.Block stmt) {
        return "";
    }

    @Override
    public String visitIfBlock(Stmt.IfBlock stmt) {
        return "";
    }

    @Override
    public String visitWhileBlock(Stmt.WhileBlock stmt) {
        return "";
    }

    @Override
    public String visitExpressionStmt(Stmt.Expression stmt) {
        return "(expr " + exprPrinter.print(stmt.expression) + ")";
    }
}

