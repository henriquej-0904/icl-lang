package ast.record;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import compiler.RecordCodeBlock;
import environment.Environment;
import environment.ITypeEnvEntry;
import typeError.TypeErrorException;
import types.IType;
import types.TypeFunction;
import types.TypeRecord;
import types.TypeRef;
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
        Function<Pair<Bind,IType>, Map.Entry<String, IValue>> mapFunction =
        (field) ->
        {
            Bind bind = field.getLeft();
            return Map.entry(bind.getLeft(), bind.getRight().eval(e));
        };

        Map<String, IValue> recordMap = fields.stream()
        .map(mapFunction)
        .collect(Collectors.toUnmodifiableMap((entry) -> entry.getKey(), (entry) -> entry.getValue()));
    
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
       
        Environment<IType> env = e.beginScope();

	
		for (Pair<Bind,IType> field : this.fields)
		{
			IType declaredType = field.getRight();
			Bind bind = field.getLeft();
			IType actualType  = null;
			if(declaredType == null){
				actualType = bind.getRight().typecheck(env);
				env.assoc(new ITypeEnvEntry(bind.getLeft(), actualType));
			}
			else
			{
				IType innerType = declaredType;
				if (innerType instanceof TypeRef)
					innerType = ((TypeRef)innerType).getInnerType();
				
				boolean funcRecursive = (innerType instanceof TypeFunction) &&
					((TypeFunction)innerType).isRecursive();
				
				if (funcRecursive) {
					env.assoc(new ITypeEnvEntry(bind.getLeft(), declaredType));
					actualType = bind.getRight().typecheck(env);
					// Check if declared type equals the actual type
					if (!declaredType.equals(actualType))
						throw new TypeErrorException(
                            String.format("Illegal expression type in record def for bind with id '%s'. " +
                            "Declared type is '%s' and got '%s'.", bind.getLeft(), declaredType.show(),
                            actualType.show()));
				} else {
					actualType = bind.getRight().typecheck(env);
					if (!declaredType.equals(actualType))
						throw new TypeErrorException(
                            String.format("Illegal expression type in record def for bind with id '%s'. " +
                            "Declared type is '%s' and got '%s'.", bind.getLeft(), declaredType.show(),
                            actualType.show()));

					env.assoc(new ITypeEnvEntry(bind.getLeft(), declaredType));
				}
			}            
		}

		return this.type =  new TypeRecord(env);
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        // TODO Auto-generated method stub
        return null;
    }
}
