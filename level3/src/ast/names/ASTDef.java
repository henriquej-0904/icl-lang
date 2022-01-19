package ast.names;

import java.util.List;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.FrameCodeBlock;
import compiler.MainCodeBlock;
import environment.Environment;
import typeError.TypeErrorException;
import types.IType;
import types.TypeFunction;
import types.TypeRecord;
import types.TypeRef;
import util.Bind;
import util.Pair;
import util.Utils;
import values.IValue;

public class ASTDef extends ASTNodeAbstract
{
	private List<Pair<Bind,IType>> init;
	private ASTNode body;

	public ASTDef(List<Pair<Bind,IType>> init, ASTNode body) {
		this.init = init;
		this.body = body;
	}

	@Override
	public IValue eval(Environment<IValue> e)
	{
		Environment<IValue> env = e.beginScope();

		for (Pair<Bind,IType> field : this.init){
			Bind bind = field.getLeft();
			
			env.assoc(bind.getLeft(), Utils.requireNonNull(bind.getRight().eval(env)));
		}
			

		IValue value = body.eval(env);
		env.endScope();
		return value;
	}

	@Override
	public IType typecheck(Environment<IType> e)
	{
		Environment<IType> env = e.beginScope();

	
		for (Pair<Bind,IType> field : this.init)
		{
			IType declaredType = field.getRight();
			Bind bind = field.getLeft();
			IType actualType  = null;

			if(declaredType == null)
			{
				actualType = bind.getRight().typecheck(env);
				Utils.requireNonNull(actualType);
				env.assoc(bind.getLeft(), actualType);
			}
			else
			{
				IType innerType = declaredType;
				if (innerType instanceof TypeRef)
					innerType = ((TypeRef)innerType).getInnerType();
				
				boolean funcRecursive = (innerType instanceof TypeFunction) &&
					((TypeFunction)innerType).isRecursive();
				
				// allows to define recursive functions inside records.
				if (funcRecursive || innerType instanceof TypeRecord) {
					env.assoc(bind.getLeft(), declaredType);
					actualType = bind.getRight().typecheck(env);
					Utils.requireNonNull(actualType);
					// Check if declared type equals the actual type
					if (!declaredType.equals(actualType))
						throw new TypeErrorException(
								String.format("Illegal expression type in def for bind with id '%s'. " +
										"Declared type is '%s' and got '%s'.", bind.getLeft(), declaredType.show(),
										actualType.show()));
				} else {
					actualType = bind.getRight().typecheck(env);
					Utils.requireNonNull(actualType);
					if (!declaredType.equals(actualType))
						throw new TypeErrorException(
								String.format("Illegal expression type in def for bind with id '%s'. " +
										"Declared type is '%s' and got '%s'.", bind.getLeft(), declaredType.show(),
										actualType.show()));

					env.assoc(bind.getLeft(), declaredType);
				}
			}
		}

		IType bodyType = body.typecheck(env);
		type = bodyType;
		env.endScope();
		return bodyType;
	}

	@Override
	public void compile(MainCodeBlock c, Environment<Coordinates> e)
	{
		Pair<Environment<Coordinates>, FrameCodeBlock> pairEnvFrameId = c.addFrame(e, this.init.size());
		Environment<Coordinates> newEnv = pairEnvFrameId.getLeft();
		FrameCodeBlock frame = pairEnvFrameId.getRight();

		IType type;
		String jvmType;
		int i = 0;
		for (Pair<Bind,IType> field : this.init)
		{
			Bind bind = field.getLeft();
			type = bind.getRight().getType();
			jvmType = type.getJvmType();
			frame.addFieldType(jvmType);

			Coordinates varCoord = new Coordinates(frame.getFrameId(), String.format(FrameCodeBlock.FIELD_NAME_FORMAT, i));
			newEnv.assoc(bind.getLeft(), varCoord);
			
			c.emitCurrentFrame();
			bind.getRight().compile(c, newEnv);
			c.emit(String.format("putfield f%d/%s %s", frame.getFrameId(), varCoord.getRight(), jvmType));

			i++;
		}

		this.body.compile(c, newEnv);
		c.endFrame(newEnv);
	}

	@Override
	public StringBuilder toString(StringBuilder builder) {
		builder.append("def ");
		Utils.toStringList(this.init, 
			(field) -> {
				builder.append(field.getLeft().getLeft());
				builder.append('=');
				field.getLeft().getRight().toString(builder);
			},
			" ", null, builder);
		builder.append(" in\n\t");
		this.body.toString(builder);
		builder.append("\nend\n");

		return builder;
	}
}
