package graphics.objects;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import global.math.Vector2;
import global.math.Vector3;
import graphics.Canvas;

public abstract class GameObject{
	private String name = "";
	private ObjectCollection buildingBlocks = new ObjectCollection(name);
	private Transform prevTransform = new Transform();
	private Transform transform = new Transform();
	
	private Vector2<Float> gravity_force = new Vector2<Float>(0f, -9.802f);
	private Vector2<Float> force = new Vector2<Float>(0f, 0f);
	private Vector2<Float> velocity = new Vector2<Float>(0f, 0f);
	private Vector2<Float> acceleration = new Vector2<Float>(0f, 0f);
	private float mass = 0;
	private boolean gravity = true;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public ObjectCollection buildingBlocks() {
		return this.buildingBlocks;
	}
	
	public Vector2<Float> getVel(){
		return this.velocity;
	}
	
	public Vector2<Float> getAcc(){
		return this.acceleration;
	}
	
	public Vector2<Float> getForce(){
		return this.force;
	}
	
	public void addForce(float x, float y) {
		force.set(Vector2.add(force, new Vector2<Float>(x, y)));
	}
	
	public void applyForce(float x, float y) {
		if(mass > 0) {
			System.out.println("Force: [x: " + x + ", y:" + y + "]");
			Vector2<Float> acc = new Vector2<Float>(0f, 0f);
			acc.set(x/mass, (-y/mass));		
			System.out.println("Acc: " + acc.toString());
			Vector2<Float> newVel = Vector2.add(velocity, acc);
			System.out.println("newVel: " + acc.toString());
			velocity.set(Vector2.add(velocity, newVel));
		}
	}
	
	public void subForce(float x, float y) {
		force.set(Vector2.subtract(force, new Vector2<Float>(x, y)));
	}
	
	public void setForce(float x, float y) {
		force.set(x, y);
	}
	
	public float getMass(){
		return this.mass;
	}
	
	public void setMass(float mass){
		this.mass = mass;
	}
	
	public boolean hasGravity() {
		return gravity;
	}
	
	public void hasGravity(boolean g) {
		this.gravity = g;
	}
	
	public void addShape(Shape shape, Color color, boolean fill) {
		ShapeObject obj = new ShapeObject(shape, color, fill);
		buildingBlocks.add(obj);			
	}
	
	public void addShape(Shape shape, float z, Color color, boolean fill) {
		ShapeObject obj = new ShapeObject(shape, color, fill);
				
		Vector3<Float> pos = obj.getPos();
		obj.getPos().set(pos.getX(), pos.getY(), z);
		ArrayList<ShapeObject> values = buildingBlocks().getObjects();
		
		if(values.size() <= 0) {
			values.add(obj);
			return;
		}
		if(z >= values.get(values.size()-1).getPos().getZ()) {
			values.add(obj);			
		}else if(z <= values.get(0).getPos().getZ()) {
			values.add(0, obj);
		}else {
			values.add(obj);
		}
	}
	
	public void setShapeObject(int index, Shape shape, float z, Color color, boolean fill) {
		ShapeObject shapeObject = new ShapeObject(shape, color, fill);
		if(buildingBlocks().getObjects().size() <= 0) buildingBlocks().getObjects().add(shapeObject);
		if(buildingBlocks().getObjects().size()-1 >= index) {
			buildingBlocks().getObjects().set(index, shapeObject);			
		}else {
			buildingBlocks().getObjects().add(index, shapeObject);			
		}
	}
	
	public void setShapeObject(int index, Shape shape, Color color, boolean fill) {
		ShapeObject shapeObject = new ShapeObject(shape, color, fill);
		if(buildingBlocks().getObjects().size() <= 0) buildingBlocks().getObjects().add(shapeObject);
		if(buildingBlocks().getObjects().size()-1 >= index) {
			buildingBlocks().getObjects().set(index, shapeObject);			
		}else {
			buildingBlocks().getObjects().add(index, shapeObject);			
		}
	}
	
	public abstract void init();
	
	public void update() {
		//F = m * a => a = F / m
		if(mass > 0) {
			if(gravity) {
				acceleration.set(force.getX()/mass, (-force.getY()/mass  - gravity_force.getY()));				
			}else {
				acceleration.set(force.getX()/mass, (-force.getY()/mass));				
			}
			velocity.set(Vector2.add(velocity, acceleration));
		}
		Vector2<Float> newPos = Vector2.add(transform.position().toVec2(), Vector2.scale(velocity, (float) Canvas.getInstance().deltaTime()));
		Vector2<Float> translation = Vector2.subtract(newPos, prevTransform.position().toVec2());
		for (ShapeObject obj : buildingBlocks.getObjects()) {
			obj.translate(translation);
			obj.update();
		}
		transform.position().set(newPos.toVec3());
		prevTransform.position().set(transform.position());
	}
}
