import compiler.CompileBlock;
import interpreter.Environment;
public interface ASTNode {

    int eval(Environment e);

    void compile(CompileBlock c);
	
}

