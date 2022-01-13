package values;

public class Vnull implements IValue{

    private Vnull(){
    }
    public static Vnull VALUE = new Vnull();
    @Override
    public String show() {
        // TODO Auto-generated method stub
        return "null";
    }
    
}
