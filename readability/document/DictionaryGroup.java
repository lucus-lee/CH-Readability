package document;

import java.util.HashMap;

public class DictionaryGroup {

	private HashMap<Integer, Dictionary> dict;
	
	public DictionaryGroup(){
		dict = new HashMap<Integer,Dictionary>();
	}
	
	
	public boolean addDict(int sign, Dictionary d) {
		if (dict.containsKey(sign)) return false;
		dict.put(sign, d);
		return true;
	}
	
	public Dictionary getDictionary(int sign){
		
		if (!dict.containsKey(sign)) {
			return null;
		}
		
		return dict.get(sign);
	}
	
	public int getGrade(String word) {
		
		for (int k = 1; k <= dict.size(); k++) {
			if (dict.get(k).isWord(word)) return k;
		}
		return -1;
	}
	
		
}
