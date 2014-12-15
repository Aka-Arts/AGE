package org.akaarts.AGE.utils;

import java.util.ArrayList;

public class Node {
	
	private ArrayList<Node> children = new ArrayList<Node>();
	private Node parent;
	
	public Node(){
		
	}
	
	/**
	 * Default children getter
	 * @return ArrayList of children
	 */
	public ArrayList<Node> getChildren(){
		return this.children;
	}
	
	/**
	 * Default child adder
	 * @param child - the childnode to add
	 */
	public void addChild(Node child){
		this.children.add(child);
		child.setParent(this);
	}

	/**
	 * 
	 * @param parent
	 */
	protected void setParent(Node parent) {
		this.parent = parent;
	}
	
	/**
	 * 
	 * @return
	 */
	public Node getParent(){
		return this.parent;
	}

}
