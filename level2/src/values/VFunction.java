package values;

import java.util.List;

import ast.ASTNode;
import util.Environment;

public class VFunction  implements IValue{
List<String> args;
ASTNode body;
Environment<IValue> env;

public VFunction(List<String> args, ASTNode body, Environment<IValue> env){
    this.args = args;
    this.body = body;
    this.env = env;
    
}
    @Override
    public String show() {
        String toReturn = "";
        for (String iValue : args) {
            toReturn += iValue + ", ";
        }
        return "fun ( " +  toReturn + ")" ;
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
