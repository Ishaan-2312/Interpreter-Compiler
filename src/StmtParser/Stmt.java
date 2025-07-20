package StmtParser;


import ExprParser.Expr;
import Lexer.Token;

public interface Stmt {
     <R> R accept(Visitor<R> visitor);

     interface Visitor<R>{
         R visitExpressionStmt(Expression stmt);
         R visitVarStmt(Var stmt);
     }

     class Expression implements Stmt{
         public final Expr expression;

         public Expression(Expr expression) {
             this.expression = expression;
         }

         @Override
         public <R> R accept(Visitor<R> visitor) {
             return visitor.visitExpressionStmt(this);
         }
     }

     class Var implements Stmt{
         public final Token name;
         public final Expr initializer;

         public Var(Token name, Expr initializer) {
             this.name = name;
             this.initializer = initializer;
         }

         @Override
         public <R> R accept(Visitor<R> visitor) {
             return visitor.visitVarStmt(this);
         }
     }
}
