import java.nio.channels.UnsupportedAddressTypeException;
import interpreter.Environment;
import compiler.CompileBlock;

public class ASTId implements ASTNode{

    private String id;
    public ASTId(String id){
        this.id = id;
    }
    @Override
    public int eval(Environment e) {
        // TODO Auto-generated method stub
        return e.find(id);
    }

    @Override
    public void compile(CompileBlock c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }
    
}
