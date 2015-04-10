package authoring.userInterface;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Really needs some refactoring
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
//	
//	// TODO: needs to be refactored
//	public void addToEnd(String colOrRow, Node...args){
//		int arg1;
//		int arg2;
//		
//		if (colOrRow == "column"){
//			arg1 = myNumRows;
//			arg2 = myNumCols;
//		}
//		else{
//			arg1 = myNumCols;
//			arg2 = myNumRows;
//		}
//		
//		if (args.length > arg1){
//			System.out.println("Too many arguments");
//		}
//		else{
//			arg2++;
//			for (int i = 0; i < args.length; i++){
//				this.add(args[i], arg2, i);
//			}
//		}
//		
//	}
	
}
