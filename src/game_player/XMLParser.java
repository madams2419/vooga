package game_player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class XMLParser {
	
	private class Directory {
		
		private String name, value;
		private List<Directory> subDirectories;
		
		public Directory(String n) {
			name = n;
			subDirectories = new ArrayList<>();
		}
		
		public void setValue(String v) {
			value = v;
		}
		
		public void addSubDirectory(Directory directory) {
			subDirectories.add(directory);
		}
		
		public List<Directory> getSubDirectories() {
			return Collections.unmodifiableList(subDirectories);
		}
		
		public Directory getDirectory(String name) {
			for (Directory directory : subDirectories) {
				if (directory.name.equals(name)) {
					return directory;
				}
			}
			return this;
		}
		
		public String toString() {
			return name;
		}
		
		public boolean equals(Object o) {
			try {
				Directory other = (Directory) o;
				return name.equals(other.name);
			}
			catch (ClassCastException e) {
				return false;
			}
		}
	}
	
	private List<Directory> activePath;
	private Directory root;
	
	public XMLParser(File data) {
		activePath = new ArrayList<>();
		root = new Directory("");
		activePath.add(root);
		
		Scanner fileData = null;
		try {
			fileData = new Scanner(data);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		fileData.useDelimiter("<");
		fileData.next();
		while (fileData.hasNext()) {
			String next = fileData.next();
			root.addSubDirectory(readXML(fileData, next));
		}
	}

	private Directory readXML(Scanner content, String start) {
		String open = start.trim();
		Directory dir = new Directory(open.substring(0, open.indexOf('>')));
		if (open.indexOf('>') < open.length() - 1) {
			content.next();
			dir.setValue(open.substring(open.indexOf('>') + 1));
			return dir;
		}
		
		String close = "/" + dir.name;
		String next = "";
		while (!(next = content.next()).substring(0, next.indexOf('>')).equals(close)) {
			dir.addSubDirectory(readXML(content, next));
		}
		return dir;
	}
	
	public void resetActivePath() {
		activePath.clear();
		activePath.add(root);
	}
	
	public void setActivePath(String ... pathName) {
		List<Directory> path = new ArrayList<>();
		Directory current = root;
		for (String step : pathName) {
			String[] components = step.split("/");
			for (int i = 1; i < components.length; i++) {
				String s = components[i];
				if (!current.getSubDirectories().contains(new Directory(s))) {
					return;
				}
				current = current.getDirectory(s);
				path.add(current);
			}
		}
		
		resetActivePath();
		activePath.addAll(path);
	}
	
	public String getActivePath() {
		String path = "";
		for (Directory directory : activePath) {
			path += directory.toString() + "/";
		}
		return path;
	}
	
	public List<String> getValidSubDirectories() {
		List<String> subDirectories = new ArrayList<>();
		for (Directory directory : activePath.get(activePath.size() - 1).getSubDirectories()) {
			if (!directory.getSubDirectories().isEmpty()) {
				subDirectories.add(directory.toString());
			}
		}
		return subDirectories;
	}
	
	public List<String> getValidLabels() {
		List<String> labels = new ArrayList<>();
		for (Directory directory : activePath.get(activePath.size() - 1).getSubDirectories()) {
			if (directory.getSubDirectories().isEmpty()) {
				labels.add(directory.toString());
			}
		}
		return labels;
	}
	
	public void moveDown(String ... path) {
		String pathName = "";
		for (String component : path) {
			pathName += component + "/";
		}
		setActivePath(getActivePath() + pathName);
	}
	
	public void moveUp() {
		moveUp(1);
	}
	
	public void moveUp(int distance) {
		List<Directory> remove = new ArrayList<Directory>();
		for (int i = activePath.size() - 1; i > activePath.size() - 1 - distance && i >= 0; i--) {
		    remove.add(activePath.get(i));
		}
		activePath.removeAll(remove);
	}
	
	public String getValue(String label) {
		return activePath.get(activePath.size() - 1).getDirectory(label).value;
	}
}