package types;

public class TypeVoid implements IType
{
    public static final String TYPE_STR = "void";

    private TypeVoid() {}

    @Override
    public String show() {
        return TYPE_STR;
    }

    @Override
    public boolean equals(Object obj)
    {
        // there is only 1 instance of this class in this program.
        return super.equals(obj);
    }

    public static final TypeVoid TYPE = new TypeVoid();

    @Override
    public String toString(){
        return show();
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 3;
        
    }
}
