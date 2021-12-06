package ast.names;

import java.util.List;

import ast.ASTNode;
import compiler.FrameCodeBlock;
import compiler.MainCodeBlock;
import util.Bind;
import util.Coordinates;
import util.Environment;
import util.Pair;
import values.IValue;

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
			env.assoc(bind.getLeft(), bind.getRight().eval(env));
		}

		IValue value = body.eval(env);
		env.endScope();
		return value;
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
}
