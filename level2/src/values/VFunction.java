package values;

import java.util.List;
import java.util.function.Consumer;

import ast.ASTNode;
import util.Environment;
import util.Utils;

public class VFunction implements IValue {
    private List<String> args;
    private ASTNode body;
    private Environment<IValue> env;

    private boolean showActive;

    public VFunction(List<String> args, ASTNode body, Environment<IValue> env) {
        this.args = args;
        this.body = body;
        this.env = env;
        this.showActive = false;
    }

    @Override
    public String show() {

        if (this.showActive)
            return "Recursive closure";

        StringBuilder builder = new StringBuilder("Closure [\n\targs=");
        Utils.toStringList(this.args, (Consumer<String>)((arg) -> builder.append(arg)), null,
            Utils.DEFAULT_DELIMITERS, builder);

        builder.append("\n\tbody=");
        this.body.toString(builder);

        builder.append("\n\tenvironment=");
        this.showActive = true;
        this.env.toString(builder);
        this.showActive = false;

        builder.append("\n]");

        return builder.toString();
    }

    public List<String> getArgs() {
        return args;
    }

    public ASTNode getBody() {
        return body;
    }

    public Environment<IValue> getEnv() {
        return env;
    }

}
