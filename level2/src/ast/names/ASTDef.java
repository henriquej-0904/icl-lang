package ast.names;

import java.util.List;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.FrameCodeBlock;
import compiler.MainCodeBlock;
import types.IType;
import types.ITypeEnvEntry;
import util.Bind;
import util.Environment;
import util.EnvironmentEntry;
import util.Pair;
import util.Utils;
import values.IValue;
import values.IValueEnvEntry;
import java.util.Iterator;
public class ASTDef extends ASTNodeAbstract {
	private List<Bind> init;
	private ASTNode body;
	private List<IType> types;
	public ASTDef(List<Bind> init, ASTNode body) {
		this.init = init;
		this.body = body;
	}

	public ASTDef(List<Bind> init, ASTNode body, List<IType> types) {
		this.init = init;
		this.body = body;
		this.types = types;
	}

	@Override
	public IValue eval(Environment<IValue> e)
	{
		Environment<IValue> env = e.beginScope();

		for (Bind bind : this.init)
		{
			env.assoc(new IValueEnvEntry(bind.getLeft(), bind.getRight().eval(env)));
		}

		IValue value = body.eval(env);
		env.endScope();
		return value;
	}

	@Override
	public IType typecheck(Environment<IType> e)
	{
		Environment<IType> env = e.beginScope();

		Iterator<IType> it = types.iterator();
	
		for (Bind bind : this.init)
		{
			env.assoc(new ITypeEnvEntry(bind.getLeft(), it.next()));
			bind.getRight().typecheck(env);
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
		for (Bind bind : this.init)
		{
			type = bind.getRight().getType();
			jvmType = type.getJvmType();
			frame.addFieldType(jvmType);

			Coordinates varCoord = new Coordinates(frame.getFrameId(), String.format(FrameCodeBlock.FIELD_NAME_FORMAT, i));
			newEnv.assoc(new EnvironmentEntry<>(bind.getLeft(), varCoord));
			
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
			(bind) -> {
				builder.append(bind.getLeft());
				builder.append('=');
				bind.getRight().toString(builder);
			},
			" ", null, builder);
		builder.append(" in\n\t");
		this.body.toString(builder);
		builder.append("\nend\n");

		return builder;
	}

	
}
