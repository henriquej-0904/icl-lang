package compiler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class MainCodeBlock {

    public static final String GENERATED_CLASS_DEFAULT_NAME = "MathExpression";

    private static final String START =

    ".class public %s\n" +
    ".super java/lang/Object\n" +
    ";\n" +
    "; standard initializer\n" +
    ".method public <init>()V\n" +
    "   aload_0\n" +
    "   invokenonvirtual java/lang/Object/<init>()V\n" +
    "   return\n" +
    ".end method\n\n" +
    
    ".method public static main([Ljava/lang/String;)V\n" +
    "      ; set limits used by this method\n" +
    "      .limit locals 10\n" +
    "      .limit stack 256\n\n" +
    
    "      ;    1 - the PrintStream object held in java.lang.System.out\n" +
    "      getstatic java/lang/System/out Ljava/io/PrintStream;\n\n" +
    
    "       ; place your bytecodes here between START and END\n" +
    "       ; START\n\n" +

    "	    ; initialize frame pointer SL stored in local #4 to null\n" +
    "       aconst_null\n" +
    "       astore 4\n\n";

    private static final String END =

    "\n       ; END\n\n" +

    "       ; convert to String;\n" +
    "       invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n" +
    "       ; call println\n" +
    "       invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
    "       return\n" +
    ".end method";

    private static final String NEW_FRAME =

    "	new f%d\n" +
    "	dup\n" +
    "       invokespecial f%d/<init>()V\n" +
    "	dup\n" +

    "	; store SL in new frame\n" +
    "	aload 4\n" +
    "	putfield f%d/sl L%s;\n" +
    "	; update SL\n" +
    "	astore 4\n\n";


    private static final String SL_OBJECT_TYPE = "java/lang/Object";

    private static final String SL_PREVIOUS_FRAME_TYPE = "f%d";

    private List<String> code;

    private String generatedClassName;

    private List<FrameCodeBlock> frames;

    public MainCodeBlock(String className) throws IOException
    {
        if (className == null || className.equals(""))
            generatedClassName = GENERATED_CLASS_DEFAULT_NAME;
        else
            generatedClassName = className;

        code = new LinkedList<>();
        this.frames = new LinkedList<>();
    }

    public void emit(String opcode) {
        code.add(opcode);
    }


    public void emitCurrentFrame()
    {
        emit("aload 4");
    }

    public void endFrame()
    {
        emitCurrentFrame();

        int currentFrameId = this.frames.size() - 1;

        String previousFrameSlType;
        if (currentFrameId == 0)
            previousFrameSlType = SL_OBJECT_TYPE;
        else
            previousFrameSlType = String.format(SL_PREVIOUS_FRAME_TYPE, currentFrameId - 1);

        emit(String.format("getfield f%d/sl L%s;", this.frames.size() - 1, previousFrameSlType) );

        emit("astore 4");
    }

    /**
     * 
     * @param numVariables
     * @return the frameId
     */
    public int addFrame(int numVariables)
    {
        int frameId = this.frames.size();
        FrameCodeBlock frame = new FrameCodeBlock(frameId);
        frame.setNumVariables(numVariables);
        this.frames.add(frame);
    
        String frameSlType;
        if (frameId == 0)
            frameSlType = SL_OBJECT_TYPE;
        else
            frameSlType = String.format(SL_PREVIOUS_FRAME_TYPE, frameId - 1);

        String newFrame = String.format(NEW_FRAME, frameId,
            frameId, frameId, frameSlType);
        
        emit(newFrame);

        return frameId;
    }

    public void dump(File outputFolder) throws IOException, FileNotFoundException { // dumps code to f

        if (!outputFolder.isDirectory())
            throw new IOException("The output folder does not exist.");

        for (FrameCodeBlock frame : this.frames) {
            dumpFrame(frame, outputFolder);
        }

        File mainClassFile = createFile(outputFolder, generatedClassName);
        
        try (PrintStream printMainClassFile = new PrintStream(mainClassFile);) {
            printMainClassFile.printf(START, generatedClassName);

            this.code.forEach(
                (line) ->
                {
                    printMainClassFile.println(line);
                }
            );

            printMainClassFile.print(END);

            printMainClassFile.flush();
        }
    }

    private File createFile(File outputFolder, String fileName)
    {
        return new File(outputFolder + File.separator + fileName + ".j");
    }

    private void dumpFrame(FrameCodeBlock frame, File outputFolder) throws FileNotFoundException
    {
        String frameClassName = frame.getClassName();

        File frameFile = createFile(outputFolder, frameClassName);

        try (PrintStream printFrameFile = new PrintStream(frameFile);) {
            frame.dump(printFrameFile);
        }
    }

}
