import tester.Tester;

interface ICircuit {

  int countComponents();
  
  double totalVoltage();
  
  double totalCurrent();
  
  double totalResistance();
  
  ICircuit reversePolarity();
  
}

//Battery: representing a battery with constant voltage 

class Battery implements ICircuit {
  String name;
  double voltage;
  double nominalResistance;

  Battery(String name, double voltage, double nominalResistance) {
    this.name = name;
    this.voltage = voltage;
    this.nominalResistance = nominalResistance;
  }
  
  /* Template:
   * Fields: 
   * this.name -- String
   * this.voltage -- double
   * this.nominalResistance -- double
   * Methods: 
   * this.countComponents()
   * this.totalVoltage()
   * this.totalCurrent()
   * this.reversePolarity()
   */
 
  public int countComponents() {
 
    return 1;
  }

  public double totalVoltage() {
  
    return this.voltage;
  }

  public double totalCurrent() {
 
    return 0.0;
  }

  public ICircuit reversePolarity() {

    return new Battery(this.name, this.voltage * -1, this.nominalResistance);
  }


public double totalResistance() {
	return this.nominalResistance;
  }
}

//Resistor: represents a passive component with 
//a fixed resistance

class Resistor implements ICircuit {
  String name;
  double resistance;

  Resistor(String name, double resistance) {
    this.name = name;
    this.resistance = resistance;
  }
  
  /* Template:
   * Fields: 
   * this.name -- String
   * this.resistance -- double
   * Methods:
   * this.totalResistance()
   * this.countComponents()
   * this.totalVoltage()
   * this.totalCurrent()
   * this.reversePolarity()
   */
  
  public int countComponents() {
    return 1;
  }
  
  public double totalVoltage() {
    return 0;
  }

  public double totalCurrent() {
    return 0;
  }

  public ICircuit reversePolarity() {
    return new Resistor(this.name, this.resistance);
  }


  public double totalResistance() {
	return this.resistance;
  }
} 

//represents two circuit components combined
class Series implements ICircuit {
  ICircuit first;
  ICircuit second;

  Series(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second; 
  }
  
  /* Template:
   * Fields: 
   * this.first -- ICircuit
   * this.second -- ICircuit
   * Methods:
   * this.totalResistance()
   * this.countComponents()
   * this.totalVoltage()
   * this.totalCurrent()
   * this.reversePolarity()
   */

  public int countComponents() {
    return first.countComponents() 
      + second.countComponents();
  }

  public double totalVoltage() {
    return this.first.totalVoltage() 
      + this.second.totalVoltage();
  }

  public double totalCurrent() {
    return this.totalVoltage() /
    		this.totalResistance();
    		
  }
 
  public double totalResistance() {
	return this.first.totalResistance() +
			this.second.totalResistance();
}

public ICircuit reversePolarity() {
    return new Series(this.second.reversePolarity(),
      this.first.reversePolarity());
  }
}

//represents two circuits combined by respective matching terminals
class Parallel implements ICircuit {
  ICircuit first;
  ICircuit second;

  Parallel(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }
  
  /* Template:
   * Fields: 
   * this.first -- ICircuit
   * this.second -- ICircuit
   * Methods: 
   * this.totalResistance()
 * this.countComponents()
 * this.totalVoltage()
 * this.totalCurrent()
 * this.reversePolarity()
   */ 

  public int countComponents() {
    return first.countComponents() 
      + second.countComponents();
  }

  public double totalVoltage() {
    return this.first.totalVoltage();
  }

  public double totalCurrent() {
    return this.first.totalCurrent() 
      + this.second.totalCurrent();
  }

  public ICircuit reversePolarity() {
    return new Parallel(this.first.reversePolarity(),
      this.second.reversePolarity());
  }

public double totalResistance() {
	return this.totalVoltage() /
    		this.totalResistance();
  }
}
//Examples of certain circuits
class ExamplesCircuits {

  ExamplesCircuits() {} 
  
  ICircuit batteryOne = new Battery("B 1", 10, 25);
  ICircuit batteryTwo = new Battery("B 2", 20, 0);
  ICircuit resistorOne = new Resistor("R 1", 100);
  ICircuit resistorTwo = new Resistor("R 2", 250);
  ICircuit resistorThree = new Resistor("R 3", 500);
  ICircuit resistorFour = new Resistor("R 4", 200);
  ICircuit resistorFive = new Resistor("R 5", 50);
  ICircuit simpleSeries = new Series(
      new Battery("B 1", 10, 25),
      new Resistor("R 1", 100));
  ICircuit morePower = new Series( 
      (new Battery("B 1", 10, 25)),
      (new Battery("B 2", 20, 0.4)));
  ICircuit complexCircuit = new Series(
      new Parallel(
      new Battery("B 1", 30, 25),
      new Resistor("R 1", 100)),
      new Parallel(
      new Battery("B 2", 20, 25),
      new Resistor("R 2", 50)));
          
