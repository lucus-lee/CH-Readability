package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.io.InputStreamReader;

import document.Dictionary;

public class LoadDictionary {
    /** Load the words from the dictionary file into the dictionary
     * 
     * @param d  The dictionary to load
     * @param filename The file containing the words to load.  Each word must be on a separate line.
     */    
	public static void load(Dictionary d, String filename)
    {
        // Dictionary files have 1 word per line
        BufferedReader reader = null;
        try {
            String nextWord;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
            while ((nextWord = reader.readLine()) != null) {
                d.addWord(nextWord);
//                System.out.println("Dictionary add + " + nextWord);
            }
        } catch (IOException e) {
            System.err.println("Problem loading dictionary file: " + filename);
            e.printStackTrace();
        }
        
    }
	
    /** Load the words from the dictionary directory into the dictionary GROUP
     * 
     * @param g  The dictionary group to load
     * @param fileDir The file containing the dictionary to load.
     */    
	public static void loadGroup(Dictionary[] g, String fileDir)
    {
        // Dictionary files have 1 word per line
        	
    	File[] files = new File(fileDir).listFiles();
	 
   		for (File f : files) {
   			
   			int sign = getSign(f.getName());
   			
            load(g[sign], fileDir + "/" + f.getName());          
   		 }
        	
        
    }
	
	private static int getSign(String filename) {
		
		String digitStr = "";
		int res = 0;
		for (int i = 0; i < filename.length(); i++){
			if (Character.isDigit(filename.charAt(i))) digitStr += filename.charAt(i);
		}
		
		if (!digitStr.isEmpty())  res = Integer.parseInt(digitStr);
		
		return res;
	}
}
