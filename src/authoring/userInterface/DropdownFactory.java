package authoring.userInterface;
/**
 * 
 */

import java.util.ArrayList;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * @author hojeanniechung
 *
 */
public class DropdownFactory {

        private static DropdownFactory mInstance;
//        private static Reflection reflection=new Reflection();

        /*==============================Constructors=================================================*/

        public static DropdownFactory getSharedInstace() {
            if(mInstance==null)
                mInstance=new DropdownFactory();
            return mInstance;
            // TODO Auto-generated constructor stub
        }

        public DropdownFactory(){
            System.out.println("Reached public constructor for DropdownFactory");

        }


        public static ComboBox generateDropdown(Map<String,String> m){
            ArrayList<String> labels = new ArrayList<String>();
            for(int i=0;i<m.size();i++){
                String label = "label"+i;
                if(m.containsKey(label)){
                    labels.add(m.get(label));
                }
            }
            ObservableList<String> options = FXCollections.observableArrayList(labels);
            final ComboBox<String> comboBox = new ComboBox<String>();
            comboBox.getItems().addAll(options);
            return comboBox;
        }

}