package main;

import graphics.objects.ShapeObject;

public class ObjectHandler {
	private static ObjectHandler objectHandler;
		
	public ObjectHandler getInstance() {
		if (objectHandler == null) {
			objectHandler = new ObjectHandler();
		}
		
		return objectHandler;
	}
	
	public void addObject(String name, ShapeObject[] shapeObjects) {
		
	}
	
	public void deleteObject(String name) {
		
	}
}
