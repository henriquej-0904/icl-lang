import compiler.CompileBlock;

public class ASTAdd implements ASTNode {

ASTNode lhs, rhs;

        public int eval(Environment e)
        { 
	int v1 = lhs.eval(e);
	int v2 = rhs.eval(e);
        return v1+v2; 
	}
    
        public ASTAdd(ASTNode l, ASTNode r)
        {
		lhs = l; rhs = r;
        }

        @Override
        public void compile(CompileBlock c) {
                // TODO Auto-generated method stub
                lhs.compile(c);
                rhs.compile(c);
                c.emit("iadd");
        }
}

