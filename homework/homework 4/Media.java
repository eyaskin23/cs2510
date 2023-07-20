import tester.Tester;

// a piece of media
interface IMedia {
  
  // is this media really old?
  boolean isReallyOld();
  
  // are captions available in this language?
  boolean isCaptionAvailable(String language);
  
  // a string showing the proper display of the media
  String format();
}

// to represent a media
abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;
  
  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }
  
  /*
  TEMPLATE
  FIELDS:
  ... this.title ...                                     -- String
  ... this.captionOptions ...                            -- ILoString
  
  METHODS:
  ... this.isReallyOld() ...                             -- boolean
  ... this.isCaptionAvailable(String) ...                -- boolean
  ... this.format() ...                                  -- String
  
  METHODS ON FIELDS:
  ... this.captionOptions.isCaptionAvailable(String)     -- boolean
  
  */
  
  public boolean isReallyOld() {
    return false;
  }
  
  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.isCaptionAvailable(language);
  }
  
  public abstract String format();
}

// represents a movie
class Movie extends AMedia {
  int year;
  
  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }
  
  /*
  TEMPLATE
  FIELDS:
  ... this.year ...                     -- int
  
  METHODS:
  ... this.isReallyOld() ...            -- boolean
  ... this.format() ...                 -- String
  
  METHODS ON FIELDS:
  
  */
  
  public boolean isReallyOld() {
    return this.year < 1930;
  }
  
  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }
  
  /*
  TEMPLATE
  FIELDS:
  ... this.showName ...                     -- String
  ... this.seasonNumber ...                 -- int
  ... this.episodeOfSeason ...              -- int
  
  METHODS:
  ... this.format() ...                     -- String
  
  METHODS ON FIELDS:
  
  */

  public String format() {
    return this.showName + " " + this.seasonNumber + "." + this.episodeOfSeason
        + " - " + this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;
  
  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }
  
  /*
  TEMPLATE
  FIELDS:
  ... this.channelName ...              -- String
  
  METHODS:
  ... this.format() ...                 -- String
  
  METHODS ON FIELDS:
  
  */
  
  public String format() {
    return this.title + " by " + this.channelName;
  }
}

// lists of strings
interface ILoString {
  
  // are captions available in this language?
  boolean isCaptionAvailable(String language);
}

// an empty list of strings
class MtLoString implements ILoString {
  
  /*
  TEMPLATE
  FIELDS:

  METHODS:
  ... this.isCaptionAvailable(String) ...        -- boolean
  
  METHODS ON FIELDS:
  
  */
  
  public boolean isCaptionAvailable(String language) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /*
  TEMPLATE
  FIELDS:
  ... this.first ...                             -- String
  ... this.rest ...                              -- ILoString
  
  METHODS:
  ... this.isCaptionAvailable(String) ...        -- boolean
  
  METHODS ON FIELDS:
  ... this.rest.isCaptionAvailable(String) ...   -- boolean 
  
  */
  
  public boolean isCaptionAvailable(String language) {
    if (this.first.equals(language)) {
      return true;
    } 
    else {
      return this.rest.isCaptionAvailable(language);
    }
  }
}

class ExamplesMedia {
  ILoString mtList = new MtLoString();
  ILoString english = new ConsLoString("English", this.mtList);
  ILoString french = new ConsLoString("French", this.english);
  ILoString languages = new ConsLoString("Spanish", this.french);
  IMedia theFavourite = new Movie("The Favourite", 2018, this.languages);
  IMedia aTripToTheMoon = new Movie("A Trip to the Moon", 1903, this.languages);
  
  IMedia madMen = new TVEpisode("Smoke Gets In Your Eyes", "Mad Men", 1, 1, this.languages);
  IMedia theOffice = new TVEpisode("Pilot", "The Office", 1, 1, this.mtList);
  
  IMedia theLonelyIsland = new YTVideo("Threw It On The Ground", "thelonelyisland", this.languages);
  IMedia chiefsVsEagles = new YTVideo("Kansas City Chiefs vs Philadeplia Eagles | "
      + "2023 Super Bowl Game Preview", "NFL", this.mtList);
  
  // test isReallyOld method
  boolean testIsReallyOld(Tester t) {
    return t.checkExpect(this.aTripToTheMoon.isReallyOld(), true)
        && t.checkExpect(this.madMen.isReallyOld(), false)
        && t.checkExpect(this.theLonelyIsland.isReallyOld(), false);
  }
  
  // test isCaptionAvailable method
  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(this.aTripToTheMoon.isCaptionAvailable("English"), true)
        && t.checkExpect(this.madMen.isCaptionAvailable("English"), true)
        && t.checkExpect(this.theLonelyIsland.isCaptionAvailable("English"), true)
        && t.checkExpect(this.theLonelyIsland.isCaptionAvailable("Japanese"), false)
        && t.checkExpect(this.chiefsVsEagles.isCaptionAvailable("English"), false);
  }
  
  // test format method
  boolean testFormat(Tester t) {
    return t.checkExpect(this.theFavourite.format(), "The Favourite (2018)")
        && t.checkExpect(this.madMen.format(), 
            "Mad Men 1.1 - Smoke Gets In Your Eyes")
        && t.checkExpect(this.theLonelyIsland.format(), 
            "Threw It On The Ground by thelonelyisland");
  }
}