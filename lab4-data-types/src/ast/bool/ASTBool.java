package ast.bool;

import ast.ASTNode;
import compiler.MainCodeBlock;
import types.IType;
import types.primitves.TypeBool;
import util.Coordinates;
import util.Environment;
import values.IValue;
import values.primitive.VBool;

public class ASTBool implements ASTNode {

	private boolean val;

	public ASTBool(boolean val) {
		this.val = val;
	}

	public IValue eval(Environment<IValue> e) {
		return new VBool(this.val);
	}
	
	@Override
	public void compile(MainCodeBlock c, Environment<Coordinates> e) {
		if (this.val)
			c.emit("sipush 1");
		else
			c.emit("sipush 0");
	}

	@Override
	public IType typecheck(Environment<IType> e) {
		return TypeBool.TYPE;
	}

}