package org.akaarts.AGE.utils;

import java.util.ArrayList;

public class Node {
	
	private ArrayList<Node> subNodes = new ArrayList<Node>();

	public Node() {
		
	}

	/**
	 * @return the subNodes
	 */
	public ArrayList<Node> getSubNodes() {
		return subNodes;
	}

	/**
	 * @param subNode - the subNode to add
	 */
	public void addNode(Node node) {
		this.subNodes.add(node);
	}
	
	/**
	 * @param subNode - the subNode to remove
	 */
	public void remNode(Node node) {
		this.subNodes.remove(node);
	}
	

}
