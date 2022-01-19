package ast.print;

import java.util.function.Consumer;

import ast.ASTNode;
import ast.ASTNodeAbstract;
import compiler.Coordinates;
import compiler.MainCodeBlock;
import environment.Environment;
import types.IType;
import types.primitives.TypeBool;
import types.primitives.TypeInt;
import types.primitives.TypePrimitive;
import util.Utils;
import values.IValue;

public class ASTPrintln extends ASTNodeAbstract
{
    private static final String OPERATOR = "println - ()";

    protected ASTNode node;

    protected Consumer<MainCodeBlock> compilerPrintlnFunc;

    /**
     * @param node
     */
    public ASTPrintln(ASTNode node) {
        this.node = node;
    }

    @Override
    public void compile(MainCodeBlock c, Environment<Coordinates> e)
    {
        node.compile(c, e);
        c.emit("dup");
        c.emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
        c.emit("swap");
        compilerPrintlnFunc.accept(c);
    }

    @Override
    public IValue eval(Environment<IValue> e)
    {
        IValue result = Utils.requireNonNull(this.node.eval(e));
        System.out.println(result.show());

        return result;
    }

    @Override
    public IType typecheck(Environment<IType> e)
    {
        this.type = this.node.typecheck(e);

        // Check if the type can be printed
        Utils.checkTypeForOperation(type, TypePrimitive.class,OPERATOR);

        if (this.type instanceof TypeInt)
            this.compilerPrintlnFunc = ASTPrintln::printInt;
        else if( this.type instanceof TypeBool)
            this.compilerPrintlnFunc = ASTPrintln::printBoolean;
        else 
            this.compilerPrintlnFunc = ASTPrintln::printString;
        return this.type;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        builder.append(OPERATOR + " (");
        this.node.toString(builder);
        builder.append(")");

        return builder;
    }

    private static void printBoolean(MainCodeBlock c)
    {
        c.emit("; convert to String;");
        c.emit("invokestatic java/lang/String/valueOf(Z)Ljava/lang/String;");
        c.emit("; call println ");
        c.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
    }

    private static void printInt(MainCodeBlock c)
    {
        c.emit("; convert to String;");
        c.emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
        c.emit("; call println ");
        c.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
    }

    private static void printString(MainCodeBlock c){
        c.emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
    }
}
