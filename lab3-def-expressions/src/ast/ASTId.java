package ast;

import compiler.CodeBlock;
import util.Environment;

public class ASTId implements ASTNode{

    private String id;
    public ASTId(String id){
        this.id = id;
    }
    @Override
    public int eval(Environment<Integer> e) {
        return e.find(id);
    }

    @Override
    public void compile(CodeBlock c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
    
}
