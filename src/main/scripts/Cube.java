package main.scripts;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import graphics.Shapes;
import graphics.objects.GameObject;
import graphics.objects.ShapeObject;

public class Cube extends GameObject{

	private Matrix[] points;
	ArrayList<Vector2<Float>> vPoints = new ArrayList<>();
	private float w, h, d, x, y, z;
	
	public Cube(float x, float y,float z, float w, float h, float d) {
		this.getTransform().position().set(x, y);
		this.w = w;
		this.h = h;
		this.d = d;
		this.x = this.getTransform().position().getX();
		this.y = this.getTransform().position().getY();
		this.z = z;
		points = new Matrix[] {
				new Matrix(3, 1, new float[][] {{x}, {y}, {z}}), //0
				new Matrix(3, 1, new float[][] {{x+w}, {y}, {z}}), //1
				new Matrix(3, 1, new float[][] {{x+w}, {y-h}, {z}}), //2
				new Matrix(3, 1, new float[][] {{x}, {y-h}, {z}}), //3

				new Matrix(3, 1, new float[][] {{x}, {y}, {z+d}}), //4
				new Matrix(3, 1, new float[][] {{x+w}, {y}, {z+d}}), //5
				new Matrix(3, 1, new float[][] {{x+w}, {y-h}, {z+d}}), //6
				new Matrix(3, 1, new float[][] {{x}, {y-h}, {z+d}}), //7
		};
	}
	
	@Override
	public void init() {
		
		
		
			ArrayList<float[][]> sidePoints = new ArrayList<>();
			pointsToVectors(points);

						
			updateLines();
	}
	
	public void rotate(float angle) {
		for (ShapeObject obj : buildingBlocks().getObjects()) {
			obj.rotate(angle, w/2, h/2);
		}
	}
	
	public void rotateX(double angle) {
		
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
		
		
		x = this.getTransform().position().getX();
		y = this.getTransform().position().getY();
		
		Matrix[] pts = new Matrix[] {
				new Matrix(3, 1, new float[][] {{x}, {y}, {z}}), //0
				new Matrix(3, 1, new float[][] {{x+w}, {y}, {z}}), //1
				new Matrix(3, 1, new float[][] {{x+w}, {y-h}, {z}}), //2
				new Matrix(3, 1, new float[][] {{x}, {y-h}, {z}}), //3

				new Matrix(3, 1, new float[][] {{x}, {y}, {z+d}}), //4
				new Matrix(3, 1, new float[][] {{x+w}, {y}, {z+d}}), //5
				new Matrix(3, 1, new float[][] {{x+w}, {y-h}, {z+d}}), //6
				new Matrix(3, 1, new float[][] {{x}, {y-h}, {z+d}}), //7
		};
		
		for (Matrix m : pts) {
			Matrix result = Matrix.mult(rotX, m);
			result = Matrix.mult(rotY, result);
			result = Matrix.mult(rotZ, result);
			float distance = 2f;
			float a = 1 / (distance-result.getValues()[2][0]);
			float b = 1 / (distance-result.getValues()[1][0]);
			float c = 1 / (distance-result.getValues()[2][0]);
			
			Matrix proj = new Matrix(2, 3, new float[][] 
					{
				{a, 0, 0},
				{0, a, 0},
					}
					);
//			System.out.println(result.toString());
			Matrix projected = Matrix.mult(proj, result);
			projected.mult(500);

			m.setValues(2, 1, projected.getValues());
		}
		pointsToVectors(pts);
		updateLines();
	};
	
	private void pointsToVectors(Matrix[] points) {
		//Convert points to vectors
		vPoints.clear();
		
		for (Matrix m : points) {
			
			float px, py, pz;
			px = m.getValues()[0][0] + 500;
			py = m.getValues()[1][0] + 350;
//			pz = m.getValues()[2][0];
			vPoints.add(new Vector2<Float>(px, py));
		}
	}
	
	private void updateLines() {
		buildingBlocks().getObjects().clear();
		addShape(Shapes.Line(vPoints.get(0), vPoints.get(1)), Color.red, false);
		addShape(Shapes.Line(vPoints.get(1), vPoints.get(2)), Color.red, false);
		addShape(Shapes.Line(vPoints.get(2), vPoints.get(3)), Color.red, false);
		addShape(Shapes.Line(vPoints.get(3), vPoints.get(0)), Color.red, false);
		
		addShape(Shapes.Line(vPoints.get(0+4), vPoints.get(1+4)), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(1+4), vPoints.get(2+4)), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(2+4), vPoints.get(3+4)), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(3+4), vPoints.get(0+4)), Color.blue, false);
		
		addShape(Shapes.Line(vPoints.get(0), vPoints.get(0+4)), Color.green, false);
		addShape(Shapes.Line(vPoints.get(3), vPoints.get(3+4)), Color.green, false);
		
		addShape(Shapes.Line(vPoints.get(1), vPoints.get(1+4)), Color.green, false);
		addShape(Shapes.Line(vPoints.get(2), vPoints.get(2+4)), Color.green, false);
//		addShape(Shapes.Arc(new Vector2<Float>(x+500-5, y+350-5), 10, 10), Color.blue, true);
	}
}
