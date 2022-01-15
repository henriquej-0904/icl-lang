package ast.record;

import java.util.List;

import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import environment.IValueEnvEntry;
import types.IType;
import util.Bind;
import values.IValue;
import values.VRecord;

public class ASTRecord extends ASTNodeAbstract
{
    private List<Bind> fields;

    /**
     * @param fields
     */
    public ASTRecord(List<Bind> fields) {
        this.fields = fields;
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        Environment<IValue> recordEnv = e.beginScope();

        for (Bind bind : fields)
            recordEnv.assoc(new IValueEnvEntry(bind.getLeft(), bind.getRight().eval(recordEnv)));

        return new VRecord(recordEnv);
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
    public StringBuilder toString(StringBuilder builder) {
        // TODO Auto-generated method stub
        return null;
    }
}
