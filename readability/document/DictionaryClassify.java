package document;

import java.util.TreeSet;

public class DictionaryClassify implements Dictionary {
	
	private TreeSet<String> dict;
	
	public DictionaryClassify(){
		dict = new TreeSet<String>();
	}

	@Override
	public boolean addWord(String word) {
		return dict.add(word);
	}

	@Override
	public boolean isWord(String s) {
		return dict.contains(s);
	}

}
