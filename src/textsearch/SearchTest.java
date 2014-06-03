package textsearch;

public class SearchTest {

	public static void main(String[] args) {
		runTest();
	}
	
	public static void runTest(){
		
		String text = "ABRACADABRA",
			   pattern = "CA";
		
		ITextSearch boyerMooreSearch = new BoyerMooreSearch(pattern);
		ITextSearch rabinKarpSearch = new RabinKarpSearch(pattern);
		ITextSearch kmpSearch = new KMPSearch(pattern);
		
		int boyerMoorePos = boyerMooreSearch.search(text);
		int rabinKarpPos = rabinKarpSearch.search(text);
		int kmpPos = kmpSearch.search(text);
		
		System.out.println("BoyerMoorer = " + boyerMoorePos);
		System.out.println("Rabin Karp = " + rabinKarpPos);
		System.out.println("Kmp = " + kmpPos);
		
	}
}
