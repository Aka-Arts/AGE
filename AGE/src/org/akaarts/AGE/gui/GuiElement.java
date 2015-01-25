package org.akaarts.AGE.gui;

import java.util.ArrayList;

import org.akaarts.AGE.input.KeyEvent;
import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.utils.Vector2i;
import org.lwjgl.util.Rectangle;


public abstract class GuiElement implements GuiEventListener{

	private GuiElement parent;
	private ArrayList<GuiElement> children = new ArrayList<GuiElement>();
	
	private Vector2i dimension;
	private Vector2i position;
	
	private Rectangle aabb;
	
	public GuiElement() {
		
		this.position = new Vector2i(0, 0);
		this.dimension  = new Vector2i(100, 100);
		
		this.computeAABB();
		
	}
	
	/**
	 * Returns all child elements as ArrayList
	 * @return - all children
	 */
	public final ArrayList<GuiElement> getChildren() {
		
		return this.children;
		
	}

	/**
	 * Adds the given child to this.children and sets itself as parent of the child
	 * @param child - the child to add
	 */
	public final void addChild(GuiElement child) {
		
		if(!this.children.contains(child)) {
		
			GuiElement formerParent = child.getParent();
			
			if(formerParent!=this&&formerParent!=null){
				
				child.getParent().removeChild(child);
				
			}
			
			this.children.add(child);
			
			child.setParent(this);
			
		}
		
	}
	
	/**
	 * Removes the given child from this.children<br>
	 * Don't forget to destroy unreferenced children, otherwise memLeaks will be created!!
	 * @param child - the child
	 */
	public final void removeChild(GuiElement child) {
		
		this.children.remove(child);
		
	};

	/**
	 * returns the parent of this element
	 * @return - the parent
	 */
	public final GuiElement getParent() {
		
		return this.parent;
		
	}
	
	/**
	 * Sets this elements new parent and removes this element from former parents children
	 * @param parent
	 */
	public final void setParent(GuiElement parent) {
		
		if(this.parent!=parent) {

			if(this.parent!=null){
				
				this.parent.removeChild(this);
				
			}

			parent.addChild(this);
			
			this.parent = parent;
			
		}
		
	}
	
	boolean onClickInternal(MouseEvent e) {

		if(this.aabb.contains(e.x, e.y)) {
		
			if(this.onClick(e)) {
				
				return true;
			
			}
			
		}
		
		for(GuiElement child : this.children) {
			
			if(child.onClickInternal(e)) {
				
				return true;
				
			}
				
		}
		
		return false;

	}

	boolean onHoverInternal(MouseEvent e) {

		if(this.aabb.contains(e.x, e.y)) {
			
			if(this.onHover(e)) {
				
				return true;
				
			}	
			
		}
		
		for(GuiElement child : this.children) {
			
			if(child.onHoverInternal(e)) {
				
				return true;
				
			}
				
		}
		
		return false;
	
	}

	boolean onKeyInternal(KeyEvent e) {

		if(this.onKey(e)) {
			
			return true;
			
		}
		
		for(GuiElement child : this.children) {
			
			if(child.onKey(e)) {
				
				return true;
				
			}
				
		}
		
		return false;

	}	

	private void computeAABB() {
		
		this.aabb = new Rectangle(this.position.x, this.position.y, this.dimension.x, this.dimension.y);
		
	}
	
}
