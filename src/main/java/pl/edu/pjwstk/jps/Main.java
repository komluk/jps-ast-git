package pl.edu.pjwstk.jps;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;
import pl.edu.pjwstk.jps.ast.AbstractExpression;
import pl.edu.pjwstk.jps.ast.datastore.SBAStore;
import pl.edu.pjwstk.jps.interpreter.Interpreter;
import pl.edu.pjwstk.jps.parser.JpsParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * User: pawel
 * Date: 19.01.13
 * Time: 21:51
 */
public class Main {

	private static class Args {
		@Parameter(names = "-f", variableArity = true, converter = FileConverter.class)
		public static List<File> files;
	}

	public static void main(String[] args) throws Exception {
		Args parsed = new Args();
		new JCommander(parsed, args);
		SBAStore.getInstance();

		if(parsed.files != null) {
			for(File file : parsed.files) {
				if(file.exists() && file.isFile()) {
					SBAStore.getInstance().loadXML(file.getAbsolutePath());
					System.out.println(file.getAbsolutePath() + " loaded");
				} else {
					throw new NoSuchFileException(file.getAbsolutePath());
				}
			}
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println();
				printBye();
			}
		});
		System.out.println();

		printHelp();
		System.out.print(">");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line = "";

		while (true) {
			try {
				line = reader.readLine();
				if(line.startsWith(":")) {
					parseInternalCommand(line);
				} else {
					executeSBQL(line);
				}
			} catch (Throwable ex) {
				System.err.println(ex.getMessage());
				ex.printStackTrace();
			}
			System.out.print("> ");
		}
	}

	private static void parseInternalCommand(String line) {
		if(line.toLowerCase().startsWith(":load")) {
			String file = line.split(" ", 2)[1];
			File filePath = new File(file);
			System.out.println("Loading file: " + filePath.getAbsolutePath());
			SBAStore.getInstance().loadXML(filePath.getAbsolutePath());
			System.out.println("Done");
		} else if(line.toLowerCase().startsWith(":reset")) {
			SBAStore.getInstance().reset();
			System.out.println("SBAStore cleared");
		} else if(line.toLowerCase().startsWith(":print")) {
			System.out.println(SBAStore.getInstance().toString());
			System.out.println();
		} else if(line.toLowerCase().startsWith(":exit")) {
			System.exit(0);
		} else {
			printHelp();
		}
	}

	private static void executeSBQL(String line) throws Exception {
		Interpreter interpreter = new Interpreter(SBAStore.getInstance());
		AbstractExpression expression = getExpression(line);
		System.out.println(interpreter.eval(expression).toString());
	}

	private static AbstractExpression getExpression(String expression) throws Exception {
		JpsParser parser = new JpsParser(expression);
		parser.user_init();
		parser.parse();
		return parser.RESULT;
	}

	private static void printBye() {
		System.out.println("JPS projekt by Paweł Chudzik & Daniel Iwaniuk");
		System.out.println("                     BYE");
	}
	private static void printHelp() {
		System.out.println("HELP:");
		System.out.println("\t:load fileName[enter] - loads file into SBAStore");
		System.out.println("\t:reset[enter]  - clear SBAStore");
		System.out.println("\t:exit[enter]   - quit");
		System.out.println("\t:print[enter]  - prints SBAStore content as xml");
		System.out.println("\t:help[enter]   - this help");
		System.out.println("\tsbql[enter]    - executes sbqlQuery");
		System.out.println("You can load file with: java -jar jar_file.jar -f xml1.xml xml2.xml");
		System.out.println();
	}
}
