package document;

import java.util.LinkedList;

public class DictionaryCharFrequency implements Dictionary{
	
	private LinkedList<String> dict;
	

	public DictionaryCharFrequency() {
		dict = new LinkedList<String>();
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
