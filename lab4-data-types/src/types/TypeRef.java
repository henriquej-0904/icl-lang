package types;

public class TypeRef implements IType
{
    public static final String TYPE = "Ref";

    protected IType valueType;

    /**
     * @param valueType
     */
    public TypeRef(IType valueType) {
        this.valueType = valueType;
    }

    @Override
    public String show() {
        return TYPE;
    }

    public IType getValueType()
    {
        return this.valueType;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean checked = super.equals(obj);

        if (checked)
            return true;
        
        checked = obj instanceof TypeRef;

        // if it is not a reference
        if (!checked)
            return false;

        TypeRef otherRef = (TypeRef)obj;

        // check if the types of the values in the references are equal.
        return getValueType().equals(otherRef.getValueType());
    }
}
