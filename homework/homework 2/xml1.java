import tester.Tester;

interface IXML {
  //counts the length of the sentence 

  int contentLength();
  
  //checks if the sentence has a tag
  
  boolean hasTag(String name);
  
  //checks if the XMl has any attributes
  
  boolean hasAttribute(String name);
  
  //prints out the xml as a string
  
  String renderAsString();

}

//represents an xml just as a string
class Plaintext implements IXML {

  String txt;
  
  Plaintext(String txt) {
    this.txt = txt;
  }
  
  /* Template:
   * FIELDS:
   * this.txt -- String;
   * METHODS:
   * this.contentLength()
   * this.hasTag()
   * this.hasAttribute()
   * this.renderAsString() 
   */

  
  public int contentLength() {
    return txt.length();
  }

  public boolean hasTag(String name) {
    return false;
  }

  public boolean hasAttribute(String name) {
    return false;
  }

  public String renderAsString() {
    return this.txt;

  }
}

//represents an XML with no tags
class Untagged implements IXML {

  ILoXML content;

  Untagged(ILoXML content) {
    this.content = content;

  }
  
  /* Template:
   * FIELDS:
   * this.content -- String;
   * METHODS:
   * this.contentLength()
   * this.hasTag()
   * this.hasAttribute()
   * this.renderAsString() 
   */
 

  public int contentLength() {
    return 0;
  
  }

  public boolean hasTag(String name) {
    return false;
  
  }

  public boolean hasAttribute(String name) {
    return false;
  
  }

  public String renderAsString() {
    return null;
  
  }
}

//represents a list of XML
interface ILoXML {
  int contentLength();
 
  String renderAsString();
 
  boolean hasTag(String name);
 
  boolean hasAttribute(String att);
  
}

//represents an empty list of xml
class MtLoXML implements ILoXML {
  public int contentLength() {
    return 0;

  }

  public String renderAsString() {
    return new String();

  }

  public boolean hasTag(String name) {
    return false;

  }
 
  public boolean hasAttribute(String att) {
    return false;
  }
}


//represents an empty list of attributes
class MtLoAtt implements ILoAtt {
  public boolean hasAttribute(String name) {
    return false;
 
  }

  public boolean hasAttributes(String name) {
    return false;
  
  }
}

//represents a list of xml
class ConsLoXML implements ILoXML {
  IXML first;
  ILoXML rest;

  ConsLoXML(IXML first, ILoXML rest) {
    this.first = first;
    this.rest = rest;

  }

  public int contentLength() {
    return this.first.contentLength() 
      + this.rest.contentLength();

  }

  public String renderAsString() {
    return first.renderAsString()
      + rest.renderAsString();

  }

  public boolean hasTag(String name) {
    return false;
 
  }

  public boolean hasAttribute(String att) {
    return false;
 
  }

}

//represents a tagged xml
class Tagged implements IXML {
  Tag tag;
  ILoXML content;

  Tagged(Tag tag, ILoXML content) {
    this.tag = tag;
    this.content = content;
  }
  
  /* Template:
   * FIELDS:
   * this.tag -- Boolean;
   * this.content -- String;
   * METHODS:
   * this.contentLength()
   * this.hasTag()
   * this.hasAttribute()
   * this.renderAsString() 
   */

  public int contentLength() {
    return this.content.contentLength();
  }

  public boolean hasTag(String name) {
    return this.tag.name.equals(name) 
      || this.content.hasTag(name);
  }

 
  public String renderAsString() {
    return this.content.renderAsString();

  }

  public boolean hasAttribute(String name) {
    return this.tag.atts.hasAttributes(name);
 
  }
}

//represents a tag with a name and list of attributes
class Tag {

  String name;
  ILoAtt atts;

  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }
}
  
//represents a list of attributes
interface ILoAtt {

  boolean hasAttributes(String name);

}

/* Template:
* FIELDS:
* this.name -- String;
* this.atts -- String;
* METHODS:
* this.contentLength()
* this.hasTag()
* this.hasAttribute()
* this.renderAsString() 
*/

//represents a list of attributes
class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;

  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;

  }

  public int contentLength() {
    return 0;
 
  }

  public boolean hasTag(String name) {
    return false;
 
  }

  public String renderAsString() {
    return null;
  
  }

  public boolean hasAttributes(String name) {
    return this.first.name.equals(name) 
      || this.rest.hasAttributes(name);
  }

}

//represents a singular attribute
class Att {

  String name;
  String value;

  Att(String name, String value) {
    this.name = name;
    this.value = value;

  }
  
  /* Template:
  * FIELDS:
  * this.name -- String;
  * this.value -- int;
  * METHODS:
  * this.contentLength()
  * this.hasTag()
  * this.hasAttribute()
  * this.renderAsString() 
  */

  public int contentLength() {
    return 0;
  }

