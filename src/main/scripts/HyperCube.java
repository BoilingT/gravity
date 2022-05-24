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
	private Matrix[] pts;
//	private final float W, H, D, x, y, z, w;

		
	public HyperCube(float x, float y,float z, float w, float W, float H, float D) {
		this.getTransform().position().set(x, y);
//		this.W = W;
//		this.H = H;
//		this.D = D;
//		this.x = x;
//		this.y = y;
//		this.z = z;
//		this.w = w;
			
		pts = new Matrix[] {
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
		
		for (int i = 0; i < 24; i++) {
			vPoints.add(new Vector3<Float>(0f, 0f, 0f));			
		}
	}
	
	@Override
	public void init() {		
		updateLines();
		rotateX(0);
	}
	
	public void rotate(float angle) {
		for (ShapeObject obj : buildingBlocks().getObjects()) {
//			obj.rotate(angle, W/2, H/2);
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
		angle*=2;
		
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
		
//		pts = new Matrix[] {
//				new Matrix(4, 1, new float[][] {{x}, {y}, {z}, {w}}), //0
//				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z}, {w}}), //1
//				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z}, {w}}), //2
//				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z}, {w}}), //3
//
//				new Matrix(4, 1, new float[][] {{x}, {y}, {z+D}, {w}}), //4
//				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z+D}, {w}}), //5
//				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z+D}, {w}}), //6
//				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z+D}, {w}}), //7
//				//
//				new Matrix(4, 1, new float[][] {{x}, {y}, {z}, {-w}}), //8
//				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z}, {-w}}), //9
//				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z}, {-w}}), //10
//				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z}, {-w}}), //11
//
//				new Matrix(4, 1, new float[][] {{x}, {y}, {z+D}, {-w}}), //12
//				new Matrix(4, 1, new float[][] {{x+W}, {y}, {z+D}, {-w}}), //13
//				new Matrix(4, 1, new float[][] {{x+W}, {y-H}, {z+D}, {-w}}), //14
//				new Matrix(4, 1, new float[][] {{x}, {y-H}, {z+D}, {-w}}), //15
//		};
		
		System.out.println("BEFORE pointsResult 1:\n" + pts[0].toString());
		Matrix[] projectedPoints = new Matrix[pts.length];
		for (int i = 0; i < pts.length; i++) {
			Matrix m = pts[i];
			Matrix result = Matrix.mult(rotW, m);

			if(result == null) {
				System.out.println("result is null");
				return;
			}
			result = Matrix.mult(rotZ, result);
//			result = Matrix.mult(rotY, result);
			float alpha = (float) -Math.PI/180f*90;
			
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

			pts[i].setValues(4, 1, result.getValues());
			
			float beta = (float) Math.PI/180f*60;
			result = Matrix.mult(new Matrix(4, 4, new float[][]{
				{1, 0, 0, 0},
				{0, (float)Math.cos(beta), (float)-Math.sin(beta), 0},
				{0, (float) Math.sin(beta), (float)Math.cos(beta), 0},
				{0, 0, 0, 1}
			}), result);
			
			float distance = 3f;

			float a = distance / (distance-result.getValues()[2][0]); //z
			float b = distance / (distance-result.getValues()[3][0]); //w
//			a = 1 / (distance-result.getValues()[2][0]); //z
//			a=1;
			Matrix proj2d = new Matrix(4, 4, new float[][] 
				{
				{a, 0, 0, 0},
				{0, a, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
					});
			
			Matrix proj3d = new Matrix(4, 4, new float[][] 
				{
				{b, 0, 0, 0},
				{0, b, 0, 0},
				{0, 0, b, 0},
				{0, 0, 0, 1},
					});
			projectedPoints[i] = Matrix.mult(proj3d, result); //4d to 3d
			if(projectedPoints[i] == null) {
				System.out.println("projected is null");
				return;
			}
			
			projectedPoints[i] = Matrix.mult(proj2d, projectedPoints[i]); //3d to 2d
			if(projectedPoints[i] == null) {
				System.out.println("projected2d is null");
				return;
			}
		}
		System.out.println("AFTER: pointsResult 1:\n" + pts[0].toString());
		pointsToVectors(projectedPoints);
		updateLines();
	};
	
	private void pointsToVectors(Matrix[] points) {
		//Convert points to vectors
//		vPoints.clear();
		for (int i = 0; i < points.length; i++) {
			Matrix m = points[i].mult(300);
			float px, py, pz;
			px = m.getValues()[0][0] + 500; //x
			py = m.getValues()[1][0] + 350; //y
			pz = m.getValues()[2][0]; 		//z
			vPoints.set(i, new Vector3<Float>(px, py, pz));
		}
	}
	
	private void updateLines() {
//		buildingBlocks().getObjects().clear();
		
		setShapeObject(0, Shapes.Line(vPoints.get(0), vPoints.get(1)), vPoints.get(0).getZ(), Color.white, false);
		setShapeObject(1, Shapes.Line(vPoints.get(1), vPoints.get(2)), vPoints.get(1).getZ(), Color.white, false);
		setShapeObject(2, Shapes.Line(vPoints.get(2), vPoints.get(3)), vPoints.get(2).getZ(), Color.white, false);
		setShapeObject(3, Shapes.Line(vPoints.get(3), vPoints.get(0)), vPoints.get(3).getZ(), Color.white, false);
		
		setShapeObject(4, Shapes.Line(vPoints.get(0+4), vPoints.get(1+4)), vPoints.get(0+4).getZ(), Color.white, false);
		setShapeObject(5, Shapes.Line(vPoints.get(1+4), vPoints.get(2+4)), vPoints.get(1+4).getZ(), Color.white, false);
		setShapeObject(6, Shapes.Line(vPoints.get(2+4), vPoints.get(3+4)), vPoints.get(2+4).getZ(), Color.white, false);
		setShapeObject(7, Shapes.Line(vPoints.get(3+4), vPoints.get(0+4)), vPoints.get(3+4).getZ(), Color.white, false);
		
		setShapeObject(8, Shapes.Line(vPoints.get(0), vPoints.get(0+4)), vPoints.get(0).getZ(), Color.white, false);
		setShapeObject(9, Shapes.Line(vPoints.get(3), vPoints.get(3+4)), vPoints.get(3).getZ(), Color.white, false);
		
		setShapeObject(10, Shapes.Line(vPoints.get(1), vPoints.get(1+4)), vPoints.get(1).getZ(), Color.white, false);
		setShapeObject(11, Shapes.Line(vPoints.get(2), vPoints.get(2+4)), vPoints.get(2).getZ(), Color.white, false);
		////////////////////////////////////////////////////
		setShapeObject(12, Shapes.Line(vPoints.get(0+8), vPoints.get(1+8)), vPoints.get(0+8).getZ(), Color.white, false);
		setShapeObject(13, Shapes.Line(vPoints.get(1+8), vPoints.get(2+8)), vPoints.get(1+8).getZ(), Color.white, false);
		setShapeObject(14, Shapes.Line(vPoints.get(2+8), vPoints.get(3+8)), vPoints.get(2+8).getZ(), Color.white, false);
		setShapeObject(15, Shapes.Line(vPoints.get(3+8), vPoints.get(0+8)), vPoints.get(3+8).getZ(), Color.white, false);
		
  		setShapeObject(16, Shapes.Line(vPoints.get(0+4+8), vPoints.get(1+4+8)), vPoints.get(0+4+8).getZ(), Color.white, false);
		setShapeObject(17, Shapes.Line(vPoints.get(1+4+8), vPoints.get(2+4+8)), vPoints.get(1+4+8).getZ(), Color.white, false);
		setShapeObject(18, Shapes.Line(vPoints.get(2+4+8), vPoints.get(3+4+8)), vPoints.get(2+4+8).getZ(), Color.white, false);
		setShapeObject(19, Shapes.Line(vPoints.get(3+4+8), vPoints.get(0+4+8)), vPoints.get(3+4+8).getZ(), Color.white, false);
		
		setShapeObject(20, Shapes.Line(vPoints.get(0+8), vPoints.get(0+4+8)), vPoints.get(0+8).getZ(), Color.white, false);
		setShapeObject(21, Shapes.Line(vPoints.get(3+8), vPoints.get(3+4+8)), vPoints.get(3+8).getZ(), Color.white, false);
		
		setShapeObject(22, Shapes.Line(vPoints.get(1+8), vPoints.get(1+4+8)), vPoints.get(1+8).getZ(), Color.white, false);
		setShapeObject(23, Shapes.Line(vPoints.get(2+8), vPoints.get(2+4+8)), vPoints.get(2+8).getZ(), Color.white, false);
		
		setShapeObject(24, Shapes.Line(vPoints.get(0), vPoints.get(8)), vPoints.get(0).getZ(), Color.white, false);
		setShapeObject(25, Shapes.Line(vPoints.get(1), vPoints.get(9)), vPoints.get(1).getZ(), Color.white, false);
		setShapeObject(26, Shapes.Line(vPoints.get(2), vPoints.get(10)), vPoints.get(2).getZ(), Color.white, false);
		setShapeObject(27, Shapes.Line(vPoints.get(3), vPoints.get(11)), vPoints.get(3).getZ(), Color.white, false);

		setShapeObject(28, Shapes.Line(vPoints.get(0+4), vPoints.get(8+4)), vPoints.get(0+4).getZ(), Color.white, false);
		setShapeObject(29, Shapes.Line(vPoints.get(1+4), vPoints.get(9+4)), vPoints.get(1+4).getZ(), Color.white, false);
		setShapeObject(30, Shapes.Line(vPoints.get(2+4), vPoints.get(10+4)), vPoints.get(2+4).getZ(), Color.white, false);
		setShapeObject(31, Shapes.Line(vPoints.get(3+4), vPoints.get(11+4)), vPoints.get(3+4).getZ(), Color.white, false);
				
//		addShape(Shapes.Arc(new Vector2<Float>(x+500-2, y+350-2), 4, 4), Color.blue, true);
		
		int n = 32;
		for (Vector3<Float> values : vPoints) {
			setShapeObject(n++, Shapes.Arc(new Vector2<Float>(values.getX()-5, values.getY()-5), 10, 10), Color.RED, true);
		}
	}
}
