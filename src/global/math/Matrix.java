package global.math;

public class Matrix {
	
	private int C, R;
	public int getC() {
		return C;
	}

	public int getR() {
		return R;
	}

	private float[][] matrix;
	
	public float getValue(int x, int y) {
		try {
			return this.matrix[x][y];			
		} catch (Exception e) {
			System.out.println("Could not get matrix value at: [x: " + x + " y: " + y + "]");
			System.out.println("Error on: " + this.toString());
			throw e;
		}
	}
	
	public float[][] getValues() {
		return this.matrix;
	}
	
	public Matrix(int C, int R) {
		this.C = C;
		this.R = R;
		this.matrix = new float[C][R];
		this.fill(0);
	}
	
	public Matrix(int C, int R, float value) {
		this.C = C;
		this.R = R;
		this.matrix = new float[C][R];
		this.fill(value);
	}
	
	public Matrix(float[][] values, int C, int R){
		this.C = C;
		this.R = R;
		try {
			this.matrix = values;			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void fill(float value) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = value;
			}
		}
	}
	
	public void set(Matrix m) {
		this.C = m.getC();
		this.R = m.getR();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = m.getValue(i, j);
			}
		}
	}
	
	public void set(int x, int y, float value) {
		this.matrix[x][y] = value;
	}
	
	public static Matrix transpose(Matrix m) {
		Matrix tMatrix = new Matrix(m.R, m.C);
		
		for (int i = 0; i < m.C; i++) {
			for (int j = 0; j < m.R; j++) {
				tMatrix.set(j, i, m.getValue(i, j));
			}
		}
		
		return tMatrix;
	}
	
	public Matrix transpose() {
		Matrix tMatrix = new Matrix(R, C);
		
		for (int i = 0; i < C; i++) {
			for (int j = 0; j < R; j++) {
				float value = this.getValue(i, j);
				tMatrix.set(j, i, value);
			}
		}
		this.C = tMatrix.C;
		this.R = tMatrix.R;
		this.matrix = tMatrix.getValues();
		return this;
	}
	
	public static Matrix mult(Matrix m1, Matrix m2) {
		//C1 == R2
		if(m1.C != m2.R) {
			System.out.println("The number of collumns in matrix A must match the number of rows in matrix B");
			System.out.println(m1.C + " != " + m2.R);
			return null;
		}		
		Matrix resMatrix = new Matrix(m1.R, m2.C);
		
			for (int y = 0; y < resMatrix.R; y++) {
				for (int x = 0; x < resMatrix.C; x++) {
				//Calculate dot product
				float value = 0;
				for (int i = 0; i < m1.C; i++) {
//					System.out.print("+");
					float m1V = m1.getValue(x, i);
					float m2V = m2.getValue(i, y);
					value += m1V * m2V;
//					System.out.print(" (" + m1V + "*" + m2V+ ") ");
				}
//				System.out.println("");
				resMatrix.set(x, y, value);					
			}
		}
//		System.out.println(resMatrix.toString());
		return resMatrix;
	}
	
	public Matrix mult(Matrix m) {
		//C == R
		Matrix m1 = this;
		if(m1.C != m.R) {
			System.out.println("The number of collumns in matrix A must match the number of rows in matrix B");
			return null;
		}
			
		
		Matrix resMatrix = new Matrix(m1.R, m.C);
		
		for (int x = 0; x < resMatrix.C; x++) {
			for (int y = 0; y < resMatrix.R; y++) {
				//Calculate dot product
				float value = 0;
				for (int i = 0; i < m1.C; i++) {
					float m1V = m1.getValue(x, i);
					float m2V = m.getValue(i, y);
					value += m1V * m2V;
//					System.out.print(" " + m1V + "*" + m2V+ " ");
				}
//				System.out.print("+");
				resMatrix.set(x, y, value);					
			}
//			System.out.println("\n");
		}
		
		return null;
	}
	
	public String toString() {
		String res = "[\n";
		
		for (int i = 0; i < matrix.length; i++) {
			res += "   ";
			for (int j = 0; j < matrix[i].length; j++) {
				res += this.getValue(i, j);
				if(j > matrix[i].length-1) {
					res += ", ";					
				}else if(j < matrix[i].length-1){
					res += " ";
				}
			}
			if(i < matrix.length-1) {
				res += "\n";				
			}
		}
		
		res += "\n]";		
		return res;
	}
}
