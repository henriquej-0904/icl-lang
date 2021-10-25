public class MathExpressionParser {

 
  
    private static final String INTERPRETER_PARAM = "-e";
    private static final String COMPILER_PARAM = "-c";
  
    private static final String DEST_FOLDER_PARAM = "-d";
  
    /** Main entry point. */
    public static void main(String args[])
    {
      if (args.length == 0)
      {
        printUsage();
        System.exit(1);
      }
  
      String[] newArgs = new String[args.length - 1];
      System.arraycopy(args, 1, newArgs, 0, newArgs.length);
  
      if (args[0].equals(INTERPRETER_PARAM))
        interpreterMain(newArgs);
      else if (args[0].equals(COMPILER_PARAM))
        compilerMain(newArgs);
      else
      {
        System.err.println("Invalid argument.");
        printUsage();
        System.exit(1);
      }
    }

    private static void printUsage()
  {
    String usage = 
      "Usage:\n" +
      "\tjava -jar MathExpression.jar -c <input-file-name>.icl [OPTIONS]\n" +
      "\tjava -jar MathExpression.jar -e\n\n" +

      "\t-c -> Compile an expression from a file to generate a .class file that computes that expression.\n" +
      "\t-e -> Evaluate an expression from stdin.\n\n" +

      "OPTIONS:\n" +
      "\t" + DEST_FOLDER_PARAM + " <Output folder> ";
    
    System.out.println(usage);
  }

  public static void interpreterMain(String [] args){
    if (args.length > 0)
    {
      printUsage();
      System.exit(1);
    }
    Interpreter interpreter = Interpreter.getInterpreter();
    while(true){
        try{Interpreter.run(interpreter);}   
        catch (Exception e) {
            System.out.println ("Syntax Error!");
          }
    }
        
  }

  public static void compilerMain(String [] args){
    if (!(args.length == 1 || args.length == 3))
    {
      printUsage();
      System.exit(1);
    }

    String destFolder = null;

    if (args.length == 3)
    {
      if (!args[1].equals(DEST_FOLDER_PARAM))
      {
        System.err.println("Invalid argument.");
        printUsage();
        System.exit(1);
      }

      destFolder = args[2];
    }
      try{
          Compiler.run(args[0], destFolder);
      }catch (ParseException e) {
        System.err.println ("Syntax Error!");
      }
        catch (Exception e) {
        System.err.println ("An error occurred!");
        System.err.println(e.getMessage());
      }
}
}