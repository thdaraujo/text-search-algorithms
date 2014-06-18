package textsearch;



import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class SearchTest {

	private static String outputFileName = "SearchTest-Results.txt";
	
	public static void main(String[] args) {
		String algorithm, file, pattern;
		if(args == null || args.length < 3){
			System.out.println("invalid arguments!");
			return;
		}
		else{
			algorithm = args[0];
			file 	  = args[1];
			pattern   = args[2];
		}
		runTest(algorithm, file, pattern);
	}
	
	public static void runTest(String algorithm, String file, String pattern){
		try{
			Path path = Paths.get(file);
			Charset cs = StandardCharsets.UTF_8;
			List<String> text = Files.readAllLines(path, cs); //TODO expensive for large files
			StringBuilder sb = new StringBuilder();
			for(String line : text){
				sb.append(line + System.lineSeparator());
			}
			runSearch(algorithm, sb.toString(), pattern);
		}
		catch(IOException ex){
			System.err.println(ex.getMessage());
		}
	}
	
	public static void runSearch(String algorithm, String text, String pattern){
	
		PerformanceHelper helper = new PerformanceHelper(outputFileName);
		helper.start();
		
		ITextSearch textSearch = null;
		switch (algorithm) {
		case "bf":
			textSearch = new BruteForceSearch(pattern);
			break;
		case "kmp":
			textSearch = new KMPSearch(pattern);
			break;
		case "rk":
			textSearch = new RabinKarpSearch(pattern);
			break;
		default:
			System.out.println("algorithm not available!");
			return;
		}
		helper.setAlgorithm(textSearch.getAlgorithmName());

		int pos = textSearch.search(text);
		helper.stop();
		
		if(pos >= 0 && pos < text.length()){
			System.out.println(textSearch.getAlgorithmName() + ": pattern " + pattern + " found at position " + pos);
		}
		else{
			System.out.println(textSearch.getAlgorithmName() + ": pattern not found!");
		}
		helper.prettyPrint();
		helper.print();
		
		helper.write();
	}
}
