import compiler.CompileBlock;

public class ASTNum extends ASTNodeAbstract {

int val;

        public int eval() { return val; }

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

