package values;

import java.util.Map;
import java.util.NoSuchElementException;

import types.IType;
import types.TypeRecord;

public class VRecord implements IValue
{
    public final static String TYPE_NAME = TypeRecord.TYPE_NAME;

    private Map<String, IValue> record;

    public VRecord(Map<String, IValue> record)
    {
        this.record = record;
    }

    public IValue getValue(String id)
    {
        IValue value = this.record.get(id);
        if (value == null)
            throw new NoSuchElementException(id + " not declared.");

        return value;
    }

    @Override
    public String show() {
        return "Record = "  + record.toString();
    }

    @Override
    public String toString() {
       return show();
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

        @Override
        public String getSimpleName() {
            // TODO Auto-generated method stub
            return null;
        }
    };
    
}
