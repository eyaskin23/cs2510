//represents a certain mode for a student
interface Mode {

}

//represents a location of the student
interface IPlace {

}

//represents a classroom
class Classroom implements IPlace { 
  String name;
  int roomCapacity;
  int occupantCount; 

  Classroom(String name, int roomCapacity, int occupantCount) {
    this.name = name;
    this.roomCapacity = roomCapacity;
    this.occupantCount = occupantCount;
  }
} 
 
//represents a gym
class Gym implements IPlace {
  String name;
  int exerciseMachines;
  int patrons;
  boolean open;

  Gym(String name, int exerciseMachines,
      int patrons, boolean open) {
    this.name = name;
    this.exerciseMachines = exerciseMachines;
    this.patrons = patrons;
    this.open = open;
  }
}

//represents a student center
class StudentCenter implements IPlace {
  String name;
  boolean open;

  StudentCenter(String name, boolean open) {
    this.name = name;
    this.open = open;
  }
}

//represents a studying mode
class Study implements Mode {
  String subject;
  boolean examReview;

  Study(String subject, boolean examReview) {
    this.subject = subject;
    this.examReview = examReview;
  }
}

//represents a socializing mode
class Socialize implements Mode {
  String description;
  int friends;

  Socialize(String description, int friends) {
    this.description = description;
    this.friends = friends;
  }
}

//represents an exercise mode
class Exercise implements Mode {
  String name;
  boolean aerobic;

  Exercise(String name, boolean aerobic) {
    this.name = name;
    this.aerobic = aerobic;
  }
}

//represents a Student with a name, location, gpa, and major
class SimStudent {
  String name;
  Mode mode;
  IPlace location;
  double gpa;
  String major; 

  SimStudent(String name, Mode mode, IPlace location, double gpa, String major) {
    this.name = name;
    this.mode = mode;
    this.location = location;
    this.gpa = gpa;
    this.major = major;
  }
}
 
//represents examples for students
class ExamplesSims {
  ExamplesSims() {}

  IPlace shillman105 = new Classroom("Shillman 105", 115, 86); 
  IPlace marino = new Gym("Marino Recreation Center", 78, 47, true);
  IPlace curry = new StudentCenter("Curry Student Center", false);

  Mode exercise = new Exercise("biceps", false);
  Mode socialize = new Socialize("Party", 25);
  Mode study = new Study("fundies", false); 
  
  SimStudent student1 = new SimStudent("Evelyn", study, curry, 3.0, 
      "Computer Science and Design");
  SimStudent student2 = new SimStudent("Simone", socialize, curry, 3.6, 
      "Data Science and Psych");
  SimStudent student3 = new SimStudent("Priyanka", exercise, marino, 4.0, 
      "Data Science and Math");
  SimStudent student4 = new SimStudent("Ruchira", study, shillman105, 2.9, 
      "Data Science and Economics"); 
}








