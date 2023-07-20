interface IIcecream {
	
}

//represents an empty serving of ice cream
class EmptyServing implements IIcecream {
	boolean cone;
	
	EmptyServing(boolean cone) {
		this.cone = cone;
	}
}

// represents an ice cream with at least one scoop
class Scooped implements IIcecream {
	IIcecream more;
	String flavor;
	
	Scooped(IIcecream more, String flavor) {
		this.more = more;
		this.flavor = flavor;
	}
}

class ExamplesIIcecream {
	IIcecream sugarCone = new EmptyServing(true);
	IIcecream scoop1 = new Scooped(this.sugarCone, "chocolate");
	IIcecream scoop2 = new Scooped(this.scoop1, "vanilla"); 
	IIcecream scoop3 = new Scooped(this.scoop2, "strawberry");
	
}