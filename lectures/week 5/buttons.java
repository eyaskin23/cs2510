import javalib.worldimages.*;
import java.awt.Color;
import tester.Tester;

interface ILoButton {
	
}

class Button {
	int radius;
	OutlineMode outline;
	Color color;
	
	Button(int radius, OutlineMode outline, Color color) {
		this.radius = radius;
		this.outline = outline;
		this.color = color;
	}
}

class ConsLoButton {
	Button first;
	ILoButton rest;
	
	ConsLoButton(Button first, ILoButton rest) {
		this.first = first;
		this.rest = rest;
	}
}

class ExamplesButtons { 
	Button redButton = new Button(30, OutlineMode.OUTLINE, Color.RED);

	Button testButton(Tester t) {
		return redButton;
		
	}

}