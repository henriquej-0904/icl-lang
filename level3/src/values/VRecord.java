package values;

import environment.Environment;
import types.IType;

public class VRecord implements IValue
{
    private final Environment<IValue> env;

    public VRecord(Environment<IValue> env)
    {
        this.env = env;
    }

    public IValue getValue(String id)
    {
        return this.env.find(id, 0);
    }

    @Override
    public String show() {
        return this.env.printLastScope();
    }

    @Override
    public IType getType() {
        return TYPE;
    }

    public static final IType TYPE = new IType() {

        @Override
        public String show() {
            return this.toString();
        }

        @Override
        public String getJvmType() {
            return "";
        }

        @Override
        public String toString() {

            return "Record";
        }
    };
    
}
