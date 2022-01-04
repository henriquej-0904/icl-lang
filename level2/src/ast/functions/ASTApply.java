package ast.functions;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import typeError.TypeErrorException;
import types.IType;
import util.Environment;
import util.Utils;
import values.IValue;
import values.IValueEnvEntry;
import values.VFunction;

public class ASTApply extends ASTNodeAbstract{
    private ASTNode function;
    private List<ASTNode> args;

    public ASTApply(ASTNode function, List<ASTNode> args) {
        this.function = function;
        this.args = args;
    }
    
    @Override
    public IValue eval(Environment<IValue> e) {
        IValue toReturn = null;
        VFunction fun = checkRuntimeTypeVFunction(function.eval(e));
        Environment<IValue> funEnv = fun.getEnv().beginScope();
        Iterator<ASTNode> it = args.iterator();
        Iterator<String> names = fun.getArgs().iterator();
        if(args.size() != fun.getArgs().size()){
            throw new TypeErrorException("Incorrect number of args for function call. Expected " +  fun.getArgs().size() +" and got " + args.size() + " arguments.");
        }
        while(it.hasNext() && names.hasNext()){
          funEnv.assoc(new IValueEnvEntry(names.next(), it.next().eval(e)));
        }
        toReturn = fun.getBody().eval(funEnv);
        funEnv.endScope();
        return toReturn;

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
    
    protected VFunction checkRuntimeTypeVFunction(IValue value)
    {
        boolean checked = value instanceof VFunction;

        if (!checked)
            throw new TypeErrorException("Unexpected type. Type expected - function");

        return (VFunction)value;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        this.function.toString(builder);
        builder.append(" ");
        Utils.toStringList(args, (Consumer<ASTNode>)((arg) -> arg.toString(builder)), null, Utils.DEFAULT_DELIMITERS, builder);

        return builder;
    }

    
}
