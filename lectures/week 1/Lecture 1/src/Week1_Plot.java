interface IPlot {
	
	
}

// represents an end to the plot
class Ending implements IPlot {
	String description;
	
	Ending(String description) {
		this.description = description;
	}
}

// represents an Event in a plot
class Event implements IPlot {
	String character;
	String description;
	IPlot left;
	IPlot right;
	
	Event (String c, String d, IPlot left, IPlot right) {
		this.character = c;
		this.description = d;
		this.left = left;
		this.right = right;
	}
}

class ExamplesEvent {
	
	Event Cinderella = new Ending("happily ever after");
	Event Cinderella1 = new Event(this.Cinderella, "Cinderella", "meets the prince", 
			left, right);
}