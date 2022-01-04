package ast.functions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.MainCodeBlock;
import types.IType;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VFunction;

public class ASTFun extends ASTNodeAbstract{
    private List<String> args;
    private ASTNode body;
    
    
    public ASTFun(List<String> args, ASTNode body) {
        this.args = args;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) {
      
        return new VFunction(args, body, e);
    }

  

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IType getType() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
