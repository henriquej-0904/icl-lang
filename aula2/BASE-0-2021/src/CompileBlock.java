import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class CompileBlock {

    private final int MAX = 100;

    private String[] code;

    private int pc;

    private String start, end;

    public CompileBlock(String inputSrc) throws IOException
    {
        BufferedReader input = new BufferedReader(new FileReader(inputSrc));
        start = readUntil("; START", input);
        end = "";

        String tmp;

        while ((tmp = input.readLine()) != null)
        {
            end += tmp;
            end += "\n";
        }

        code = new String[MAX];
        pc = 0;
    }

    private String readUntil(String s, BufferedReader input) throws IOException
    {
        String res, tmp;
        res = "";

        while ((tmp = input.readLine()) != null && !tmp.contains(s))
        {
            res += tmp;
            res += "\n";
        }

        res += tmp;
        return res;
    }

    

    void emit(String opcode) {
        code[pc++] = opcode;
    }

    void dump(PrintStream f) { // dumps code to f }

        f.println(start);

        for (int i = 0; i < pc; i++)
            f.println(code[i]);

        f.println(end);

    }

}
