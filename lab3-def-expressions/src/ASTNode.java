import compiler.CompileBlock;

public interface ASTNode {

    int eval(Environment e);

    void compile(CompileBlock c);
	
}

