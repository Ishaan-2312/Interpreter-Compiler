package StmtParser;


import ExprParser.Expr;
import Lexer.Token;

import java.util.List;

public interface Stmt {
     <R> R accept(Visitor<R> visitor);

     interface Visitor<R>{
         R visitExpressionStmt(Expression stmt);
         R visitVarStmt(Var stmt);
         R visitBlockStmt(Block stmt);
         R visitIfBlock(IfBlock stmt);
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

     class Block implements Stmt{
         public final List<Stmt> statements;

         public Block(List<Stmt> statements) {
             this.statements = statements;
         }

         @Override
         public <R> R accept(Visitor<R> visitor) {
             return visitor.visitBlockStmt(this);
         }
     }

     class IfBlock implements Stmt{
         public final Expr conditionExpr;
         public final Stmt ifStmt;
         public final Stmt elseStmt;

         public IfBlock(Expr conditionExpr, Stmt ifStmt, Stmt elseStmt) {
             this.conditionExpr = conditionExpr;
             this.ifStmt = ifStmt;
             this.elseStmt = elseStmt;
         }

         @Override
         public <R> R accept(Visitor<R> visitor) {
             return visitor.visitIfBlock(this);
         }
     }

}
