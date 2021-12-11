package types.primitves;

public class TypeBool extends TypePrimitive
{
    /**
     * @param classType
     */
    private TypeBool() {
        super(Boolean.class);
    }

    public static final TypeBool TYPE = new TypeBool();

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 2;
        
    }
}
