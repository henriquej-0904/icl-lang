package values.primitive;

public class VString extends VPrimitive<String>
{

    public VString(String value) {
        super(value);
    }

    @Override
    public String show() {
        return new StringBuilder("\"").append(super.show()).append("\"").toString();
    }
    
}
