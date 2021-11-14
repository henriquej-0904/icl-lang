package ast;

import compiler.MainCodeBlock;
import util.Coordinates;
import util.Environment;

public class ASTSub implements ASTNode {

        ASTNode lhs, rhs;

        public int eval(Environment<Integer> e) {
                int v1 = lhs.eval(e);
                int v2 = rhs.eval(e);
                return v1 - v2;
        }

        public ASTSub(ASTNode l, ASTNode r) {
                lhs = l;
                rhs = r;
        }

        @Override
        public void compile(MainCodeBlock c, Environment<Coordinates> e) {
                // TODO Auto-generated method stub
                lhs.compile(c, e);
                rhs.compile(c, e);
                c.emit("isub");
        }
}
