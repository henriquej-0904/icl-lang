package ast.record;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import compiler.RecordCodeBlock;
import environment.Environment;
import typeError.TypeErrorException;
import types.IType;
import types.TypeRecord;

import util.Bind;
import util.Pair;
import values.IValue;
import values.VRecord;

public class ASTRecord extends ASTNodeAbstract
{
    private List<Pair<Bind,IType>> fields;

    /**
     * @param fields
     */
    public ASTRecord(List<Pair<Bind,IType>> fields) {
        this.fields = fields;
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        
        Map<String, IValue> recordMap = fields.stream()
        .collect(Collectors.toMap(
            (field) -> field.getLeft().getLeft(),
            (field) -> field.getLeft().getRight().eval(e),
            (field1,field2) -> field1,
            ()-> {
                return new LinkedHashMap<>(fields.size());
            }   
        ));

        if(recordMap.size() != fields.size())
            throw new TypeErrorException("Cannot have repeated names for diferent fields in the same record");
        return new VRecord(recordMap);
       
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
       RecordCodeBlock record =  c.createRecord((TypeRecord)this.type);
       for (Pair<Bind,IType> pair : fields) {
           Bind bind = pair.getLeft();
           bind.getRight().compile(c, e);
           c.emit(String.format("putfield %s/%s %s", record.className,bind.getLeft(), bind.getRight().getType().getJvmType()));
           c.emit("dup");
       }
       c.emit("pop");

    }

    @Override
    public IType typecheck(Environment<IType> e) {
       
       
        Map<String, IType> recordMap = fields.stream()
        .collect(Collectors.toMap(
            (field) -> field.getLeft().getLeft(),
            (field) -> {
                IType declaredType = field.getRight();
                Bind bind = field.getLeft();
                IType actualType  = null;
                if(declaredType == null){
                    actualType = bind.getRight().typecheck(e);
                }
                else
                {
                actualType = bind.getRight().typecheck(e);
                // Check if declared type equals the actual type
                if (!declaredType.equals(actualType))
                    throw new TypeErrorException(
                        String.format("Illegal expression type in record def for bind with id '%s'. " +
                        "Declared type is '%s' and got '%s'.", bind.getLeft(), declaredType.show(),
                        actualType.show()));
                } 
                return actualType;           
            },
            (field1,field2) -> field1,
            ()-> {
                return new LinkedHashMap<>(fields.size());
            }   
        ));

        if(recordMap.size() != fields.size())
            throw new TypeErrorException("Cannot have repeated names for diferent fields in the same record");
		return this.type =  new TypeRecord(recordMap);
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        // TODO Auto-generated method stub
        return null;
    }
}
