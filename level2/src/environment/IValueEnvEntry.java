package environment;

import values.IValue;

public class IValueEnvEntry extends EnvironmentEntry<IValue> {

    public IValueEnvEntry(String id, IValue value) {
        super(id, value);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getLeft());
        builder.append('=');
        builder.append(getRight().show());

        return builder.toString();
    }
}
