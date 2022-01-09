package util;

import types.IType;

public class FunctionArg {
    public final String id;
    public final IType type;
    public FunctionArg(String id) {
        this.id = id;
        this.type = null;
    }
    public FunctionArg(String id, IType type) {
        this.id = id;
        this.type = type;
    }
    
}
