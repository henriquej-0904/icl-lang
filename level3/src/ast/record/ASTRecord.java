package ast.record;

import java.util.Iterator;
import java.util.List;

import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import environment.ITypeEnvEntry;
import environment.IValueEnvEntry;
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
        Environment<IValue> recordEnv = e.beginScope();

        for (Pair<Bind,IType> field : fields){
            Bind bind = field.getLeft();
            recordEnv.assoc(new IValueEnvEntry(bind.getLeft(), bind.getRight().eval(recordEnv)));
        }
           

        return new VRecord(recordEnv);
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e) {
      
      
        
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
			else{
				env.assoc(new ITypeEnvEntry(bind.getLeft(), declaredType));
			 actualType = bind.getRight().typecheck(env);
			// Check if declared type equals the actual type
			if (!declaredType.equals(actualType))
				throw new TypeErrorException(
					String.format("Illegal expression type in record def for bind with id '%s'. " +
						"Declared type is '%s' and got '%s'.", bind.getLeft(), declaredType.show(),
						actualType.show())
				);
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
