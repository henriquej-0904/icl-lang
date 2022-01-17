package types;

import environment.Environment;
import environment.EnvironmentEntry;

public class TypeRecord  implements IType {

    public static final String TYPE_NAME = "Record";
    protected Environment<IType> fields;
    public TypeRecord(Environment<IType> fields){
        this.fields = fields;
    }
    @Override
    public String show() {
      return TYPE_NAME + " " +  fields.printLastScope();
       
    }

    @Override
    public String getJvmType() {
      
        return "Ljava/lang/Object;";
    }

    @Override

    public String toString(){
        return  TYPE_NAME;
    }

    @Override
    public int hashCode() {
        return show().hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if( this == obj)
            return true;
        if(!(obj instanceof TypeRecord))
            return false;
        TypeRecord other = (TypeRecord)obj;
        return this.fields.getLastScope().equals(other.fields.getLastScope());

    }

    public Iterable<EnvironmentEntry<IType>> getFields(){
        return fields.getLastScope();
    }

    public IType getFieldTypeFromRecord(String id){
        return fields.find(id, 0);
    }
}
