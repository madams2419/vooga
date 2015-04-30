package authoring.dataEditors;

/**
 * Wrapper class for actions
 * @author Andrew Sun
 *
 */
public class Action {
	
	private String myAction;
	private String myDescription;
	private String myParamDescription;
	private int myNumParams;
	
	public Action(String action, String description, String paramDescription,
	              int numParams){
		setAction(action);
		setDescription(description);
		setParamDescription(paramDescription);
		myNumParams = numParams;
	}

	public String getAction() {
		return myAction;
	}

	public void setAction(String action) {
		myAction = action;
	}

	public String getDescription() {
		return myDescription;
	}

	public void setDescription(String description) {
		myDescription = description;
	}
	
	public boolean checkParams(String s){
		String[] result = s.split(" ");
		return (result.length == myNumParams);
	}
	
	@Override
	public String toString(){
		return myAction;
	}

    public String getParamDescription () {
        return myParamDescription;
    }

    public void setParamDescription (String paramDescription) {
        myParamDescription = paramDescription;
    }
}
