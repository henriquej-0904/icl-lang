package compiler;

import java.io.PrintStream;

public abstract class CodeBlock {
    public final String className;
    
    protected CodeBlock(String className){
        this.className = className;
    }

    public abstract void dump(PrintStream f);
}
