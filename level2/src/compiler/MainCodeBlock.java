package compiler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import types.IType;
import types.TypeFunction;
import util.Environment;
import util.Pair;

public class MainCodeBlock
{

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
   // "      getstatic java/lang/System/out Ljava/io/PrintStream;\n\n" +
    
    "       ; place your bytecodes here between START and END\n" +
    "       ; START\n\n" +

    "	    ; initialize frame pointer SL stored in local #4 to null\n" +
    "       aconst_null\n" +
    "       astore 4\n\n";

    private static final String END =

    "\n       ; END\n\n" +

    // "       ; convert to String;\n" +
    // "       invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n" +
    // "       ; call println\n" +
    // "       invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
    "       return\n" +
    ".end method";

    private static final String NEW_FRAME =

    "	new f%d\n" +
    "	dup\n" +
    "       invokespecial f%d/<init>()V\n" +
    "	dup\n" +

    "	; store SL in new frame\n" +
    "	aload 4\n" +
    "	putfield f%d/sl %s\n" +
    "	; update SL\n" +
    "	astore 4\n\n";


    private static final String SL_OBJECT_TYPE = "java/lang/Object";

    private static final String SL_PREVIOUS_FRAME_TYPE = "f%d";

    private List<String> code;

    private String generatedClassName;

    /**
     * List of pairs: previous frameId, Frame
     */
    private List<Pair<Integer, FrameCodeBlock>> frames;
    private Map<IType,RefCodeBlock> refs;

    private int currentFrameId;

    private int framesIdCount;


    private Map<String, ClosureInterfaceCodeBlock> closureInterfaces;

    private Map<Integer, ClosureCodeBlock> closures;

    /**
     * The active closure or null.
     */
    private ClosureCodeBlock activeClosure;

    public MainCodeBlock(String className) throws IOException
    {
        if (className == null || className.equals(""))
            generatedClassName = GENERATED_CLASS_DEFAULT_NAME;
        else
            generatedClassName = className;

        code = new LinkedList<>();
        this.frames = new LinkedList<>();

        this.currentFrameId = FrameCodeBlock.INVALID_FRAME_ID;

        framesIdCount = 0;
        refs = new HashMap<>();

        closureInterfaces = new HashMap<>();
        closures = new HashMap<>();
    }

    public String getNewFrameId(){
        return "L" + framesIdCount++;
    }

    public void emit(String opcode) {
        if (this.activeClosure != null)
            this.activeClosure.emit(opcode);
        else
            code.add(opcode);
    }


    public void emitCurrentFrame()
    {
        if (this.activeClosure != null)
            this.activeClosure.emitCurrentFrame();
        else
            emit("aload 4");
    }

    /**
     * 
     * @param env
     * @return The new Environment.
     */
    public Environment<Coordinates> endFrame(Environment<Coordinates> env)
    {
        int previousFrameId;
        String previousFrameSlType;

        // Check Environment depth
        // if depth == 1 then there is no previous frame
        if (env.getDepth() == 1)
        {
            previousFrameSlType = SL_OBJECT_TYPE;
            previousFrameId = FrameCodeBlock.INVALID_FRAME_ID;
        }
        else
        {
            Pair<Integer, FrameCodeBlock> framePair = this.frames.get(this.currentFrameId);
            previousFrameId = framePair.getLeft();
            previousFrameSlType = String.format(SL_PREVIOUS_FRAME_TYPE, previousFrameId);
        }

        String getPreviousSL = String.format("getfield f%d/sl L%s;", this.currentFrameId, previousFrameSlType);

        emitCurrentFrame();
        emit(getPreviousSL);
        emit("astore 4");

        this.currentFrameId = previousFrameId;

        return env.endScope();
    }

    private FrameCodeBlock createFrame(int numFields)
    {
        int previousFrameId = this.currentFrameId;

        // Create frame
        FrameCodeBlock frame = new FrameCodeBlock(this.frames.size(), previousFrameId, numFields);

        Pair<Integer, FrameCodeBlock> framePair = new Pair<>(previousFrameId, frame);
        this.frames.add(framePair);
        this.currentFrameId = frame.getFrameId();

        return frame;
    }

