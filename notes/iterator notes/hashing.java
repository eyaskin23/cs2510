import tester.Tester;
import java.util.ArrayList;
import java.util.Objects;

class ExamplesHashing {
	ExamplesHashing (){}
	
	
	void testExamples(Tester t) {
		ArrayList<String> dictionary = new ArrayList<String>();
		String word = "java";
		String meaning = "a modern programming language.";
		
		dictionary.add(word);
		dictionary.add(meaning);
		
		t.checkExpect(dictionary.get(0), "java");
		
//		dictionary.add("java", "a modern programming language");
//		dictionary["Java"] = "a modern programming language.";
//		String meaning = dictionary.get("java");
		
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
	
	
	public void put(String word, String definition) {
		entries.add(new DictionaryEntry(word, definition));
		
	}
	
	public String getMeaning(String word) {
		for (DictionaryEntry entry : entries) {
			if (entry.word.equals(word)) {
				return entry.definition;
			}
		} 
		throw new IllegalArgumentException("word not in dictionary.");
	}
}


class HashmapExamples {
	IDictionary<String, String> dictionary;
	
	HashmapExamples(){};
	
	void initData() {
		dictionary = new StringDictionary();
		dictionary.put("darkness", "the absence of light");
		dictionary.put("java", "a modern programming language");
		dictionary.put("laptop", "a mobile computer");
	}
	
	void testHashMapExamples(Tester t) {
		
		initData();
		
		t.checkExpect(dictionary.contains("darkness"), true);
		t.checkExpect(dictionary.contains("java"), true);
		t.checkExpect(dictionary.contains("laptop"), true);
		t.checkExpect(dictionary.contains("other"), false);
		
		t.checkExpect(dictionary.getMeaning("darkness"), "the absence of light");
		t.checkExpect(dictionary.getMeaning("java"), "a modern programming language");
		t.checkExpect(dictionary.getMeaning("laptop"), "a mobile computer");
		t.checkException(new IllegalArgumentException("word not in dictionary."), 
				dictionary, "getMeaning", "other");
		
	}
	
	 void testGeneric(Tester t) {
		 GenericDictionary<String, String> sDictionary = 
				 new GenericDictionary<String, String>();
		 sDictionary.put("darkness", "the absence of light");
		 sDictionary.put("java", "a modern programming language");
		 sDictionary.put("laptop", "a mobile computer");
		 
		 t.checkExpect(sDictionary.contains("darkness"), true);
		 t.checkExpect(sDictionary.contains("java"), true);
		 t.checkExpect(sDictionary.contains("laptop"), true);
		 t.checkExpect(sDictionary.contains("other"), false);
		 
		 t.checkExpect(sDictionary.getValue("darkness"), "the absence of light");
		 t.checkExpect(sDictionary.getValue("java"), "a modern programming language");
		 t.checkExpect(sDictionary.getValue("laptop"), "a mobile computer");
		 t.checkException(new IllegalArgumentException("Word is not in dictionary"), 
				 sDictionary, "getValue", "other");
		 
	 }
	
}

class Author {
	String name;
	int yob;
	
	Author(String name, int yob) {
		this.name = name;
		this.yob = yob;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		
		if (! (other instanceof Author)) {
			return false;
		}
			
			Author author = (Author) other;
			
			return this.name.equals(author.name) &&
					this.yob == author.yob;
	}
	
	public int hashCode() {
		return Objects.hash(this.name, this.yob);
	}
}



class GenericDictionaryEntry<T, U> {
	T key;
	U value;
	
	GenericDictionaryEntry(T key, U value) {
		this.key = key;
		this.value = value;
	}
}

class GenericDictionary<T, U> {
	ArrayList<GenericDictionaryEntry<T, U>> entries;
	
	GenericDictionary() {
		entries = new ArrayList<GenericDictionaryEntry<T, U>>();
		
	}
	
	public boolean contains(T key) {
		for (GenericDictionaryEntry<T, U> entry : entries) {
			if (entry.key.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	public void put(T key, U value) {
		entries.add(new GenericDictionaryEntry<T, U>(key, value));
	}
	
	public U getValue(T key) {
		int indexToGetFrom = key.hashCode();
		return entries.get(indexToGetFrom).value;
		
		
//		for (GenericDictionaryEntry<T, U> entry : entries) {
//			if (entry.key.equals(key)) {
//				return entry.value;
//			}
//		}
		// throw new IllegalArgumentException("Word is not in dictionary");
	}
}




























