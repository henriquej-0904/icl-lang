package ast.record;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import typeError.IllegalOperatorException;
import types.IType;
import types.TypeRecord;
import values.IValue;
import values.VRecord;

public class ASTGetRecordValue extends ASTNodeAbstract
{
    private static final String OPERATOR = ".";

    private final ASTNode record;
    private final String id;

    /**
     * @param record
     * @param id
     */
    public ASTGetRecordValue(ASTNode record, String id) {
        this.record = record;
        this.id = id;
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VRecord record = checkRuntimeTypeRecord(this.record.eval(e));
        return record.getValue(this.id);
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IType typecheck(Environment<IType> e) {
     TypeRecord type = checkTypeRecord(record.typecheck(e));
        return this.type = type.getFieldTypeFromRecord(id);

    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        // TODO Auto-generated method stub
        return null;
    }

    protected VRecord checkRuntimeTypeRecord(IValue val)
    {
        boolean checked = val instanceof VRecord;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, TypeRecord.TYPE_NAME, val.getType().show());

        return (VRecord)val;
    }

    protected TypeRecord checkTypeRecord(IType type)
    {
        boolean checked = type instanceof TypeRecord;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, TypeRecord.TYPE_NAME,type.show());

        return (TypeRecord)type;
    }
    
}
