package ast.bool;

import ast.ASTNode;
import compiler.MainCodeBlock;
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
		//TODO:
        throw new Error("Not implemented");
	}

}