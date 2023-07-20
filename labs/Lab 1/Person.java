// Lab 1 (Problem 1) 


// (define Tim (make-person "Tim" 23 "Male"))
// define Kate (make-person "Kate" 23 "Female")
// define Rebecca (make-person "Rebecca" 23 "Non-binary")



//+---------------+
//| Person        |
//+---------------+
//| String name   |
//| int age       |
//| String gender |---+
//+---------------+   |
//                    | 
//                    v
			// +---------------+
			// | Address       |
			// +---------------+
			// | String city   |
			// | String state  |
			// +---------------+


// to represent a person
class Person {
	String name;
	int age;
	String gender;
	Address address;
	
	// the constructor
	Person(String name, int age, String gender, Address address){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}
}

// to represent an address of a person
class Address {
	String city;
	String state;
	
	// the constructor
	Address(String city, String state) {
		this.city = city;
		this.state = state;
	}
}

 
class ExamplesPersons {
  ExamplesPersons () {}
	
	Person tim = new Person("Tim", 23, "Male", new Address("Boston", "MA"));
	
	Person rebecca = new Person("Rebecca", 23, "Non-binary", new Address ("Boston", "MA")); 
	
}
















