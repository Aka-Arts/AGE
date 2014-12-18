package org.akaarts.AGE.utils;

import java.util.ArrayList;

public interface NodeStructure {
	
	/**
	 * Default children getter
	 * @return - ArrayList of children
	 */
	public ArrayList<NodeStructure> getChildren();
	
	/**
	 * Default child adder.<br>
	 * This function should also automatically do:
	 * <pre>
	 * child.getParent().removeChild(child);
	 * child.setParent(this);
	 * </pre>
	 * @param child - the child node to add
	 */
	public void addChild(NodeStructure child);
	
	/**
	 * Remove's the given child from this.children
	 * @param child - the child object to remove
	 */
	public void removeChild(NodeStructure child);
	
	/**
	 * Default parent getter
	 * @return - this.parent
	 */
	public NodeStructure getParent();

	/**
	 * Default parent setter.<br>
	 * This function should also automatically do:
	 * <pre>
	 * parent.addChild(this);
	 * </pre>
	 * @param parent - the parent to set
	 */
	public void setParent(NodeStructure parent);
}
