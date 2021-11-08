package ast;
import compiler.CodeBlock;
import util.Environment;

public interface ASTNode {

    int eval(Environment<Integer> e);

    void compile(CodeBlock c);
	
}

