package global.math;

public class Vector2<T> {
	
	private T x, y;
	
	public Vector2() {
		this.x = null;
		this.y = null;
	}
	
	public Vector2(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2(Vector2<T> v) {
		this.x = v.getX();
		this.y = v.getY();
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}
	
	public void set(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2<T> vec2) {
		this.x = vec2.getX();
		this.y = vec2.getY();
	}
	
	public String toString() {
		return "[x: " + this.getX() + ", y: " + this.getY()+ "]"; 
	}
	
	public Vector3<T> toVec3() {
		return new Vector3<T>(x, y, null);
	}

	public static Vector2<Float> scale(Vector2<Float> v, float s){
		return new Vector2<Float>(v.getX()*s, v.getY()*s);
	}
	
	public static Vector2<Float> mult(Vector2<Float> v){
		
		return null;
	}
	
	public static Vector2<Float> normalize(Vector2<Float> v) {
		float d = (float) getLength(v);
		return new Vector2<Float>(v.getX() / d, v.getY() / d);
	}

	public static double getLength(Vector2<Float> v) {
		return Math.sqrt(Math.pow((double) v.getX(), 2) + Math.pow((double) v.getY(), 2));
	}
	
	public static double getDistance(Vector2<Float> v1, Vector2<Float> v2) {
		return Math.sqrt(Math.pow(v2.getX()-v1.getX(), 2) + Math.pow(v2.getY()-v1.getY(), 2));
	}
	
	public static double calcAngle() {
		return 0;
	}
	
	public static Vector2<Float> add(Vector2<Float> v1, Vector2<Float> v2) {
		return new Vector2<Float>(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}
	
	public static Vector2<Float> subtract(Vector2<Float> v1, Vector2<Float> v2) {
		return new Vector2<Float>(v1.getX() - v2.getX(), v1.getY() - v2.getY());
	}
}