    /**
     * 
     * @return Pair(New Environment, new Frame).
     */
    public Pair<Environment<Coordinates>, FrameCodeBlock>
        addFrame(Environment<Coordinates> env, int numFields)
    {
        // new scope
        Environment<Coordinates> newEnv = env.beginScope();

        FrameCodeBlock frame = createFrame(numFields);

        if (activeClosure != null)
            activeClosure.createFrame(frame);
        else
        {
            String newFrameInstruction = String.format(NEW_FRAME, this.currentFrameId,
            this.currentFrameId, this.currentFrameId, frame.slType);
        
            emit(newFrameInstruction);
        }

        return new Pair<>(newEnv, frame);
    }

    public void reachFrameIdFromCurrentFrame(int frameId)
    {
        if (this.currentFrameId == frameId)
            return;

        assert this.currentFrameId != FrameCodeBlock.INVALID_FRAME_ID;

        int currentFrameId = this.currentFrameId;
        Pair<Integer, FrameCodeBlock> pair = this.frames.get(currentFrameId);
        int previousFrameId = pair.getLeft();

        while (previousFrameId != FrameCodeBlock.INVALID_FRAME_ID
            && currentFrameId != frameId)
        {
            emit(String.format("getfield f%d/sl Lf%d;", currentFrameId, previousFrameId));
            currentFrameId = previousFrameId;

            assert this.currentFrameId != FrameCodeBlock.INVALID_FRAME_ID;

            if (currentFrameId != frameId)
                pair = this.frames.get(currentFrameId);
        }
    }

    public ClosureCodeBlock createClosure(TypeFunction type)
    {
        // Create closure interface
        ClosureInterfaceCodeBlock closureInterface = new ClosureInterfaceCodeBlock(type);
        ClosureInterfaceCodeBlock previous =
            closureInterfaces.putIfAbsent(closureInterface.interfaceName, closureInterface);

        if (previous != null)
            closureInterface = previous;

        // Create Closure and update activeClosure
        String sl = currentFrameId == FrameCodeBlock.INVALID_FRAME_ID ? "Ljava/lang/Object;" :
            "L" + frames.get(currentFrameId).getRight().getClassName() + ";";
        
        ClosureCodeBlock closure = new ClosureCodeBlock(closures.size(), sl, closureInterface, activeClosure);
        closures.put(closure.id, closure);
        activeClosure = closure;

        // emit init apply function in activeClosure
        activeClosure.emitInitApplyFunction((numArgs) -> createFrame(numArgs));

        return activeClosure;
    }

    public void dump(File outputFolder) throws IOException, FileNotFoundException { // dumps code to f

        if (!outputFolder.isDirectory())
            throw new IOException("The output folder does not exist.");

        for (Pair<Integer, FrameCodeBlock> pairPreviousFrameIdFrame : this.frames) {
            dumpFrame(pairPreviousFrameIdFrame.getRight(), outputFolder);
        }

        for (RefCodeBlock ref : this.refs.values()) {
            dumpRef(ref, outputFolder);
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

    /**
     * Creates a new Reference Code Block with the specified value type.
     * 
     * @param valueType - The type of value this reference points.
     * 
     * @return A new Reference Code Block with the specified value type.
     */
    public RefCodeBlock createRefClass(IType valueType){
        RefCodeBlock r = new RefCodeBlock(valueType);
        refs.putIfAbsent(valueType,r );
        return r;
    }

    /**
     * Get a Reference Code Block with the specified value type.
     * 
     * @param valueType - The type of value this reference points.
     * 
     * @return A Reference Code Block with the specified value type.
     */
    public RefCodeBlock getRefClass(IType valueType){
        return refs.get(valueType);
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

    private void dumpRef(RefCodeBlock ref, File outputFolder) throws FileNotFoundException
    {
        String classname = ref.getClassName();

        File refFile = createFile(outputFolder, classname);

        try (PrintStream printRefFile = new PrintStream(refFile);) {
            ref.dump(printRefFile);
        }
    }
}
