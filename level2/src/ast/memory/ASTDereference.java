package ast.memory;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import compiler.RefCodeBlock;
import environment.Environment;
import typeError.IllegalOperatorException;
import types.IType;
import types.TypeRef;
import util.Utils;
import values.IValue;
import values.VCell;

public class ASTDereference extends ASTNodeAbstract
{
    public static final String OPERATOR = "!";

    protected ASTNode reference;

    /**
     * @param reference
     */
    public ASTDereference(ASTNode reference) {
        this.reference = reference;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
       reference.compile(c, e);
       RefCodeBlock ref = c.getRefClass(((TypeRef)reference.getType()).getValueType());
       c.emit(String.format("getfield %s/v %s", ref.getClassName(), ref.getValueFieldTypeJVM()));
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        return checkRuntimeType(this.reference.eval(e)).getValue();
    }

    @Override
    public IType typecheck(Environment<IType> e) {
         type = checkType(this.reference.typecheck(e)).getValueType();
         return type;
    }

    protected VCell checkRuntimeType(IValue value)
    {
        boolean checked = value instanceof VCell;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, "Ref", value.show());

        return (VCell)value;
    }

    protected TypeRef checkType(IType type)
    {
        boolean checked =  Utils.checkType(type, TypeRef.class);

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, "Ref", type.show());

        return (TypeRef)type;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        builder.append(OPERATOR);
        this.reference.toString(builder);
        return builder;
    }

    
}
