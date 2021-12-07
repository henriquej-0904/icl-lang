package ast.names;

import java.util.List;

import ast.ASTNode;
import compiler.FrameCodeBlock;
import compiler.MainCodeBlock;
import typeError.TypeErrorException;
import types.IType;
import types.TypeVoid;
import util.Bind;
import util.Coordinates;
import util.Environment;
import util.Pair;
import values.IValue;
import values.VVoid;

public class ASTDef implements ASTNode {
	private List<Bind> init;
	private ASTNode body;

	public ASTDef(List<Bind> init, ASTNode body) {
		this.init = init;
		this.body = body;
	}

	@Override
	public IValue eval(Environment<IValue> e)
	{
		Environment<IValue> env = e.beginScope();

		for (Bind bind : this.init)
		{
			env.assoc(bind.getLeft(), checkRuntimeTypeValue(bind.getRight().eval(env)));
		}

		IValue value = body.eval(env);
		env.endScope();
		return value;
	}

	@Override
	public IType typecheck(Environment<IType> e)
	{
		Environment<IType> env = e.beginScope();

		for (Bind bind : this.init)
		{
			env.assoc(bind.getLeft(), checkTypeValue(bind.getRight().typecheck(env)));
		}

		IType type = body.typecheck(env);
		env.endScope();
		return type;
	}

	@Override
	public void compile(MainCodeBlock c, Environment<Coordinates> e)
	{
		Pair<Environment<Coordinates>, Integer> pairEnvFrameId = c.addFrame(this.init.size(), e);
		Environment<Coordinates> newEnv = pairEnvFrameId.getLeft();
		int frameId = pairEnvFrameId.getRight();

		int i = 0;
		for (Bind bind : this.init)
		{
			Coordinates varCoord = new Coordinates(frameId, String.format(FrameCodeBlock.VARIABLE_NAME, i));

			c.emitCurrentFrame();
			bind.getRight().compile(c, newEnv);
			c.emit(String.format("putfield f%d/%s I", frameId, varCoord.getRight()));

			newEnv.assoc(bind.getLeft(), varCoord);
			i++;
		}

		this.body.compile(c, newEnv);
		c.endFrame(newEnv);
	}

	protected IValue checkRuntimeTypeValue(IValue value)
    {
        boolean voidValue = value instanceof VVoid;

        if (voidValue)
            throw new TypeErrorException("Cannot bind non values (void).");

        return value;
    }

	protected IType checkTypeValue(IType type)
    {
        boolean voidValue = type instanceof TypeVoid;

        if (voidValue)
            throw new TypeErrorException("Cannot bind non values (void).");

        return type;
    }
}
