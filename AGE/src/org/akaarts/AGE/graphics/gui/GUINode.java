package org.akaarts.AGE.graphics.gui;

import java.util.ArrayList;

import org.akaarts.AGE.utils.NodeStructure;

public class GUINode implements NodeStructure {

	private GUINode parent;
	private GUIElement element;
	private ArrayList<NodeStructure> children = new ArrayList<NodeStructure>();

	/**
	 * Default constructor
	 */
	public GUINode() {
	}
	
	/*
	 * 
	 * 
	 * 
	 * TODO move all of GUIElement into GUINode, rename TextElement to TextNode
	 * node is an element (empty by default)
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * Draws this.element and all children
	 */
	public void draw() {
		if(this.element!=null) {
			this.element.draw();	
		}
		for (NodeStructure child : this.children) {
			((GUINode) child).draw();
		}
	}

	/**
	 * Updates itself and all children
	 */
	public void update() {
		if(this.element!=null) {
			this.element.update();	
		}
		for (NodeStructure child : this.children) {
			((GUINode) child).update();
		}
	}

	public void destroy() {
		if(this.element!=null) {
			this.element.destroy();
		}
		for (NodeStructure child : this.children) {
			((GUINode) child).destroy();
		}
	}

	/**
	 * Getter for this node's GUIElement
	 * 
	 * @return - this.GUIElement
	 */
	public GUIElement getElement() {
		return this.element;
	}

	/**
	 * Setter for this node's GUIElement
	 * 
	 * @param element
	 *            - the new GUIElement
	 */
	public void setElement(GUIElement element) {
		if(this.element!=null) {
			this.element.destroy();
		}
		this.element = element;
	}

	@Override
	public ArrayList<NodeStructure> getChildren() {
		return this.children;
	}

	@Override
	public void addChild(NodeStructure child) {
		this.children.add(child);
		child.getParent().removeChild(child);
		child.setParent(this);
	}

	@Override
	public void removeChild(NodeStructure child) {
		this.children.remove(child);
	};

	@Override
	public NodeStructure getParent() {
		return this.parent;
	}

	@Override
	public void setParent(NodeStructure parent) {
		this.parent = (GUINode) parent;
		parent.addChild(this);
	}
}
