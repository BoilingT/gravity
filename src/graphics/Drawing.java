package graphics;

import java.awt.Color;
import java.util.ArrayList;

import global.math.Vector2;
import graphics.objects.ObjectCollection;
import graphics.objects.ShapeObject;

public class Drawing {
	
	//Singleton
	private static Drawing instance = null;
	
	private static Color color = Color.black;
	private static boolean fill = false;
	private static boolean draw = true;
	private static long delay = 0;
	private static ArrayList<ObjectCollection> objectCollection = new ArrayList<>();
	private static ArrayList<ShapeObject> objects = new ArrayList<>();
	
	public Drawing() {
		
	}
	
	public static Drawing getInstance() {
		if (instance == null) {
			instance = new Drawing();
		}
		
		return instance;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean shouldDraw() {
		return draw;
	}
	
	public long getDelay() {
		return delay;
	}
	
	public ArrayList<ObjectCollection> getCollections(){
		return objectCollection;
	}
	
	public ObjectCollection getCollection(int index) {
		return objectCollection.get(index);
	}
	
	public ArrayList<ShapeObject> getObjects(){
		return objects;
	}
	
	public void setObjects(ArrayList<ShapeObject> objects) {
		this.objects = objects;
	}
	
	public ShapeObject getObject(int index) {
		return objects.get(index);
	}
	
	public ShapeObject getObject(ShapeObject obj) {		
		return objects.get(objects.indexOf(obj));
	}
		
	public void addShape(ShapeObject obj) {
		objects.add(obj);
	}
	
	public boolean deleteShape(ShapeObject obj) {
		return objects.remove(obj);
	}

	public ShapeObject addRect(Vector2<Float> pos, float width, float height, String name) {
		ShapeObject rect = new ShapeObject(Shapes.Rect(pos, width, height), color, fill);
		rect.setName(name);
		addShape(rect);
		return rect;
	}
	
	public ShapeObject addArc(Vector2<Float> pos, float width, float height, String name) {
		ShapeObject arc = new ShapeObject(Shapes.Arc(pos, width, height), color, fill);
		arc.setName(name);
		addShape(arc);
		return arc;
	}
	
	public ShapeObject addLine(Vector2<Float> origin, Vector2<Float> destination, String name) {
		ShapeObject line = new ShapeObject(Shapes.Line(origin, destination), color, fill);
		line.setName(name);
		addShape(line);
		return line;
	}
	
	public ObjectCollection addCollection(ObjectCollection collection) {
		objectCollection.add(collection);
		return collection;
	}
	
	public void deleteCollection(ObjectCollection collection) {
		objectCollection.remove(collection);
	}
	
	public void rotate(int index, float theta, float offx, float offy) {
		objects.get(index).rotate(theta, offx, offy);
	}
	
	public void stop() {
		draw = false;
	}
	
	public void play() {
		draw = true;
	}
	
	public void setDelay(int delay) {
		if (delay > 0) {
			Drawing.delay = delay;
		}
	}
}
