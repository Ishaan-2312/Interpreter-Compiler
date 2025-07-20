package StmtParser;

import ExprParser.ASTPrinter;

public class StmtPrinter implements Stmt.Visitor<String> {
    private final ASTPrinter exprPrinter = new ASTPrinter(); // Tu already bana chuka hai Expr ka printer

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
    public String visitExpressionStmt(Stmt.Expression stmt) {
        return "(expr " + exprPrinter.print(stmt.expression) + ")";
    }
}

