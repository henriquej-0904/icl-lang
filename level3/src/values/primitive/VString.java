package values.primitive;

import types.primitives.TypePrimitive;
import types.primitives.TypeString;

public final class VString extends VPrimitive<String>
{
    private final static TypePrimitive type = TypeString.TYPE;

    public VString(String value) {
        super(value);
    }

    @Override
    public String show() {
        return new StringBuilder("\"").append(super.show()).append("\"").toString();
    }

    @Override
    public TypePrimitive getPrimitiveType() {
        return type;
    }
    
}
