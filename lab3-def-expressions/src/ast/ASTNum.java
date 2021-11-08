package ast;
import compiler.CodeBlock;
import util.Environment;

public class ASTNum implements ASTNode {

int val;

        public int eval(Environment<Integer> e) { return val; }

        public ASTNum(int n)
        {
	   val = n;
        }

        @Override
        public void compile(CodeBlock c) {
                // TODO Auto-generated method stub
                c.emit("sipush " + val);
        }

}

