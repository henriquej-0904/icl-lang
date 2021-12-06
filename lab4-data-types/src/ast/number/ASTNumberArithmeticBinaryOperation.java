package ast.number;

import ast.ASTNode;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VInt;

public class ASTNumberArithmeticBinaryOperation implements ASTNode
{
    protected final ASTNode left, rigth;

    protected final NumberArithmeticBinaryOperator operator;

    /**
     * @param left
     * @param rigth
     */
    public ASTNumberArithmeticBinaryOperation(ASTNode left, ASTNode rigth, NumberArithmeticBinaryOperator operator) {
        this.left = left;
        this.rigth = rigth;
        this.operator = operator;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
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

    @Override
    public IValue eval(Environment<IValue> e)
    {
        VInt val1 = checkType(this.left.eval(e));
        VInt val2 = checkType(this.rigth.eval(e));

        IValue result = null;
        switch(this.operator)
        {
            case ADD:
                result = new VInt(val1.getValue() + val2.getValue());
                break;
            case DIV:
                result = new VInt(val1.getValue() / val2.getValue());
                break;
            case MUL:
                result = new VInt(val1.getValue() * val2.getValue());
                break;
            case SUB:
                result = new VInt(val1.getValue() - val2.getValue());
                break;
        }

        return result;
    }

    protected VInt checkType(IValue val)
    {
        boolean checked = val instanceof VInt;
        if (!checked)
            throw new IllegalOperatorException(this.operator.getOperator());

        return (VInt)val;
    }

}