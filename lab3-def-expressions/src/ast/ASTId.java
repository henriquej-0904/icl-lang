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
        
        int currentFrameId = e.getDepth() - 1;

        //obter frame atual
        Coordinates coord = e.find(this.id);
        int varFrameId = coord.getLeft();
        String varId = coord.getRight();

        c.emitCurrentFrame();

        // obter ref frame da var.
        for (int i = currentFrameId; i > varFrameId; i--)
        {
            c.emit(String.format("getfield f%d/sl Lf%d;", i, i - 1));
        }

        c.emit(String.format("getfield f%d/%s I", varFrameId, varId) );
    }
    
}
