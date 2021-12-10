import compiler.CompileBlock;

public class ASTSub extends ASTNodeAbstract {

ASTNode lhs, rhs;

        public int eval()
        { 
	int v1 = lhs.eval();
	int v2 = rhs.eval();
        return v1-v2; 
	}
    
        public ASTSub(ASTNode l, ASTNode r)
        {
		lhs = l; rhs = r;
        }

        @Override
        public void compile(CompileBlock c) {
                // TODO Auto-generated method stub
                lhs.compile(c);
                rhs.compile(c);
                c.emit("isub");
        }
}

