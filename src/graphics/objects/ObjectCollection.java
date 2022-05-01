package graphics.objects;

import java.util.ArrayList;

public class ObjectCollection {
	private String name;
	private Transform transform;
	private ArrayList<ShapeObject> shapeObjects;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Transform transform() {
		return this.transform;
	}
	
	public ArrayList<ShapeObject> getObjects() {
		return this.shapeObjects;
	}
	
	public void set(ArrayList<ShapeObject> shapeObjects) {
		this.shapeObjects = shapeObjects;
	}
	
	public void set(ShapeObject[] shapeObjects) {
		for (ShapeObject obj : shapeObjects) {
			this.shapeObjects.add(obj);
		}
	}
	
	public void add(ShapeObject shapeObject) {
		this.shapeObjects.add(shapeObject);
	}
		
	public ObjectCollection(String name) {
		this.name = name;
		this.shapeObjects = new ArrayList<>();
	}
	
	public ObjectCollection(String name, ArrayList<ShapeObject> shapeObjects) {
		this.name = name;
		this.shapeObjects = shapeObjects;
	}
	
}
