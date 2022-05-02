package global.math;

public class Matrix {
	
	protected int n, m; //rowsxcols
	protected float[][] values;
	
	public int getN() {
		return this.n;
	}
	
	public int getM() {
		return this.m;
	}
	
	public float[][] getValues(){
		return this.values;
	}
	
	public void setValues(float[][] values) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.values[i][j] = values[i][j];
			}
		}
	}
	
	public void setValues(int n, int m, float[][] values) {
		this.n = n;
		this.m = m;
		this.values = new float[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.values[i][j] = values[i][j];
			}
		}
	}

	public Matrix(int n, int m) {
		this.n = n;
		this.m = m;
		fillValues();
	}
	
	public Matrix(int n, int m, float[][] values) {
		this.n = n;
		this.m = m;
		this.values = values;
	}
	
	public void fillValues() {
		values = new float[n][m];
		fill2dArray(values, n, m, 0);
	}
	
	
	public static Matrix mult(Matrix m1, Matrix m2) {
		if(m1 == null || m2 == null) return null;
		if(m1.m != m2.n) {
			System.out.println("The number of collumns in matrix A must match the number of rows in matrix B");
			System.out.println("Matrix A: " + m1.toString() + "\n Matrix B: "  + m2.toString() + "\n Can not be done");
			System.out.println(m1.n + " != " + m2.m);
			return null;
		}
		
		Matrix product = new Matrix(m1.n, m2.m);
//		System.out.println("Product size: " + product.getN() + "x" + product.getM());
		
		for (int y = 0; y < product.n; y++) {//3
			for (int x = 0; x < product.m; x++) {//2
				//Calculate dot product
				float value = 0;
				for (int i = 0; i < m1.m; i++) {
					float m1V = m1.getValues()[y][i];
					float m2V = m2.getValues()[i][x];
					value += m1V * m2V;
//					System.out.print(" (" + m1V + "*" + m2V+ ") ");
				}
				product.getValues()[y][x] = value;		
			}
//			System.out.println("");
		}
		
		return product;
	}
	
	public Matrix mult(float scaler) {
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.values[i][j]*=scaler;
			}
		}
		return this;
	}
	
	private void fill2dArray(float[][] arr, int r, int c, float value) {
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				arr[i][j] = value;
			}
		}
	}
	
	
	public String toString() {
		String res = "[";
		
		for (int i = 0; i < values.length; i++) {
			res += "   ";
			for (int j = 0; j < values[i].length; j++) {
				res += values[i][j];
				if(j > values[i].length-1) {
					res += ", ";					
				}else if(j < values[i].length-1){
					res += " ";
				}
			}
			if(i < values.length-1) {
				res += "\n";				
			}
		}
		
		res += "]";		
		return res;
	}
}