  boolean testCountComponents(Tester t) {
    return t.checkExpect(this.batteryOne.countComponents(), 1);
 
  }

  boolean testCountComponents1(Tester t) {
    return t.checkExpect(this.batteryTwo.countComponents(), 1);
 
  }

  boolean testCountComponents2(Tester t) {
    return t.checkExpect(this.resistorOne.countComponents(), 1);

  }

  boolean testCountComponents3(Tester t) {
    return t.checkExpect(this.resistorTwo.countComponents(), 1);
 
  }

  boolean testCountComponents4(Tester t) {
    return t.checkExpect(this.simpleSeries.countComponents(), 2);

  }

  boolean testCountComponents5(Tester t) {
    return t.checkExpect(this.morePower.countComponents(), 2);
  
  }

  boolean testCountComponents6(Tester t) {
    return t.checkExpect(this.complexCircuit.countComponents(), 4);
 
  }

  boolean testTotalVoltage(Tester t) {
    return t.checkExpect(this.batteryOne, 
      (new Battery("B 1", 10, 25)));

  }

  boolean testTotalVoltage2(Tester t) {
    return t.checkExpect(this.batteryTwo, 
      new Battery("B 2", 20, 0));

  }

  boolean testTotalVoltage3(Tester t) {
    return t.checkExpect(this.resistorOne, 
    new Resistor("R 1", 100.0));

  }

  boolean testTotalVoltage4(Tester t) {
    return t.checkExpect(this.resistorTwo, 
       new Resistor("R 2", 250));
  }

  boolean testTotalVoltage5(Tester t) {
    return t.checkExpect(this.simpleSeries,
      new Series(
       new Battery("B 1", 10, 25),
       new Resistor("R 1", 100)));

  }

  boolean testTotalVoltage6(Tester t) {
    return t.checkExpect(this.morePower,
      new Series(
       new Battery("B 1", 10, 25),
       new Battery("B 2", 20, 0.4)));
  }
 
  boolean testTotalCurrent(Tester t) {
    return t.checkExpect(this.batteryOne, 
      (new Battery("B 1", 10, 25)));
  }

  boolean testTotalCurrent1(Tester t) { 
    return t.checkExpect(this.batteryTwo, 
      (new Battery("B 2", 20, 0)));
  }

  boolean testTotalCurrent2(Tester t) {
    return t.checkExpect(this.resistorOne, 
      new Resistor("R 1", 100));
 
  }

  boolean testTotalCurrent3(Tester t) {
    return t.checkExpect(this.resistorTwo, 
      (new Resistor("R 2", 250)));
  
  }

  boolean testTotalCurrent4(Tester t) {
    return t.checkExpect(this.simpleSeries, 
      (new Series(
      (new Battery("B 1", 10, 25)),
      (new Resistor("R 1", 100)))));
  }
  
  boolean testTotalCurrent5(Tester t) {
    return t.checkExpect(this.morePower,
      (new Series(
      new Battery("B 1", 10, 25),
      new Battery("B 2", 20, 0.4))));
 
  }

  boolean testTotalCurrent6(Tester t) {
    return t.checkExpect(this.complexCircuit, 
      new Series(new Parallel(
      new Battery("B 1", 30, 25),
      new Resistor("R 1", 100)),
      new Parallel(
      new Battery("B 2", 20, 25),
      new Resistor("R 2", 50))));
  }

  boolean testReversePolarity(Tester t) {
    return t.checkExpect(this.batteryOne, 
      new Battery("B 1", 10, 25));
 
  }

  boolean testReversePolarity1(Tester t) {
    return t.checkExpect(this.batteryTwo, new Battery("B 2", 20.0, 0));
  }

  boolean testReversePolarity2(Tester t) {
    return t.checkExpect(this.resistorOne, new Resistor("R 1", 100));
    		
 
  }

  boolean testReversePolarity3(Tester t) {
    return t.checkExpect(this.resistorTwo, new Resistor("R 2", 250));
  
  }

  boolean testReversePolarity4(Tester t) {
    return t.checkExpect(this.simpleSeries, (new Series(
      new Battery("B 1", 10, 25),
      (new Resistor("R 1", 100)))));

  }

  boolean testReversePolarity5(Tester t) {
    return t.checkExpect(this.morePower, (new Series(
      new Battery("B 1", 10.0, 25.0), 
      (new Battery("B 2", 20.0, 0.4)))));
  }

  boolean testReversePolarity6(Tester t) {
    return t.checkExpect(this.complexCircuit, 
     new Series(
     new Parallel(
     new Battery("B 1", 30, 25),
     new Resistor("R 1", 100)), 
     new Parallel(
     new Battery("B 2", 20, 25),
     new Resistor("R 2", 50))));
  }
}
 










