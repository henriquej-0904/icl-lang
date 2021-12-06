package ast.memory;

import ast.ASTNode;
import compiler.MainCodeBlock;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.VCell;

public class ASTNew implements ASTNode
{
    protected ASTNode val;

    /**
     * @param val
     */
    public ASTNew(ASTNode val) {
        this.val = val;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        // TODO Auto-generated method stub
        throw new Error("Not implemented");
    }

    @Override
    public IValue eval(Environment<IValue> e) {
        return new VCell(this.val.eval(e));
    }

    
}
