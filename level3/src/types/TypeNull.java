package types;

/**
 * A special type in the language that represents null.
 * This type cannot be used in any operation and throws a Typechecker Exception.
 */
public class TypeNull implements IType
{
    public static final String TYPE_NAME = "Null";
    public static final TypeNull TYPE = new TypeNull();

    private TypeNull() {}

    @Override
    public String show() {
        return "Null";
    }

    @Override
    public String getJvmType() {
        throw new Error("Trying to access TypeNull.getJvmType() for illegal type.");
    }
    
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String getSimpleName() {
        // TODO Auto-generated method stub
        return "null";
    }
}
