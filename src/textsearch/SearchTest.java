package textsearch;



import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class SearchTest {

	private static String outputFileName = "results/SearchTest-Results.txt";
	
	public static void main(String[] args) {
		
		printInstructions();
		
		String algorithm, file, pattern;
		int runs;
		
		if(args == null || args.length < 3){
			System.out.println("Invalid args! See example above!");
			return;
		}
		
		algorithm = args[0];
		file 	  = args[1];
		pattern   = args[2];
		runs = args.length > 3? Integer.parseInt(args[3]) : 1;
	
		runTest(algorithm, file, pattern, runs);
	}

	public static void runTest(String algorithm, String file, String pattern, int runs){
		try{
			Path path = Paths.get(file);
			Charset cs = StandardCharsets.UTF_8;
			List<String> text = Files.readAllLines(path, cs); //TODO expensive for large files
			StringBuilder sb = new StringBuilder();
			for(String line : text){
				sb.append(line + System.lineSeparator());
			}
			
			String textContent = sb.toString();
			
			if(algorithm.equals("all")){ //run each algorithm
				System.out.println("Running all algorithms!");
				List<String> algs = Arrays.asList("bf", "bm", "kmp", "rk");
				for(String alg : algs){
					for(int i = 0; i < runs; i++){
						runSearch(alg, file, textContent, pattern);
					}
				}
			}
			else{
				for(int i = 0; i < runs; i++){
					runSearch(algorithm, file, textContent, pattern);
				}
			}
		}
		catch(IOException ex){
			System.err.println(ex.getMessage());
		}
	}
	
	public static void runSearch(String algorithm, String file, String text, String pattern){
	
		PerformanceHelper helper = new PerformanceHelper(file, text.length(), pattern, outputFileName);
		helper.start();
		
		ITextSearch textSearch = null;
		switch (algorithm) {
		case "bf":
			textSearch = new BruteForceSearch(pattern, helper);
			break;
		case "bm":
			textSearch = new BoyerMooreSearch(pattern, helper);
			break;
		case "kmp":
			textSearch = new KMPSearch(pattern, helper);
			break;
		case "rk":
			textSearch = new RabinKarpSearch(pattern, helper);
			break;
		default:
			System.out.println("algorithm not available!");
			return;
		}
		helper.setAlgorithm(textSearch.getAlgorithmName());

		int pos = textSearch.search(text);
		boolean found = pos >= 0 && pos < text.length();
		helper.stop(found);
		
		if(found){
			System.out.println(textSearch.getAlgorithmName() + ": pattern " + pattern + " found at position " + pos);
		}
		else{
			System.out.println(textSearch.getAlgorithmName() + ": pattern not found!");
		}
		helper.prettyPrint();
		//helper.print();
		
		helper.write();
	}
	
	private static void printInstructions() {
		System.out.println("usage: bf|bm|kmp|rk file_path pattern");
		System.out.println("ex: kmp \"examples/genoma/Allium_cepa.SES\" GATTACA");
		System.out.println("algorithms: bf (Brute-Force), bm (Boyer-Moore), kmp (KMP), rk (Rabin-Karp)");
		System.out.println("");
	}
}
