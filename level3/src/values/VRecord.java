package values;

import environment.Environment;

public class VRecord implements IValue
{
    private Environment<IValue> env;

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
    
}
