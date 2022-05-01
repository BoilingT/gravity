package graphics;

import java.awt.Color;

import global.math.*;
import graphics.objects.ObjectCollection;
import graphics.objects.ShapeObject;

public class GraphicGeometry extends Drawing{

	private static GraphicGeometry instance = null;
	
	public static GraphicGeometry getInstance() {
		if (instance == null) {
			instance = new GraphicGeometry();
		}
		
		return instance;
	}
	
	private ObjectCollection getCollection(ShapeObject[] objects, String name) {
		ObjectCollection collection = new ObjectCollection(name);
		for (ShapeObject shapeObject : objects) {
			try {
				collection.add(shapeObject);
				System.out.println("Hello");
				
			} catch (Exception e) {
				System.out.println("Could not add shapeobject");
				System.out.println(e.getMessage());
			}
		}
		return collection;
	}
		
	public ObjectCollection addTriangle(Vector3<Float> x, Vector3<Float> y, Color color, boolean fill, String name) {
		ObjectCollection collection = getCollection(new ShapeObject[] {
				
				new ShapeObject(Shapes.Line(new Vector2<Float>(x.getX(), y.getX()), new Vector2<Float>(x.getY(), y.getY())), color, false),
				new ShapeObject(Shapes.Line(new Vector2<Float>(x.getY(), y.getY()), new Vector2<Float>(x.getZ(), y.getZ())), color, false),
				new ShapeObject(Shapes.Line(new Vector2<Float>(x.getZ(), y.getZ()), new Vector2<Float>(x.getX(), y.getX())), color, false)
				
		}, name);
		
		addCollection(collection);
		
		return collection;
	}
	
	public void addLines(float[] x, float[] y, String name) {
		if (x.length - y.length == 0) {
			int length = x.length;
			for (int i = 0; i < length-1; i++) {
				addLine(new Vector2<Float>(x[i], y[i]), new Vector2<Float>(x[i+1], y[i+1]), name);			
			}
		}
	}

	public ShapeObject addText(String string, Vector2<Float> pos, Color color) {
		ShapeObject obj = new ShapeObject(string, pos, color);
		addShape(obj);
		return obj;
	}
}
