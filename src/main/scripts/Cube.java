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
	private float w, h, d;
	
	public Cube(float x, float y, float w, float h, float d) {
		this.getTransform().position().set(x, y);
		this.w = w;
		this.h = h;
		this.d = d;
		float z;
		x = this.getTransform().position().getX();
		y = this.getTransform().position().getY();
		z = 0;
		points = new Matrix[] {
				new Matrix(new float[][] {{x}, {y}, {z}}, 1, 3), //0
				new Matrix(new float[][] {{x+w}, {y}, {z}}, 1, 3), //1
				new Matrix(new float[][] {{x+w}, {y-h}, {z}}, 1, 3), //2
				new Matrix(new float[][] {{x}, {y-h}, {z}}, 1, 3), //3

				new Matrix(new float[][] {{x}, {y}, {z+d}}, 1, 3), //4
				new Matrix(new float[][] {{x+w}, {y}, {z+d}}, 1, 3), //5
				new Matrix(new float[][] {{x+w}, {y-h}, {z+d}}, 1, 3), //6
				new Matrix(new float[][] {{x}, {y-h}, {z+d}}, 1, 3), //7
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
		
		Matrix rotX = new Matrix(new float[][] 
				{
				{1, 0, 0},
				{0, (float)Math.cos(angle), (float)-Math.sin(angle)},
				{0, (float) Math.sin(angle), (float)Math.cos(angle)},
				}
		, 3, 3);
		
//		float rotateZ[4][4] = {
//				{cosf(timeValue), -sinf(timeValue), 0, 0},
//				{sinf(timeValue), cosf(timeValue), 0, 0},
//				{0, 0, 1, 0},
//				{0, 0, 0, 1}
//			};
		
		Matrix rotY = new Matrix(new float[][] 
				{
				{(float)Math.cos(angle), 0, (float)Math.sin(angle)},
				{0, 1, 0},
				{(float)-Math.sin(angle), 0, (float)Math.cos(angle)}
				}
		, 3, 3);
		
		Matrix tM = new Matrix(new float[][] 
				{
				{1, 0, 0},
				{0, 1, 0},
				{0, 0, 1},
				}
		, 3, 3);
		
		rotY = Matrix.mult(rotX, rotY);
		float z = 0;
		float x = this.getTransform().position().getX();
		float y = this.getTransform().position().getY();
		Matrix[] pts = new Matrix[] {
				new Matrix(new float[][] {{x}, {y}, {z}}, 1, 3), //0
				new Matrix(new float[][] {{x+w}, {y}, {z}}, 1, 3), //1
				new Matrix(new float[][] {{x+w}, {y-h}, {z}}, 1, 3), //2
				new Matrix(new float[][] {{x}, {y-h}, {z}}, 1, 3), //3

				new Matrix(new float[][] {{x}, {y}, {z+d}}, 1, 3), //4
				new Matrix(new float[][] {{x+w}, {y}, {z+d}}, 1, 3), //5
				new Matrix(new float[][] {{x+w}, {y-h}, {z+d}}, 1, 3), //6
				new Matrix(new float[][] {{x}, {y-h}, {z+d}}, 1, 3), //7
		};
		
		for (Matrix m : pts) {
//			Matrix result1 = Matrix.mult(rotX, m);
			Matrix result = Matrix.mult(rotY, m);
			
			float[][] values = m.getValues();
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[i].length; j++) {
					m.getValues()[i][j] = result.getValue(i, j);
				}
			}
//			System.out.println("M: " + m.toString());
		}
		pointsToVectors(pts);
		updateLines();
	};
	
	private void pointsToVectors(Matrix[] points) {
		//Convert points to vectors
		vPoints.clear();
		for (Matrix m : points) {
			
			float px, py, pz;
			px = m.getValue(0, 0) + this.getTransform().position().getX() + 300;
			py = m.getValue(1, 0) + this.getTransform().position().getY();
			pz = m.getValue(2, 0);
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
	}
}
