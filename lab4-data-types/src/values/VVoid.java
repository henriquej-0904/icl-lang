package values;

/**
 * Represents a void value, i.e., an IValue without a value.
 */
public class VVoid implements IValue
{
    private VVoid() {}

    @Override
    public String show() {
        return "";
    }

    /**
     * A void value.
     */
    public static final VVoid V_VOID = new VVoid();
    
}
