import java.util.*;
import tester.Tester;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
 

class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = 
      new ArrayList<Character>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                    't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code 
  PermutationCode() {
    this.code = this.initEncoder();
  
  }

  // Create a new instance of the encoder/decoder with the given code 
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> encoder = new ArrayList<Character>();
 
    ArrayList<Character> alpha = new ArrayList<Character>(
            Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                't', 'u', 'v', 'w', 'x', 'y', 'z'));
    
    for (int i = 0; i < 26; i++) {

      int index = rand.nextInt(alpha.size());
      char c = alpha.get(index);
      encoder.add(c);
      alpha.remove(index);
    }
    return encoder;
  }
  
  
  

  // produce an encoded String from the given String
  String encode(String source) {
    String result = "";
    
    for (int i = 0; i < source.length(); i++) {
      char character = source.charAt(i);
      int index = this.alphabet.indexOf(character);
      result += (this.code.get(index));
    }
    return result;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    String result = "";
 
    for (int i = 0; i < code.length(); i++) {
      char character = code.charAt(i);
      int index = this.code.indexOf(character);
      result += this.alphabet.get(index);
    }
    return result;
  }
}

// represents a class of example permutations
class ExamplePermutations {

  ArrayList<Character> empty = 
              new ArrayList<Character>(Arrays.asList());

  ArrayList<Character> code1 = 
              new ArrayList<Character>(Arrays.asList(
                            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                             't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code2 = 
              new ArrayList<Character>(Arrays.asList(
                            'm', 'd', 'c', 'b', 'p', 'r', 'g', 'h', 's', 'j', 
                            'k', 'l', 'a', 'n', 'o', 'e', 'q', 'f', 'i', 
                             't', 'u', 'v', 'w', 'x', 'y', 'z'));


  PermutationCode encoder = new PermutationCode(this.empty);
  PermutationCode pc1 = new PermutationCode(this.code1);
  PermutationCode pc2 = new PermutationCode(this.code2);

  // creates a new sample set of permutation codes
  void initPermutations() {
    PermutationCode encoder1 = new PermutationCode();
    PermutationCode encoder2 = new PermutationCode();
    String message = "evelyn";
    String message2 = "hello";
    String encodedMessage1 = encoder1.encode(message);
    String encodedMessage2 = encoder2.encode(message2);
    System.out.println("encoded message 1 is " + encodedMessage1);
    System.out.println("encoded message 2 is " + encodedMessage2);
    
  }

  // tests the decode method
  void testDecode(Tester t) {
    t.checkExpect(this.pc1.decode("fundies"), "fundies");
    t.checkExpect(this.pc1.decode("evelyn"), "evelyn");
    t.checkExpect(this.pc2.decode("hello"), "hpllo");
    t.checkExpect(this.pc2.decode("world"), "woflb");  
  }
  
  // tests the encode method
  void testEncode(Tester t) {
    t.checkExpect(this.pc1.encode("hello"), "hello");
    t.checkExpect(this.pc1.encode("world"), "world");
    t.checkExpect(this.pc2.encode("fundies"), "runbspi");
    t.checkExpect(this.pc2.encode("evelyn"), "pvplyn");
    t.checkExpect(this.pc2.encode("hi"), "hs");

  }
 
  // tests the initencoder method
  void testinitEncoder(Tester t) {
    this.pc1.rand = new Random(5);
    t.checkExpect(this.pc1.initEncoder(),  new ArrayList<Character>(Arrays.asList(
              'p', 's', 'c', 'o', 'r', 't', 'u', 'i', 'k', 'q', 
              'v', 'e', 'f', 'x', 'n', 'j', 'y', 'w', 'z', 
              'd', 'a', 'b', 'g', 'm', 'l', 'h')));
    t.checkExpect(this.pc1.initEncoder().size(), 26);
    t.checkExpect(this.pc1.encode(this.pc1.decode("string")), "string");
    t.checkExpect(this.empty.size(), 0);
  }
}

















