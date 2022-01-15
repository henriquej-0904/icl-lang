package values;

import types.IType;

public class VNull implements IValue {
    
    private VNull() {}

    public static final VNull VALUE = new VNull();

    @Override
    public String show() {
        return "Null";
    }

    @Override
    public IType getType() {
        return new IType() {

            @Override
            public String show() {
                return this.toString();
            }

            @Override
            public String getJvmType() {
                return "";
            }
            
            @Override
            public String toString() {

                return VNull.this.show();
            }
        };
    }

    
}
