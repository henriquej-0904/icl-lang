package ast;

import compiler.MainCodeBlock;
import util.Coordinates;
import util.Environment;

public class ASTId implements ASTNode{

    private String id;
    public ASTId(String id){
        this.id = id;
    }
    @Override
    public int eval(Environment<Integer> e) {
        return e.find(id);
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
        
        //TODO: Frame atual pode nao ser a depth -1. Frame anterior ao atual pode nao ser o atual -1.

        Coordinates coord = e.find(this.id);
        int varFrameId = coord.getLeft();
        String varId = coord.getRight();

        c.emitCurrentFrame();
        c.reachFrameIdFromCurrentFrame(varFrameId);
        c.emit(String.format("getfield f%d/%s I", varFrameId, varId) );
    }
    
}
