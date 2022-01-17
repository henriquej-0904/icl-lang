package types.primitives;

public class TypeBool extends TypePrimitive implements TypeJavaPrimitive
{
    private static final String JVM_TYPE = "I";

    /**
     * @param classType
     */
    private TypeBool() {
        super(Boolean.class);
    }

    public static final TypeBool TYPE = new TypeBool();

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public String getJvmType() {
        return JVM_TYPE;
    }
}
