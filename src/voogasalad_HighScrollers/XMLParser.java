package voogasalad_HighScrollers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class XMLParser {
	private static XMLParser sharedInstance;
	private static String mCurrentFile;
	private static Map<String,String> labelMap;
	private ResourceBundle resources;

	public static XMLParser getSharedInstance(String fileName) {
		mCurrentFile = mCurrentFile == null ? fileName : mCurrentFile;
		if(sharedInstance == null || !mCurrentFile.equals(fileName)) {
			sharedInstance = new XMLParser(mCurrentFile);
		}
		return sharedInstance;
	}
	
	public static XMLParser getSharedInstance(){
		return getSharedInstance(mCurrentFile);
	}

	private XMLParser(String language) {
		sharedInstance = this;
		getResources(mCurrentFile);
	}

	public ResourceBundle getResources(String fileName) {
		if(resources == null || !fileName.equals(mCurrentFile)) {
			resources = ResourceBundle.getBundle(String.format("resources.languages.%s", fileName));
			mCurrentFile = fileName;
		}
		return resources;
	}

	public ResourceBundle getResources(){
		return getResources(mCurrentFile);
	}

	private Map<String,String> createLabels() {		
		HashMap<String, String> labelMap = new HashMap<String, String>();
		// A collection of buttons in a map
		// that allows you to pick buttons to add
		Enumeration<String> mElements = resources.getKeys();
		while (mElements.hasMoreElements()) {
			String key = mElements.nextElement();
			String label = resources.getString(key);
			labelMap.put(key,label);
		}
		return labelMap;
	}

//	public String getCommandName(String s) {
//		for(String command:resources.keySet()){
//			String[] possibleCommands = resources.getString(command).split("\\|");
//			for(String value: possibleCommands) {
//				if(value.replace("\\", "").equals(s)) {
//					return command;
//				}
//			}
//		}
//		return s;
//	}
	
	public Map<String,String> getLabelMap() {
		if(labelMap == null)
			labelMap = createLabels();
		return labelMap;
	}

	public void changeLocale(String locale){
		this.getResources(locale);
		labelMap = createLabels();
	}
		

}
