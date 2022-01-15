package values.primitive;

import types.primitives.TypeBool;
import types.primitives.TypePrimitive;

public final class VBool extends VPrimitive<Boolean>
{
    private final static TypePrimitive type = TypeBool.TYPE;

    /**
     * @param value
     */
    public VBool(boolean value) {
        super(value);
    }

    @Override
    public TypePrimitive getPrimitiveType() {
        return type;
    }
    
}
