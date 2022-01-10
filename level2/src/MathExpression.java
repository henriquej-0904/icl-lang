import compiler.*;
import typeError.TypeErrorException;
import types.IType;
import types.ITypeEnvEntry;
import util.Environment;
import values.IValue;
import values.IValueEnvEntry;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import ast.ASTNode;
import ast.print.ASTPrintln;

import java.util.LinkedList;

/**
 * Main class for the Math Expression parser (interpreter & compiler).
 */
public class MathExpression {

	private static final String INTERPRETER_PARAM = "-e";
	private static final String COMPILER_PARAM = "-c";

	private static final String DEST_FOLDER_PARAM = "-d";
	private static final String JASMIN_JAR_PATH_ENV = "JASMIN_JAR_PATH";
	private static final String EXPRESSION_FILE_EXTENSION = ".icl";

	private static final String DEFAULT_OUTPUT_FOLDER = "out";

	private static final boolean DEBUG = System.getenv("DEBUG") != null;

	/** Main entry point. */
	public static void main(String args[]) {
		if (args.length == 0) {
			printUsage();
			System.exit(1);
		}

		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, newArgs.length);

		if (args[0].equals(INTERPRETER_PARAM))
			interpreterMain(newArgs);
		else if (args[0].equals(COMPILER_PARAM))
			compilerMain(newArgs);
		else {
			System.err.println("Invalid argument.");
			printUsage();
			System.exit(1);
		}
	}

	/**
	 * Print usage.
	 */
	private static void printUsage() {
		String usage = "Usage:\n" + "\tjava -jar MathExpression.jar -c <input-file-name>.icl [OPTIONS]\n"
				+ "\tjava -jar MathExpression.jar -e [<input-file-name>.icl]\n\n" +

				"\t-c -> Compile an expression from a file to generate a .class file that computes that expression.\n"
				+ "\t-e -> Evaluate an expression from stdin.\n\n" +

				"OPTIONS:\n" + "\t" + DEST_FOLDER_PARAM + " <Output folder> ";

		System.out.println(usage);
	}

	/**
	 * The main for the interpreter.
	 * @param args
	 */
	private static void interpreterMain(String[] args) {
		if (args.length != 0 && args.length != 1) {
			printUsage();
			System.exit(1);
		}

		boolean inputFile = args.length > 0;

		try (InputStream in = inputFile ? new FileInputStream(args[0]) : System.in)
		{
			Parser parser = new Parser(in);
			boolean end = false;

			do {
				try
				{
					if (!inputFile)
						System.out.print("> ");

					ASTNode ast = parser.Start(true);
					System.out.println(ast.eval(new Environment<IValue>()).show());
				} catch (ParseException e) {
					System.err.println("Syntax Error!");

					if (!inputFile)
						parser.ReInit(System.in);
				} catch (Exception e) {
					if (DEBUG)
						e.printStackTrace();
					else {
						System.err.println("An error occurred!");
						System.err.println(e.getMessage());
					}

					end = true;
				}
			} while (!end && !inputFile);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * The main for the compiler.
	 * @param args
	 */
	private static void compilerMain(String[] args) {
		if (!(args.length == 1 || args.length == 3)) {
			printUsage();
			System.exit(1);
		}

		String source = args[0];
		File destFolder = null;

		if (args.length == 3) {
			if (!args[1].equals(DEST_FOLDER_PARAM)) {
				System.err.println("Invalid argument.");
				printUsage();
				System.exit(1);
			}

			destFolder = new File(args[2]);
		}
		else
			destFolder = new File(DEFAULT_OUTPUT_FOLDER);

		File tmpFolder = null;
		try {
			String jasminJarPath = getJasminPath();
			// Open expression file
			File expressionFile = new File(source);
			String expressionFileName = getExpressionFileNameWithoutExtension(expressionFile);

			// Init parser from the input file.
			Parser parser = new Parser(new FileInputStream(expressionFile));

			// Create destFolder
			if (destFolder != null)
				destFolder.mkdirs();

			// Create tmp folder for j files.
			if (DEBUG)
			{
				tmpFolder = new File("MathExpressionJfiles");
				tmpFolder.mkdirs();
			}
			else
			{
				tmpFolder = Files.createTempDirectory("MathExpressionJfiles").toFile();
				tmpFolder.deleteOnExit();
			}

			// parse input
			ASTNode ast = parser.Start(false);

			IType type = ast.typecheck(new Environment<IType>());
			
			System.out.println(String.format("The expression type is: %s\n", type.show()));
			
			// Compile expression and dump to tmp file.
			/* MainCodeBlock c = new MainCodeBlock(expressionFileName);
			ast.compile(c, new Environment<Coordinates>());
			c.dump(tmpFolder);

			callJasmin(jasminJarPath, tmpFolder, expressionFileName);
			buildJar(destFolder, expressionFileName, tmpFolder);
			 */
		} catch (ParseException e) {
			System.err.println("Syntax Error!");
		}
		catch (TypeErrorException e)
		{
			System.err.println("Type error!");
			System.err.println(e.getMessage());
		}
		catch (Exception e) {
			System.err.println("An error occurred!");
			System.err.println(e.getMessage());
		}
		finally
		{
			if (!DEBUG && tmpFolder != null)
			{
				File[] files = tmpFolder.listFiles();
				for (File file : files) {
					file.delete();
				}
				tmpFolder.delete();
			}
		} 
	}

	private static void callJasmin(String jasminJarPath, File tmpFolder,
		String expressionFileName) throws IOException, InterruptedException
	{
		// Call jasmin to compile to a .class file.

		List<String> jasminCommand = new LinkedList<String>();
		jasminCommand.add("java");
		jasminCommand.add("-jar");
		jasminCommand.add(jasminJarPath);

		jasminCommand.add(DEST_FOLDER_PARAM);
		jasminCommand.add(tmpFolder.getAbsolutePath());

		// Debug
		if (DEBUG)
			jasminCommand.add("-g");

		File[] jFiles = tmpFolder.listFiles();
		for (File jFile : jFiles) {
			jasminCommand.add(jFile.getAbsolutePath());
		}

		ProcessBuilder processBuilder = new ProcessBuilder(jasminCommand);
		Process jasminProcess = processBuilder.start();
		int jasminExitValue = jasminProcess.waitFor();

		if (jasminExitValue == 0) {
			System.out.println("Compiled with success!");
		} else {
			System.err.println("Jasmin returned an error.");
			System.exit(jasminExitValue);
		}
	}

	private static void buildJar(File destFolder, String expressionFileName,
		File tmpFolder) throws IOException, InterruptedException
	{
		List<String> jarCommand = new LinkedList<String>();
		jarCommand.add("jar");
		jarCommand.add("--create");
		jarCommand.add("--file");
		jarCommand.add(destFolder.getAbsolutePath() + File.separator + expressionFileName + ".jar");
		jarCommand.add("-e");
		jarCommand.add(expressionFileName);

		File[] classFiles = tmpFolder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.contains(".class");
			}
			
		});

		for (File classFile : classFiles) {
			jarCommand.add(classFile.getName());
		}

		ProcessBuilder processBuilder = new ProcessBuilder(jarCommand);
		processBuilder.directory(tmpFolder);

		Process jarProcess = processBuilder.start();
		int jarExitValue = jarProcess.waitFor();

		if (jarExitValue == 0) {
			System.out.println("Created file: " + expressionFileName + ".jar");
		} else {
			System.err.println(new String (jarProcess.getErrorStream().readAllBytes()));
			System.err.println("Jar create returned an error.");
			System.exit(jarExitValue);
		}
	}

	/**
	 * Get the jasmin.jar file path from the environment.
	 * @return The path for the jasmin.jar file.
	 */
	private static String getJasminPath() {
		String jasminJarPath = System.getenv(JASMIN_JAR_PATH_ENV);
		if (jasminJarPath == null) {
			System.err.println("Cannot find jasmin jar executable.\n" + "Environment var " + JASMIN_JAR_PATH_ENV
					+ " must be defined.");
			System.exit(1);
		}

		return jasminJarPath;
	}

	/**
	 * Get the name of the file without the extension.
	 * @param expressionFile - The file to get the name.
	 * @return The name of the file without the extension.
	 */
	private static String getExpressionFileNameWithoutExtension(File expressionFile) {
		String name = expressionFile.getName();
		int extensionIndex;
		if (name.isEmpty() || (extensionIndex = name.lastIndexOf(EXPRESSION_FILE_EXTENSION)) <= 0)
			throw new IllegalArgumentException("The input file name must begin with a valid character"
					+ " and have the " + EXPRESSION_FILE_EXTENSION + " extension.");

		return name.substring(0, extensionIndex);
	}
}