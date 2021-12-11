package ast.memory;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.MainCodeBlock;
import compiler.RefCodeBlock;
import typeError.TypeErrorException;
import types.IType;
import types.TypeRef;
import types.TypeVoid;
import types.primitves.TypePrimitive;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;
import values.VVoid;

public class ASTNew extends ASTNodeAbstract {
    protected ASTNode val;

    /**
     * @param val
     */
    public ASTNew(ASTNode val) {
        this.val = val;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
       RefCodeBlock ref = c.createRefClass(val.getType());
       String className = ref.getClassName();
        c.emit("new " + className);
        c.emit("dup");
        c.emit(String.format("invokespecial %s/<init>()V", className));
        c.emit("dup");
        val.compile(c, e);
        c.emit(String.format("putfield %s/v %s", className, ref.getValueFieldType()));
    }

    @Override
    public IValue eval(Environment<IValue> e) {
        return new VCell(checkRuntimeType(this.val.eval(e)));
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        type = new TypeRef(checkType(this.val.typecheck(e)));
        return type;
    }

    protected IValue checkRuntimeType(IValue value) {
        boolean voidValue = value instanceof VVoid;

        if (voidValue)
            throw new TypeErrorException("Incompatible type. Cannot create reference to 'void'");

        return value;
    }

    protected IType checkType(IType type) {
        boolean voidType = type instanceof TypeVoid;

        if (voidType)
            throw new TypeErrorException("Incompatible type. Cannot create reference to 'void'");

        return type;
    }
}
