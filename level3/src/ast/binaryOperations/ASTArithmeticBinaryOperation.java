package ast.binaryOperations;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import ast.binaryOperations.operators.ArithmeticBinaryOperator;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import typeError.IllegalOperatorException;
import types.IType;
import types.primitives.TypeBool;
import types.primitives.TypeInt;
import types.primitives.TypePrimitive;
import types.primitives.TypeString;
import values.IValue;
import values.primitive.VBool;
import values.primitive.VInt;
import values.primitive.VPrimitive;
import values.primitive.VString;

public class ASTArithmeticBinaryOperation extends ASTNodeAbstract
{
    protected final ASTNode left, rigth;

    protected final ArithmeticBinaryOperator operator;

    /**
     * @param left
     * @param rigth
     */
    public ASTArithmeticBinaryOperation(ASTNode left, ASTNode rigth, ArithmeticBinaryOperator operator) {
        this.left = left;
        this.rigth = rigth;
        this.operator = operator;
       
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        if( left.getType() instanceof TypeInt)
            compileInt(c, e);
        else 
            compileString(c, e);
    }

    private void compileInt(MainCodeBlock c, Environment<Coordinates> e){
        this.left.compile(c, e);
        this.rigth.compile(c, e);
        switch(this.operator)
        {
            case ADD:
                c.emit("iadd");
                break;
            case DIV:
                c.emit("idiv");
                break;
            case MUL:
                c.emit("imul");
                break;
            case SUB:
                c.emit("isub");
                break;
        }
    }

    private void compileString(MainCodeBlock c, Environment<Coordinates> e){
        
        c.emit("new java/lang/StringBuilder");
        c.emit("dup");
        c.emit("invokespecial java/lang/StringBuilder/<init>()V");
        this.left.compile(c, e);
        c.emit("invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        this.rigth.compile(c, e);
        if(rigth.getType() instanceof TypeInt)
            c.emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
        c.emit("invokevirtual java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        c.emit("invokevirtual java/lang/StringBuilder/toString()Ljava/lang/String;");
           

    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VPrimitive<?> val1 = checkRuntimeType(this.left.eval(e));
        VPrimitive<?> val2 = checkRuntimeType(this.rigth.eval(e));

        if (val1 instanceof VInt)
            return evalInt((VInt)val1, val2);
        
        return evalString((VString)val1, val2);
    }

    private IValue evalInt(VInt value1, VPrimitive<?> value2)
    {
        VInt valueInt2 = checkRuntimeTypeInt(value2);
        IValue result = null;

        switch(this.operator)
        {
            case ADD:
                result = new VInt(value1.getValue() + valueInt2.getValue());
                break;
            case DIV:
                result = new VInt(value1.getValue() / valueInt2.getValue());
                break;
            case MUL:
                result = new VInt(value1.getValue() * valueInt2.getValue());
                break;
            case SUB:
                result = new VInt(value1.getValue() - valueInt2.getValue());
                break;
        }

        return result;
    }

    private IValue evalString(VString value1, VPrimitive<?> value2)
    {
        IValue result = null;

        switch(this.operator)
        {
            case ADD:
                result = new VString(value1.getValue() + value2.getValue());
                break;
            default:
                throw new IllegalOperatorException(operator.getOperator(), TypeString.TYPE.show());
        }

        return result;
    }

    @Override
    public IType typecheck(Environment<IType> e) {
       TypePrimitive type1 =  checkType(this.left.typecheck(e));
       TypePrimitive type2 = checkType(this.rigth.typecheck(e));
       if( type1 instanceof TypeString){
           if(this.operator == ArithmeticBinaryOperator.ADD)
                return this.type = TypeString.TYPE;
            
            else
                throw new IllegalOperatorException(operator.getOperator(), TypeString.TYPE.show());
       }
           
        else{
            // type 1 is TypeInt
            if(type2 instanceof TypeInt)
                return this.type = TypeInt.TYPE;
            
            else 
             throw new IllegalOperatorException(this.operator.getOperator(), TypeInt.TYPE.show(), type2.show());

        }
    }

    protected VPrimitive<?> checkRuntimeType(IValue val)
    {
        boolean checked = (val instanceof VPrimitive) && !(val instanceof VBool);

        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), "Integer or String", val.getType().show());

        return (VPrimitive<?>)val;
    }

    protected VInt checkRuntimeTypeInt(IValue val)
    {
        boolean checked = (val instanceof VInt);

        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), TypeInt.TYPE.show(), val.getType().show());

        return (VInt)val;
    }

    protected TypePrimitive checkType(IType type)
    {
        boolean checked = (type instanceof TypePrimitive) && !(type instanceof TypeBool);

        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator(), "Integer or String", type.show());

        return (TypePrimitive)type;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        this.left.toString(builder);
        builder.append(' ');
        builder.append(this.operator.getOperator());
        builder.append(' ');
        this.rigth.toString(builder);

        return builder;
    }
}
