package graphics.objects;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import global.math.Vector2;
import global.math.Vector3;

public class ShapeObject {

	private String name;
	private Transform transform = new Transform();
	private Color color;
	private boolean filled;
	private String text;
	private boolean isText;
	
	private Shape shape;
	private AffineTransform affineTransform;
	
	public boolean isText() {
		return isText;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String str) {
		this.text = str;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public final Vector3<Float> getPos() {
		return transform.position();
	}
	
	public void setPos(Vector2<Float> pos) {
		float x = pos.getX() - this.transform.position().getX();
		float y = pos.getY() - this.transform.position().getY();
		System.out.println("Translate {x: " + x + ", y: " + y + "}");
		affineTransform.setToTranslation(x, y);
		this.shape = affineTransform.createTransformedShape(this.shape);
		
		this.transform.position().set(new Vector3<Float>(this.transform.position().getX() + x, this.transform.position().getY() + y, 0f));
	}
	
	public void translate(Vector2<Float> translation) {
		affineTransform.setToTranslation(translation.getX(), translation.getY());
		this.shape = affineTransform.createTransformedShape(this.shape); //Create the new translated shape
		this.transform.position().set(new Vector3<Float>(this.transform.position().getX() + translation.getX(), this.transform.position().getY() + translation.getY(), 0f));
	}
	
	public void rotate(float degrees, float offsetx, float offsety) {
		affineTransform.setToRotation(degrees * Math.PI/180, this.transform.position().getX() + offsetx, this.transform.position().getY() + offsety);
		//affineTransform.setToRotation(degrees * Math.PI/180);
		this.shape = affineTransform.createTransformedShape(this.shape); //Create the new rotated shape
		
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public void setFilled(boolean fill) {
		filled = fill;
	}

	public ShapeObject() {
		this.setPos(new Vector2<Float>(0f, 0f));
		this.shape = null;
		filled = false;
	}
	
	public ShapeObject(Shape shape, Color color, boolean fill) {
		set(shape, color, fill);
	}
	
	public ShapeObject(String text, Vector2<Float> pos, Color color) {
		this.isText = true;
		this.text = text;
		this.transform.position().set(pos.toVec3());
		this.color = color;
	}

	public void set(Shape shape, Color color, boolean fill) {
		affineTransform = new AffineTransform();
		Shape tempShape = affineTransform.createTransformedShape(shape);
		this.shape = tempShape;
		this.transform.position().set(new Vector3<Float>((float) tempShape.getBounds().x, (float) tempShape.getBounds().y, 0f));
		this.setColor(color);
		this.filled = fill;
	}
	
	public void update() {
	}	
}
