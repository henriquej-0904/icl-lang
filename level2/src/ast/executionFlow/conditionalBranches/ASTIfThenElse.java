package ast.executionFlow.conditionalBranches;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import typeError.IllegalOperatorException;
import typeError.TypeErrorException;
import types.IType;
import types.primitives.TypeBool;
import util.Environment;
import util.Utils;
import values.IValue;
import values.Vnull;
import values.primitive.VBool;

public class ASTIfThenElse extends ASTNodeAbstract {
    public static final String OPERATOR = "if then else";

    protected ASTNode ifNode, thenNode, elseNode;

    /**
     * @param ifNode
     * @param thenNode
     * @param elseNode
     */
    public ASTIfThenElse(ASTNode ifNode, ASTNode thenNode, ASTNode elseNode) {
        this.ifNode = ifNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        String l1, l2;
        l1 = c.getNewFrameId();
        l2 = c.getNewFrameId();
        
        ifNode.compile(c, e);
        c.emit("ifeq " + l1);
        thenNode.compile(c, e);
        c.emit("goto " + l2);
        c.emit(l1 + ": ");
        elseNode.compile(c, e);
        c.emit(l2 + ":");
        
    }

    @Override
    public IValue eval(Environment<IValue> e) {
        Boolean value =  checkIfRuntimeType(this.ifNode.eval(e)).getValue();
        if(value)
           return this.thenNode.eval(e);
        else if( elseNode != null)
            return this.elseNode.eval(e);
        return Vnull.VALUE;
    }

    @Override
    public IType typecheck(Environment<IType> e) {
        checkTypeIf(this.ifNode.typecheck(e));

        IType thenType = this.thenNode.typecheck(e);
        if(elseNode != null){
            IType elseType = this.elseNode.typecheck(e);

        if (!thenType.equals(elseType))
            throw new TypeErrorException(String.format("Incompatible types for if statement. The types of" +
                    " the then and else branches must be equal.\nThen branch type: %s\nElse branch type: %s\n",
                    thenType.show(), elseType.show()));

        }
        type = thenType;
        return thenType;
    }

    protected VBool checkIfRuntimeType(IValue value) {
        boolean checked = value instanceof VBool;

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, TypeBool.TYPE.show(), value.show());

        return (VBool) value;
    }

    protected TypeBool checkTypeIf(IType type) {
        boolean checked = Utils.checkType(type, TypeBool.class);

        if (!checked)
            throw new IllegalOperatorException(OPERATOR, TypeBool.TYPE.show(), type.show());

        return (TypeBool) type;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        builder.append("if (");
        this.ifNode.toString(builder);
        builder.append(")\nthen\n\t");
        this.thenNode.toString(builder);
        builder.append("\nelse\n\t");
        this.elseNode.toString(builder);
        builder.append("\nend\n");

        return builder;
    }

    

}
