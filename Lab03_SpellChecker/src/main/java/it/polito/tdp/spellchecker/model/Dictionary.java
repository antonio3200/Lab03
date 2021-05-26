package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
	List<String> dizionario;
	String lingua;
	

	public Dictionary() {
		
	}


	public boolean loadDictionary(String language)  {
		
		if(dizionario!=null && this.lingua.equals(language)) {
			return true;
		}
		dizionario= new ArrayList<String>();
		this.lingua=language;
		
		try {
			FileReader fr = new FileReader("src/main/resources/" + language + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;

			while ((word = br.readLine()) != null) {
				dizionario.add(word.toLowerCase());
			}

			Collections.sort(dizionario);

			br.close();
			System.out.println("Dizionario " + language + " caricato. Trovate " + dizionario.size() + " parole.");
			
			return true;
			
		}
		catch(IOException e) {
			System.out.println("ERRORE NELLA LETTURA DA FILE");
			return false;
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		
		List<RichWord> risultato= new ArrayList<RichWord>();
		for(String s : inputTextList) {
			RichWord r = new RichWord(s);
			if(dizionario.contains(r.parola.toLowerCase()))
				r.setCorretta(true);
			else {
				r.setCorretta(false);
			}
			risultato.add(r);
		}
		return risultato;
	}
	
	
	
	
	
	
	
}
