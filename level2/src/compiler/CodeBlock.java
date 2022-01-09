package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import types.IType;
import util.Environment;
import util.Pair;

public interface CodeBlock {


    public String getNewId();

    public void emit(String opcode);


    public void emitCurrentFrame();

    /**
     * 
     * @param env
     * @return The new Environment.
     */
    public Environment<Coordinates> endFrame(Environment<Coordinates> env);

    /**
     * 
     * @return Pair(New Environment, new Frame).
     */
    public Pair<Environment<Coordinates>, FrameCodeBlock>
        addFrame(Environment<Coordinates> env, int numFields);

    public void reachFrameIdFromCurrentFrame(int frameId);

    public void dump(File outputFolder) throws IOException, FileNotFoundException;

    /**
     * Creates a new Reference Code Block with the specified value type.
     * 
     * @param valueType - The type of value this reference points.
     * 
     * @return A new Reference Code Block with the specified value type.
     */
    public RefCodeBlock createRefClass(IType valueType);

    
    /**
     * Get a Reference Code Block with the specified value type.
     * 
     * @param valueType - The type of value this reference points.
     * 
     * @return A Reference Code Block with the specified value type.
     */
    public RefCodeBlock getRefClass(IType valueType);


}
