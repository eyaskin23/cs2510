import tester.Tester;
import java.util.ArrayList;

class Examples {
	Examples() {}
	
	void testExamples(Tester t) {
		ArrayList<String> dictionary = new ArrayList<String>();
		String word = "java";
		String meaning = "a modern programming language";
		
		dictionary.add(meaning);
		dictionary.add(word);
		
		t.checkExpect(dictionary.get(0), "java");
		
//		dictionary.add("Java", "amodern");
//		String meaning = dictionary.get("Java");
		
	}
}

class DictionaryEntry {
	String word;
	String definition;
		
	
	DictionaryEntry(String word, String definition) {
		this.word = word;
		this.definition = definition;
		
	}
}

interface IDictionary<T, U> {
	boolean contains(T word);
	void put(T word, U meaning);
	U getMeaning(T word);
	
}

class StringDictionary implements IDictionary<String, String> {
	ArrayList<DictionaryEntry> entries;
	
	StringDictionary() {
		entries = new ArrayList<DictionaryEntry>();
	}
	
	public boolean contains(String word) {
		for (DictionaryEntry entry : entries) {
			if (entry.word.equals(word)) {
				return true;
			}
		} 
		
		return false;
	} 
	
	public String getMeaning(String word) {
		for (DictionaryEntry entry : entries) {
			if (entry.word.equals(word)) {
				return entry.definition;
			}
		}
		
		throw new IllegalArgumentException("Word is not in dictionary");
	}
}

class HashmapExamples {
	IDictionary<String, String> dictionary;
	
	HashmapExamples(){}
	
	void initData() {
		dictionary = new StringDictionary();
		dictionary.put("darkness", "absence of light");
		dictionary.put("java", "a modern programming language");
		dictionary.put("laptop", "mobile computer");
	}
	
	void testHasmapExamples(Tester t) {
		initData();
		
		t.checkExpect(dictionary.contains("darkness"), true);
	}
}





