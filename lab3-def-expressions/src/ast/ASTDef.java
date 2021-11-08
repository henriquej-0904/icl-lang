package ast;

import compiler.CodeBlock;

import util.Environment;
import util.Pair;

import java.util.*;

public class ASTDef implements ASTNode{
    private List<Pair<String,ASTNode>> init;
    private ASTNode body;
    public ASTDef(List<Pair<String,ASTNode>> init, ASTNode body){
        this.init = init;
        this.body = body;
    }
    @Override
    public int eval(Environment<Integer> e) {
       Iterator<Pair<String,ASTNode>> it = init.iterator();
       Pair<String,ASTNode> pair = null;
       Environment<Integer> env = e.beginScope();
       while(it.hasNext()){
           pair = it.next();
           env.assoc(pair.getLeft(), pair.getRight().eval(env));
       }
       int toReturn = body.eval(env);
       env.endScope();
       return toReturn;
    }

    @Override
    public void compile(CodeBlock c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
    
}
