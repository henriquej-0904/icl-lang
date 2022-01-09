package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import types.IType;
import util.Environment;
import util.Pair;

public class ClosureCodeBlock extends MainCodeBlock {
    public static final String START = ".class public closure_%d\n" + 
    ".super	java/lang/Object\n" +	
    ".implements %s\n" + 
    ".field	public sl %s\n" + 
    ".method public	<init>()V\n" +
         "aload_0\n" + 	
         "invokenonvirtual	java/lang/Object/<init>()V\n " + 	
         "return" + 	
    ".end method";
    static MainCodeBlock main;

    public static final String APPLY = "";

    private int id;
    private String staticLink;
    private ClosureInterfaceCodeBlock closureInterface;
    private List<String> code;
    private String currFrame;
    public ClosureCodeBlock(int id, String staticLink, ClosureInterfaceCodeBlock closureInterface) {
        this.id = id;
        this.staticLink = staticLink;
        this.closureInterface = closureInterface;
        code = new LinkedList<>();
        currFrame = "aload " + closureInterface.getNumArgs() + 1;
    }


    @Override
    public Pair<Environment<Coordinates>, FrameCodeBlock> addFrame(Environment<Coordinates> env, int numFields) {
        return main.addFrame(env, numFields);
    }
    @Override
    public RefCodeBlock createRefClass(IType valueType) {
       return main.createRefClass(valueType);
    }
    @Override
    public void dump(File outputFolder) throws IOException, FileNotFoundException {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void emit(String opcode) {
       code.add(opcode);
        
    }
    @Override
    public void emitCurrentFrame() {
        emit(currFrame);
        
    }
    @Override
    public Environment<Coordinates> endFrame(Environment<Coordinates> env) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String getNewId() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public RefCodeBlock getRefClass(IType valueType) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void reachFrameIdFromCurrentFrame(int frameId) {
        // TODO Auto-generated method stub
        
    }
    


}
