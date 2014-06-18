package textsearch;

public class BruteForceSearch implements ITextSearch {

	private String pat; // or the pattern string
	
	public BruteForceSearch(String pattern){
		this.pat = pattern;
	}
	
	@Override
	public String getAlgorithmName() {
		return "Brute-Force";
	}
	
	@Override
	public int search(String txt) {
		// TODO Auto-generated method stub
		return txt.length();
	}
}
