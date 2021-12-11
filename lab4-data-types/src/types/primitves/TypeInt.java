package types.primitves;

public class TypeInt extends TypePrimitive
{
    /**
     * @param classType
     */
    private TypeInt() {
        super(Integer.class);
    }

    public static final TypeInt TYPE = new TypeInt();

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 1;
        
    }

    
}
