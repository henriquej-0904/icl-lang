package ast.functions;

import java.util.ArrayList;
import java.util.List;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import types.IType;
import types.ITypeEnvEntry;
import types.TypeFunction;
import util.Environment;
import util.FunctionArg;
import util.Utils;
import values.IValue;
import values.VFunction;

public class ASTFun extends ASTNodeAbstract
{
    private List<FunctionArg> args;
   
    private ASTNode body;
    
    
    public ASTFun(List<FunctionArg> args, ASTNode body) {
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
        // Assocs
        // createClosure
        // compile body
        // endClosure
    }

    @Override
    public IType typecheck(Environment<IType> e) {
      Environment<IType> env = e.beginScope();
      List<IType> types = new ArrayList<>(args.size());
      for (FunctionArg arg : args) {
          env.assoc(new ITypeEnvEntry(arg.id, arg.type));
          types.add(arg.type);
      }
      IType funReturnType = body.typecheck(env);
      this.type = new TypeFunction(types, funReturnType);
    return type;
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
