package main.scripts;

import java.awt.Color;
import java.awt.Shape;
import java.util.ArrayList;

import global.math.Matrix;
import global.math.Vector2;
import global.math.Vector3;
import graphics.Shapes;
import graphics.objects.GameObject;
import graphics.objects.ObjectCollection;
import graphics.objects.ShapeObject;

public class HyperCube extends GameObject{

	ArrayList<Vector3<Float>> vPoints = new ArrayList<>();
	private float W, H, D, x, y, z, w;
	
	public HyperCube(float x, float y,float z, float w, float W, float H, float D) {
		this.getTransform().position().set(x, y);
		this.W = W;
		this.H = H;
		this.D = D;
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		for (int i = 0; i < 24; i++) {
			vPoints.add(new Vector3<Float>(0f, 0f, 0f));			
		}
	}
	
	@Override
	public void init() {				
			updateLines();
	}
	
	public void rotate(float angle) {
		for (ShapeObject obj : buildingBlocks().getObjects()) {
			obj.rotate(angle, W/2, H/2);
		}
	}
	
	public void rotateX(double angle) {
		Matrix rotX = new Matrix(4, 4, new float[][] 
				{
				{1, 0, 0, 0},
				{0, (float)Math.cos(angle), (float)-Math.sin(angle), 0},
				{0, (float) Math.sin(angle), (float)Math.cos(angle), 0},
				{0, 0, 0, 1}
				}
		);
		
		Matrix rotZ = new Matrix(4, 4, new float[][]{
				{(float)Math.cos(angle), (float)-Math.sin(angle), 0, 0},
				{(float)Math.sin(angle), (float)Math.cos(angle), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
			});
		
		Matrix rotY = new Matrix(4, 4, new float[][] 
				{
				{(float)Math.cos(angle), 0, (float)Math.sin(angle), 0},
				{0, 1, 0, 0},
				{(float)-Math.sin(angle), 0, (float)Math.cos(angle), 0},
				{0, 0, 0, 1}
				}
		);
		
		Matrix rotW = new Matrix(4, 4, new float[][] 
				{
					{1, 0, 0, 0},
					{0, 1, 0, 0},
					{0, 0, (float)Math.cos(angle), (float)-Math.sin(angle)},
					{0, 0, (float)Math.sin(angle), (float)Math.cos(angle)}
				}
		);
		
		Matrix rotXW = new Matrix(4, 4, new float[][] 
				{
					{(float)Math.cos(angle), 0, 0, (float)-Math.sin(angle)},
					{0, 1, 0, 0},
					{0, 0, 1, 0},
					{(float)Math.sin(angle), 0, 0, (float)Math.cos(angle)}
				}
		);
		
		Matrix[] pts = new Matrix[] {
				new Matrix(4, 1, new float[][] {{x}, {y}, {z}, {w}}), //0
				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z}, {w}}), //1
				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z}, {w}}), //2
				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z}, {w}}), //3

				new Matrix(4, 1, new float[][] {{x}, {y}, {z+D}, {w}}), //4
				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z+D}, {w}}), //5
				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z+D}, {w}}), //6
				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z+D}, {w}}), //7
				//
				new Matrix(4, 1, new float[][] {{x}, {y}, {z}, {-w}}), //8
				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z}, {-w}}), //9
				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z}, {-w}}), //10
				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z}, {-w}}), //11

				new Matrix(4, 1, new float[][] {{x}, {y}, {z+D}, {-w}}), //12
				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z+D}, {-w}}), //13
				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z+D}, {-w}}), //14
				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z+D}, {-w}}), //15
		};
		
		for (Matrix m : pts) {
			Matrix result = Matrix.mult(rotW, m);
			result = Matrix.mult(rotZ, result);
			float alpha = (float) -Math.PI/180f*90;
			float beta = (float) -Math.PI/180f*110;
			result = Matrix.mult(new Matrix(4, 4, new float[][]{
				{1, 0, 0, 0},
				{0, (float)Math.cos(beta), (float)-Math.sin(beta), 0},
				{0, (float) Math.sin(beta), (float)Math.cos(beta), 0},
				{0, 0, 0, 1}
			}), result);
//			result = Matrix.mult(new Matrix(4, 4, new float[][] 
//					{
//					{(float)Math.cos(alpha), 0, (float)Math.sin(alpha), 0},
//					{0, 1, 0, 0},
//					{(float)-Math.sin(alpha), 0, (float)Math.cos(alpha), 0},
//					{0, 0, 0, 1}
//					}
//			), result);
//			result = Matrix.mult(rotY, result);
//			result = Matrix.mult(new Matrix(4, 4, new float[][] 
//					{
//					{1, 0, 0, 0},
//					{0, (float)Math.cos(alpha), (float)-Math.sin(alpha), 0},
//					{0, (float) Math.sin(alpha), (float)Math.cos(alpha), 0},
//					{0, 0, 0, 1}
//					}
//			), result);
			float distance = 2f;
			float distance2 = 2f;

			float a = 1 / (distance-result.getValues()[2][0]); //z
			float b = 1 / (distance2-result.getValues()[3][0]); //w
			
			Matrix proj2d = new Matrix(3, 3, new float[][] 
				{
				{a, 0, 0},
				{0, a, 0},
				{0, 0, 1}
					});
			
			Matrix proj3d = new Matrix(3, 4, new float[][] 
					{
					{b, 0, 0, 0},
					{0, b, 0, 0},
					{0, 0, b, 0},
						});
//			System.out.println(result.toString());
			Matrix projected = Matrix.mult(proj3d, result);
			projected = Matrix.mult(proj2d, projected);
			projected.mult(300);

			m.setValues(3, 1, projected.getValues());
		}
		pointsToVectors(pts);
		updateLines();
	};
	
	private void pointsToVectors(Matrix[] points) {
		//Convert points to vectors
//		vPoints.clear();
		for (int i = 0; i < points.length; i++) {
			Matrix m = points[i];
			float px, py, pz;
			px = m.getValues()[0][0] + 500;
			py = m.getValues()[1][0] + 350;
			pz = m.getValues()[2][0];
			vPoints.set(i, new Vector3<Float>(px, py, pz));
		}
	}
	
	private void updateLines() {
		buildingBlocks().getObjects().clear();
		
		addShape(Shapes.Line(vPoints.get(0), vPoints.get(1)), vPoints.get(0).getZ(), Color.red, false);
		addShape(Shapes.Line(vPoints.get(1), vPoints.get(2)), vPoints.get(1).getZ(), Color.red, false);
		addShape(Shapes.Line(vPoints.get(2), vPoints.get(3)), vPoints.get(2).getZ(), Color.red, false);
		addShape(Shapes.Line(vPoints.get(3), vPoints.get(0)), vPoints.get(3).getZ(), Color.red, false);
		
		addShape(Shapes.Line(vPoints.get(0+4), vPoints.get(1+4)), vPoints.get(0+4).getZ(), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(1+4), vPoints.get(2+4)), vPoints.get(1+4).getZ(), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(2+4), vPoints.get(3+4)), vPoints.get(2+4).getZ(), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(3+4), vPoints.get(0+4)), vPoints.get(3+4).getZ(), Color.blue, false);
		
		addShape(Shapes.Line(vPoints.get(0), vPoints.get(0+4)), vPoints.get(0).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(3), vPoints.get(3+4)), vPoints.get(3).getZ(), Color.green, false);
		
		addShape(Shapes.Line(vPoints.get(1), vPoints.get(1+4)), vPoints.get(1).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(2), vPoints.get(2+4)), vPoints.get(2).getZ(), Color.green, false);
		////////////////////////////////////////////////////
		addShape(Shapes.Line(vPoints.get(0+8), vPoints.get(1+8)), vPoints.get(0+8).getZ(), Color.red, false);
		addShape(Shapes.Line(vPoints.get(1+8), vPoints.get(2+8)), vPoints.get(1+8).getZ(), Color.red, false);
		addShape(Shapes.Line(vPoints.get(2+8), vPoints.get(3+8)), vPoints.get(2+8).getZ(), Color.red, false);
		addShape(Shapes.Line(vPoints.get(3+8), vPoints.get(0+8)), vPoints.get(3+8).getZ(), Color.red, false);
		
  		addShape(Shapes.Line(vPoints.get(0+4+8), vPoints.get(1+4+8)), vPoints.get(0+4+8).getZ(), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(1+4+8), vPoints.get(2+4+8)), vPoints.get(1+4+8).getZ(), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(2+4+8), vPoints.get(3+4+8)), vPoints.get(2+4+8).getZ(), Color.blue, false);
		addShape(Shapes.Line(vPoints.get(3+4+8), vPoints.get(0+4+8)), vPoints.get(3+4+8).getZ(), Color.blue, false);
		
		addShape(Shapes.Line(vPoints.get(0+8), vPoints.get(0+4+8)), vPoints.get(0+8).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(3+8), vPoints.get(3+4+8)), vPoints.get(3+8).getZ(), Color.green, false);
		
		addShape(Shapes.Line(vPoints.get(1+8), vPoints.get(1+4+8)), vPoints.get(1+8).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(2+8), vPoints.get(2+4+8)), vPoints.get(2+8).getZ(), Color.green, false);
		
		addShape(Shapes.Line(vPoints.get(0), vPoints.get(8)), vPoints.get(0).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(1), vPoints.get(9)), vPoints.get(1).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(2), vPoints.get(10)), vPoints.get(2).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(3), vPoints.get(11)), vPoints.get(3).getZ(), Color.green, false);

		addShape(Shapes.Line(vPoints.get(0+4), vPoints.get(8+4)), vPoints.get(0+4).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(1+4), vPoints.get(9+4)), vPoints.get(1+4).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(2+4), vPoints.get(10+4)), vPoints.get(2+4).getZ(), Color.green, false);
		addShape(Shapes.Line(vPoints.get(3+4), vPoints.get(11+4)), vPoints.get(3+4).getZ(), Color.green, false);
				
		addShape(Shapes.Arc(new Vector2<Float>(x+500-5, y+350-5), 10, 10), Color.blue, true);
		
		for (Vector3<Float> values : vPoints) {
			addShape(Shapes.Arc(new Vector2<Float>(values.getX()-5, values.getY()-5), 10, 10), Color.blue, true);
		}
	}
}
