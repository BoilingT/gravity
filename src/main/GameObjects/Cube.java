package main.GameObjects;

import java.awt.Color;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import global.math.Vector3;
import graphics.Canvas;
import graphics.Shapes;
import graphics.objects.GameObject;

public class Cube extends GameObject{

	public Matrix[] pts;
	ArrayList<Vector2<Float>> vPoints = new ArrayList<>();
	private Vector3<Float> origin = new Vector3<Float>(-0.5f, 0.5f, -0.5f);
	public float distance = 5f;
	public float scale = 100f;
	

	public Cube(float x, float y,float z, float w, float h, float d) {
		this.getTransform().position().set(x, y, z);
//		this.w = w;
//		this.h = h;
//		this.d = d;
//		this.x = x;
//		this.y = y;
//		this.z = z;
		pts = new Matrix[] {
				new Matrix(3, 1, new float[][] {{origin.getX()}, {origin.getY()}, {origin.getZ()}}), //0
				new Matrix(3, 1, new float[][] {{origin.getX()+w}, {origin.getY()}, {origin.getZ()}}), //1
				new Matrix(3, 1, new float[][] {{origin.getX()+w}, {origin.getY()-h}, {origin.getZ()}}), //2
				new Matrix(3, 1, new float[][] {{origin.getX()}, {origin.getY()-h}, {origin.getZ()}}), //3

				new Matrix(3, 1, new float[][] {{origin.getX()}, {origin.getY()}, {origin.getZ()+d}}), //4
				new Matrix(3, 1, new float[][] {{origin.getX()+w}, {origin.getY()}, {origin.getZ()+d}}), //5
				new Matrix(3, 1, new float[][] {{origin.getX()+w}, {origin.getY()-h}, {origin.getZ()+d}}), //6
				new Matrix(3, 1, new float[][] {{origin.getX()}, {origin.getY()-h}, {origin.getZ()+d}}), //7
		};
		
		for (int i = 0; i < 24; i++) {
			vPoints.add(new Vector2<Float>(0f, 0f));			
		}
	}
	
	@Override
	public void init() {
		updateLines();
		rotateUpdate(0);
	}
	
	public void rotate(float angle) {
//		for (ShapeObject obj : buildingBlocks().getObjects()) {
//			obj.rotate(angle, w/2, h/2);
//		}
	}
	
	public void rotateUpdate(double angle) {
		Vector3<Float> rot = getTransform().rotation();
		Matrix rotX = new Matrix(3, 3, new float[][] 
				{
				{1, 0, 0},
				{0, (float)Math.cos(rot.getX()), (float)-Math.sin(rot.getX())},
				{0, (float) Math.sin(rot.getX()), (float)Math.cos(rot.getX())},
				}
		);
		
		Matrix rotZ = new Matrix(3, 3, new float[][]{
				{(float)Math.cos(rot.getZ()), (float)-Math.sin(rot.getZ()), 0},
				{(float)Math.sin(rot.getZ()), (float)Math.cos(rot.getZ()), 0},
				{0, 0, 1},
			});
		
		Matrix rotY = new Matrix(3, 3, new float[][] 
				{
				{(float)Math.cos(rot.getY()), 0, (float)Math.sin(rot.getY())},
				{0, 1, 0},
				{(float)-Math.sin(rot.getY()), 0, (float)Math.cos(rot.getY())}
				}
		);
				
		Matrix[] projectedPoints = new Matrix[pts.length];
		for (int i = 0; i < pts.length; i++) {
			Matrix m = pts[i];
			Matrix result = Matrix.mult(rotY, m);
			result = Matrix.mult(rotZ, result);
			result = Matrix.mult(rotX, result);
			pts[i].setValues(3,1, result.getValues());
//			float distance = 3f;
//			float a = 1;

//			if(distance-result.getValues()[2][0] > 0) {
//				a = distance / (distance-result.getValues()[2][0]);				
//				Matrix proj2d = new Matrix(2, 3, new float[][] 
//						{
//					{a, 0, 0},
//					{0, a, 0},
//						});
//				projectedPoints[i] = Matrix.mult(proj2d, result);
//			}else if(distance-result.getValues()[2][0] != 0){
//				a = distance / (distance-result.getValues()[2][0]);				
//				System.out.println("noll: " + result.getValues()[2][0]);
//				Matrix proj2d = new Matrix(2, 3, new float[][] 
//						{
//					{a, 0, 0},
//					{0, a, 0},
//						});
//
//				projectedPoints[i] = Matrix.mult(proj2d, result).mult(distance*100*a);
//			}
			
			//Project
			
			float a = distance / (distance-result.getValues()[2][0]); //z
//			a = 1 / (distance-result.getValues()[2][0]); //z
//			a=1;
			Matrix proj2d = new Matrix(3, 3, new float[][] 
				{
				{a, 0, 0},
				{0, a, 0},
				{0, 0, 1},
					});
			projectedPoints[i] = Matrix.mult(proj2d, result);
//			System.out.println("Result: \n" + result.toString() + " a: " + a + "\nprojected: \n" + projectedPoints[i].toString());

		}
		pointsToVectors(projectedPoints);
		updateLines();
	};
	
	private void pointsToVectors(Matrix[] points) {
		//Convert points to vectors
		int n = 0;
		final Canvas canvas = Canvas.getInstance();
		final Vector2<Integer> screenOrigin = new Vector2<Integer>(canvas.getWidth()/2, canvas.getHeight()/2);
		for (int i = 0; i < points.length; i++) {
			Vector3<Float> pos = this.getTransform().position();
			Matrix m = points[i].mult(pos.getZ()*500 + 100);
//			Matrix m = points[i].mult(100);
			float px, py;
			px = m.getValues()[0][0] + pos.getX() * canvas.getWidth(); //x
			py = m.getValues()[1][0] + pos.getY() * canvas.getHeight(); //y
			
			vPoints.get(i).set(px, py);
		}
	}
	
	private void updateLines() {
		setShapeObject(0, Shapes.Line(vPoints.get(0), vPoints.get(1)), Color.red, false);
		setShapeObject(1, Shapes.Line(vPoints.get(1), vPoints.get(2)), Color.red, false);
		setShapeObject(2, Shapes.Line(vPoints.get(2), vPoints.get(3)), Color.red, false);
		setShapeObject(3, Shapes.Line(vPoints.get(3), vPoints.get(0)), Color.red, false);
		
		setShapeObject(4, Shapes.Line(vPoints.get(0+4), vPoints.get(1+4)), Color.blue, false);
		setShapeObject(5, Shapes.Line(vPoints.get(1+4), vPoints.get(2+4)), Color.blue, false);
		setShapeObject(6, Shapes.Line(vPoints.get(2+4), vPoints.get(3+4)), Color.blue, false);
		setShapeObject(7, Shapes.Line(vPoints.get(3+4), vPoints.get(0+4)), Color.blue, false);
		
		setShapeObject(8, Shapes.Line(vPoints.get(0), vPoints.get(0+4)), Color.green, false);
		setShapeObject(9, Shapes.Line(vPoints.get(3), vPoints.get(3+4)), Color.green, false);
		
		setShapeObject(10, Shapes.Line(vPoints.get(1), vPoints.get(1+4)), Color.green, false);
		setShapeObject(11, Shapes.Line(vPoints.get(2), vPoints.get(2+4)), Color.green, false);
		
		int n = 12;
		for (Vector2<Float> values : vPoints) {
			setShapeObject(n++, Shapes.Arc(new Vector2<Float>(values.getX()-5, values.getY()-5), 10, 10), Color.white, true);
		}
	}
}
