package ast.functions;

import java.util.List;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import types.IType;
import util.Environment;
import util.Utils;
import values.IValue;
import values.VFunction;

public class ASTFun extends ASTNodeAbstract
{
    private List<String> args;
    private ASTNode body;
    
    
    public ASTFun(List<String> args, ASTNode body) {
        this.args = args;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) {
      
        //TODO: Environment should be a copy.
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

    @Override
    public StringBuilder toString(StringBuilder builder) {
        builder.append("Function ");
        Utils.toStringList(this.args, null, null, builder);
        builder.append(" ->\n\t");
        this.body.toString(builder);
        builder.append("\nend\n");

        return builder;

    }

    
    
}