  public boolean hasTag() {
    return false;
  }

  public boolean hasAttribute(String name) {
    return false;

  }

  public String renderAsString() {
    return null;

  }
}

//represents examples of xmls
class ExamplesXML {

  ConsLoAtt yellAtts = new ConsLoAtt(new Att("volume", "30db"),
      new ConsLoAtt(new Att("duration", "5sec"), new MtLoAtt()));
  Att size10 = new Att("size", "10px");
  Att colorRed = new Att("color", "red");
  Tag styleTag = new Tag("style", new MtLoAtt());
  Tag italicTag = new Tag("italic", new ConsLoAtt(colorRed, yellAtts));
  Tag yellTag = (new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), yellAtts)));
  Tagged yellTagged = new Tagged(yellTag,
      new ConsLoXML(new Tagged(italicTag, new MtLoXML()), 
      new ConsLoXML(new Plaintext("X"), new MtLoXML())));
  IXML plainText = new Plaintext("I am XML!");
  IXML tagged1 = new Tagged(styleTag, new ConsLoXML(plainText, new MtLoXML()));
  IXML tagged2 = new Tagged(italicTag, new ConsLoXML(plainText, new MtLoXML())); 
  ILoXML xmlDoc1 = new MtLoXML();
  ILoXML xmlDoc2 = new ConsLoXML(plainText, xmlDoc1);
  IXML xmlDoc3 = new Tagged(yellTag, 
      new ConsLoXML(new Tagged(italicTag, new MtLoXML()), new ConsLoXML(plainText, new MtLoXML())));
  IXML xmlDoc4 = new Tagged(new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), 
      new ConsLoAtt(new Att("duration", "5sec"), new MtLoAtt()))),
      new ConsLoXML(new Tagged(italicTag, new MtLoXML()), new ConsLoXML(plainText, new MtLoXML())));
  IXML xml1 = new Tagged(yellTag, new ConsLoXML(new Tagged(italicTag, 
      new ConsLoXML(new Plaintext("X"), new MtLoXML())), xmlDoc1));  
  IXML xml2 = new Tagged(new Tag("yell", new MtLoAtt()),
      new ConsLoXML(this.plainText, new MtLoXML())); 
  IXML xml3 = new Tagged(this.yellTag,
      new ConsLoXML(new Tagged(yellTag,
      new ConsLoXML(new Tagged(this.italicTag, new ConsLoXML(new Plaintext("X"), new MtLoXML())),
      new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
      new MtLoXML()));
  IXML xml4 = new Tagged(this.yellTag,
            new ConsLoXML(new Tagged(yellTag,
            new ConsLoXML(new Tagged(this.italicTag, 
            new ConsLoXML(new Plaintext("X"), new MtLoXML())),
            new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
            new MtLoXML()));

  boolean testContentLength1(Tester t) {
    return t.checkExpect(this.plainText.contentLength(), 9) 
      && t.checkExpect(this.tagged1.contentLength(), 9) 
      && t.checkExpect(this.tagged2.contentLength(), 9) 
      && t.checkExpect(this.xmlDoc1.contentLength(), 0)
      && t.checkExpect(this.xmlDoc2.contentLength(), 9)
      && t.checkExpect(this.xml2.contentLength(), 9);
  }

  boolean testhasTag(Tester t) {
    return t.checkExpect(this.plainText.hasTag("yell"), false) 
      && t.checkExpect(this.tagged1.hasTag(null), false) 
      && t.checkExpect(this.tagged2.hasTag(null), false)
      && t.checkExpect(this.xmlDoc1.hasTag(null), false)
      && t.checkExpect(this.xmlDoc2.hasTag(null), false)
      && t.checkExpect(this.xml2.hasTag(null), false)
      && t.checkExpect(this.xml4.hasTag("volume="), true);
  }

  boolean testhasAttributes(Tester t) {
    return t.checkExpect(this.plainText.hasAttribute(null), false)
      && t.checkExpect(this.tagged1.hasAttribute(null), false) 
      && t.checkExpect(this.tagged2.hasAttribute(null), false)
      && t.checkExpect(this.xmlDoc1.hasAttribute("style"), false)
      && t.checkExpect(this.xmlDoc2.hasAttribute("style"), false)
      && t.checkExpect(this.xmlDoc3.hasAttribute("color"), false) 
      && t.checkExpect(this.xmlDoc4.hasAttribute("size"), false) 
      && t.checkExpect(this.xml2.hasAttribute(null), false);
  }

  boolean renderAsString(Tester t) {
    return t.checkExpect(this.plainText.renderAsString(), "I am XML!")
      && t.checkExpect(this.xmlDoc1, "I am XML!")
      && t.checkExpect(this.xmlDoc2, "I am XMl!")
      && t.checkExpect(this.xmlDoc3, "I am XML!");
  }
}   