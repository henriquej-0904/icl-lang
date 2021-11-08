import compiler.CompileBlock;
import interpreter.Environment;
public class ASTNum implements ASTNode {

int val;

        public int eval(Environment e) { return val; }

        public ASTNum(int n)
        {
	   val = n;
        }

        @Override
        public void compile(CompileBlock c) {
                // TODO Auto-generated method stub
                c.emit("sipush " + val);
        }

}

