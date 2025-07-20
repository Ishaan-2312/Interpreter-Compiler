package ExprParser;

public class ASTPrinter implements Expr.Visitor<String>{


    public String print(Expr expr){
        return expr.accept(this);
    }
    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme,expr.left,expr.right);
    }

     String parenthesize(String name, Expr... exprs) {
        StringBuilder builder=new StringBuilder();
         builder.append("(").append(name);
         for (Expr expr : exprs) {
             builder.append(" ").append(expr.accept(this));
         }
         builder.append(")");
         return builder.toString();
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "null";
        return expr.value.toString();
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return "";
    }

}
