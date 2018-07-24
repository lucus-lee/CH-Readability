package document;

import java.util.ArrayList;
import java.util.HashMap;

import application.Main;

public class Document {
	
	private String text;
	private int numChar;
	private String[] characters;
	
	
	/** Create a new document from the given text.	 */
	
	public Document(String text)
	{
		this.text = text;
		characters = calculateCharacters();
		numChar = characters.length;
	}

	
	private String[] calculateCharacters() {
		ArrayList<String> chars = new ArrayList<>();
				
		String Reg="^[\u4e00-\u9fa5]{1}$";
		
		for(int i=0;i<text.length();i++){
	         String b = Character.toString(text.charAt(i));
	         if (b.matches(Reg)) {
	        	 chars.add(b);
	         }
	     }
		
		
		return chars.toArray(new String[chars.size()]);
	}


	/** Return the number of words in this document */
	public int getNumWords() {

        return numChar;
		
	}
	
	/** Return the length of sentences in this document */
	public double getLenSentences() {
        String Reg="[。？！.\n]";
        double result=0.0;
        for(int i = 0;i < text.length(); i++){
        	String b = Character.toString(text.charAt(i));
        	if (b.matches(Reg))result++;
        }
        
        if (result == 0) {
        	result = 1;
        }
        
        return numChar/result;
		
	}
	
	/** Return the number of common words in this document */
	public int getNumRegular() {
		//initialize dictionary
		DictionaryCharFrequency dic = new DictionaryCharFrequency();
		LoadDictionary.load(dic, "database/freqList");
		
		//initialize variables
		int regular = 0;
		HashMap<String, Integer> visited = new HashMap<String, Integer>();
		
		//search each character in dictionary
		
		for(String ch : characters) {
			
			 if (visited.containsKey(ch)) regular += visited.get(ch);
        	 else {
        		 
        		 if (dic.isWord(ch)) visited.put(ch, 1);
        		 else {
        			 
        			 visited.put(ch, 0);
        		 }
        		 regular += visited.get(ch);
        	 }
		}
		
		
		return regular;
		
	}

	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the readability score of this document */
	public double getScore()
	{
		double res = 8.76105604 + 0.00272438 * numChar + 0.07866782 * getLenSentences() 
		- 8.9311010 * (getNumRegular()/numChar);
		return 10 * res;
	}
	
	/** return the classification result of this document */
	public double[] classify()
	{
		//check null
		if (characters == null) {
			return null;
		}
		
		
		//initialize dictionary
		document.DictionaryGroup group = new DictionaryGroup();
		
		for (int i = 1; i < 10; i++) {
			String filename = "database/grade/grade" + i;
			document.DictionaryClassify dict = new document.DictionaryClassify();
			LoadDictionary.load(dict, filename);
			group.addDict(i, dict);
		}
		
		//initialize grade group for calculating
		int[] grade = new int[10];
		
		for (int i : grade) i = 0;
		
		//search each character in the text and find the grade it belongs, increase the number of the corresponding grade
		HashMap<String, Integer> visited = new HashMap<String, Integer>();

		for (String c : characters) {
			if (visited.containsKey(c)) grade[visited.get(c)] ++;
			
			else {
				int g = group.getGrade(c);
				
				if (g == -1) {
					grade[0] ++;
					visited.put(c, 0);
				}
				
				else {
					grade[g] ++;
					visited.put(c, g);
				}
				
				
			}
		}
		
		double[] res = new double[10];
		
		for (int j = 0; j < 10; j++) {
			res[j] = (double)grade[j]/numChar;
		}
		
		return res;
		
	}
	
	
	public int[] classifyNoRepeat()
	{
		//check null
		if (characters == null) {
			return null;
		}
		
		
		//initialize dictionary
		document.DictionaryGroup group = new DictionaryGroup();
		
		for (int i = 1; i < 10; i++) {
			String filename = "database/grade/grade" + i;
			document.DictionaryClassify dict = new document.DictionaryClassify();
			LoadDictionary.load(dict, filename);
			group.addDict(i, dict);
		}
		
		//initialize grade group for calculating
		int[] grade = new int[10];
		
		for (int i : grade) i = 0;
		
		//search each character in the text and find the grade it belongs, increase the number of the corresponding grade
		HashMap<String, Integer> visited = new HashMap<String, Integer>();

		for (String c : characters) {
			if (!visited.containsKey(c)) {
				int g = group.getGrade(c);
				
				if (g == -1) {
					grade[0] ++;
					visited.put(c, 0);
				}
				
				else {
					grade[g] ++;
					visited.put(c, g);
				}
				
				
			}
		}
		
		
		return grade;
		
	}
	
	public String gradeOutput(int[] grades) {
    	StringBuffer s = new StringBuffer();
    	String b = "一二三四五六七八九";
    	
    	
		for (int i = 1; i < 10; i++) {
			s.append(b.charAt(i-1) + "年级生字有：" + grades[i] + "个\n");
		}
		
		s.append("非义务教育识字要求的生字有：" + grades[0] + "个");
		return s.toString();
    }
	
	
	
	
	public boolean testCase(Document doc, int length, int regular, double sentences, int testGrade, int grade)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;

		if (length != numChar) {
			System.out.println("\nIncorrect number of words.  Found " + numChar 
					+ ", expected " + length);
			passed = false;
		}
		
		if (getLenSentences() != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found "  + getLenSentences() 
					+ ", expected " + sentences);
			passed = false;
		}
		
		
		if (getNumRegular() != regular) {
			System.out.println("\nIncorrect number of regulars.  Found "  + getNumRegular() 
					+ ", expected " + regular);
			passed = false;
		}
		
		int[] c = classifyNoRepeat();
		
		if ( c[grade]!= testGrade) {
			System.out.println("\nIncorrect number of certaingrade.  Found "  + c[grade] 
					+ ", expected " + testGrade);
			passed = false;
		}
		
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;					
	}
	
	public String calLevel(double index) {
		String res = "";	
		
		return res;
	}
	
	
}
