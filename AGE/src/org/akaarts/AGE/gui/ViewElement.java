package org.akaarts.AGE.gui;

import java.util.ArrayList;

import org.akaarts.AGE.input.MouseEvent;
import org.akaarts.AGE.utils.Vector2i;
import org.lwjgl.util.Rectangle;


public abstract class ViewElement implements ViewEventListener{

	private ViewElement parent;
	private ArrayList<ViewElement> children = new ArrayList<>();
	
	private Vector2i dimension;
	private Vector2i position;
	
	private String texturePath;
	
	private Rectangle aabb;
	
	public ViewElement() {
		
		this.position = new Vector2i(0, 0);
		this.dimension  = new Vector2i(100, 100);
		
		this.computeAABB();
		
	}
	
	// texture and render
	
	final void render(){
	
		// TODO
		
	}
	
	public final void setTexture(String path){
		
		this.texturePath = path;
					
	}
	
	// tree methods
	
	/**
	 * Returns all child elements as ArrayList
	 * @return - all children
	 */
	public final ArrayList<ViewElement> getChildren() {
		
		return this.children;
		
	}

	/**
	 * Adds the given child to this.children and sets itself as parent of the child
	 * @param child - the child to add
	 */
	public final void addChild(ViewElement child) {
		
		if(!this.children.contains(child)) {
		
			ViewElement formerParent = child.getParent();
			
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
	public final void removeChild(ViewElement child) {
		
		this.children.remove(child);
		
	};

	/**
	 * returns the parent of this element
	 * @return - the parent
	 */
	public final ViewElement getParent() {
		
		return this.parent;
		
	}
	
	/**
	 * Sets this elements new parent and removes this element from former parents children
	 * @param parent
	 */
	public final void setParent(ViewElement parent) {
		
		if(this.parent!=parent) {

			if(this.parent!=null){
				
				this.parent.removeChild(this);
				
			}

			parent.addChild(this);
			
			this.parent = parent;
			
		}
		
	}
	
	// overrides
	
	boolean onClickInternal(MouseEvent e) {

		if(this.aabb.contains(e.x, e.y)) {
		
			if(this.onClick(e)) {
				
				return true;
			
			}
			
		}
		
		for(ViewElement child : this.children) {
			
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
		
		for(ViewElement child : this.children) {
			
			if(child.onHoverInternal(e)) {
				
				return true;
				
			}
				
		}
		
		return false;
	
	}
	
	// internal helper

	private void computeAABB() {
		
		this.aabb = new Rectangle(this.position.x, this.position.y, this.dimension.x, this.dimension.y);
		
	}
	
}
