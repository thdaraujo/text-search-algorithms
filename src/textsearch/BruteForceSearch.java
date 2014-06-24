package textsearch;

public class BruteForceSearch implements ITextSearch {
	private PerformanceHelper helper;
	private String pat; // or the pattern string
	
	public BruteForceSearch(String pattern, PerformanceHelper helper){
		this.pat = pattern;
		this.helper = helper;
	}
	
	@Override
	public String getAlgorithmName() {
		return "Brute-Force";
	}
	
	@Override
	public int search(String txt) {
        int M = this.pat.length();
        int N = txt.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i+j) != pat.charAt(j)){
                    break;
                }
                this.helper.addCount();
            }
            if (j == M) return i;            // found at offset i
        }
        return N;                            // not found
    }
}
