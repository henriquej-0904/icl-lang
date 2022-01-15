package values.primitive;

import types.primitives.TypeInt;
import types.primitives.TypePrimitive;

public final class VInt extends VPrimitive<Integer>
{
    private static final TypePrimitive type = TypeInt.TYPE;

    /**
     * @param value
     */
    public VInt(int value) {
        super(value);
    }

    @Override
    public TypePrimitive getPrimitiveType() {
        return type;
    }
}
