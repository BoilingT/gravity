package main.scripts;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import global.math.Vector3;
import graphics.Shapes;
import graphics.objects.GameObject;
import graphics.objects.ShapeObject;

public class Cube extends GameObject{

	private Matrix[] pts;
	ArrayList<Vector2<Float>> vPoints = new ArrayList<>();
//	private float w, h, d, x, y, z;
	
	public Cube(float x, float y,float z, float w, float h, float d) {
		this.getTransform().position().set(x, y);
//		this.w = w;
//		this.h = h;
//		this.d = d;
//		this.x = x;
//		this.y = y;
//		this.z = z;
		pts = new Matrix[] {
				new Matrix(3, 1, new float[][] {{x}, {y}, {z}}), //0
				new Matrix(3, 1, new float[][] {{x+w}, {y}, {z}}), //1
				new Matrix(3, 1, new float[][] {{x+w}, {y-h}, {z}}), //2
				new Matrix(3, 1, new float[][] {{x}, {y-h}, {z}}), //3

				new Matrix(3, 1, new float[][] {{x}, {y}, {z+d}}), //4
				new Matrix(3, 1, new float[][] {{x+w}, {y}, {z+d}}), //5
				new Matrix(3, 1, new float[][] {{x+w}, {y-h}, {z+d}}), //6
				new Matrix(3, 1, new float[][] {{x}, {y-h}, {z+d}}), //7
		};
		
		for (int i = 0; i < 24; i++) {
			vPoints.add(new Vector3<Float>(0f, 0f, 0f));			
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
		
		Matrix rotX = new Matrix(3, 3, new float[][] 
				{
				{1, 0, 0},
				{0, (float)Math.cos(angle), (float)-Math.sin(angle)},
				{0, (float) Math.sin(angle), (float)Math.cos(angle)},
				}
		);
		
		Matrix rotZ = new Matrix(3, 3, new float[][]{
				{(float)Math.cos(angle), (float)-Math.sin(angle), 0},
				{(float)Math.sin(angle), (float)Math.cos(angle), 0},
				{0, 0, 1},
			});
		
		Matrix rotY = new Matrix(3, 3, new float[][] 
				{
				{(float)Math.cos(angle), 0, (float)Math.sin(angle)},
				{0, 1, 0},
				{(float)-Math.sin(angle), 0, (float)Math.cos(angle)}
				}
		);
				
		Matrix[] projectedPoints = new Matrix[pts.length];
		for (int i = 0; i < pts.length; i++) {
			Matrix m = pts[i];
			Matrix result = Matrix.mult(rotY, m);
//			result = Matrix.mult(rotZ, result);
			pts[i].setValues(3,1, result.getValues());
			float distance = 3f;
			float a = 1;

			if(distance-result.getValues()[2][0] > 0) {
				a = distance / (distance-result.getValues()[2][0]);				
				Matrix proj2d = new Matrix(2, 3, new float[][] 
						{
					{a, 0, 0},
					{0, a, 0},
						});
				projectedPoints[i] = Matrix.mult(proj2d, result);
			}else if(distance-result.getValues()[2][0] != 0){
//				result.getValues()[2][0] = ;
				a = distance / (distance-result.getValues()[2][0]);				
				System.out.println("noll: " + result.getValues()[2][0]);
				Matrix proj2d = new Matrix(2, 3, new float[][] 
						{
					{a, 0, 0},
					{0, a, 0},
						});
//				float tempAngle = (float) Math.PI/180*180;
//				result = Matrix.mult(new Matrix(3, 3, new float[][] 
//						{
//					{(float)Math.cos(tempAngle), 0, (float)Math.sin(tempAngle)},
//					{0, 1, 0},
//					{(float)-Math.sin(tempAngle), 0, (float)Math.cos(tempAngle)}
//					}), result);
//				result.mult(10);
				projectedPoints[i] = Matrix.mult(proj2d, result).mult(distance*100*a);
			}
			projectedPoints[i] = projectedPoints[i].mult(100);
			System.out.println("Result: \n" + result.toString() + " a: " + a + "\nprojected: \n" + projectedPoints[i].toString());

		}
		pointsToVectors(projectedPoints);
		updateLines();
	};
	
	private void pointsToVectors(Matrix[] points) {
		//Convert points to vectors
		int n = 0;
		for (Matrix m : points) {
			
			float px, py, pz;
			px = m.getValues()[0][0] + 500;
			py = m.getValues()[1][0] + 350;
			setPoint(n++, new Vector2<Float>(px, py));
		}
	}
	
	private void setPoint(int index, Vector2<Float> v) {
		if(vPoints.size() <= 0) vPoints.add(v);
		if(vPoints.size()-1 >= index) {
			vPoints.set(index, v);			
		}else {
			vPoints.add(index, v);			
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
//		addShape(Shapes.Arc(new Vector2<Float>(x+500-2, y+350-2), 4, 4), Color.blue, true);
		
		int n = 12;
		for (Vector2<Float> values : vPoints) {
			setShapeObject(n++, Shapes.Arc(new Vector2<Float>(values.getX()-5, values.getY()-5), 10, 10), Color.white, true);
		}
	}
}
