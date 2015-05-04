
package authoring.userInterface;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Creates basic grid to base dialog on
 * @author Andrew
 *
 */
public class DialogGridOrganizer extends GridPane{
	private int myNumCols;
	private int myNumRows;
	
	public DialogGridOrganizer(int numCols){
		myNumCols = numCols;
		myNumRows = 0;
	}
	
	public void addRowEnd(Node...args){
		if (args.length > myNumCols){
			System.out.println("Too many arguments");
		}
		else{
			for (int i = 0; i < args.length; i++){
				this.add(args[i], i, myNumRows);
			}
			myNumRows++;
		}
	}
}
