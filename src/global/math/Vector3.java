package global.math;

public class Vector3<T>{
	
	private T x, y, z;
	
	public Vector3() {
		this.x = this.y = this.z = null;
	}
	
	public Vector3(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public T getX() {
		return this.x;
	}

	public T getY() {
		return this.y;
	}

	public T getZ() {
		return this.z;
	}
	
	public void set(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3<T> vec3) {
		this.x = vec3.getX();
		this.y = vec3.getY();
		this.z = vec3.getZ();
	}
	
	public String toString() {
		return "[x: " + this.getX() + ", y: " + this.getY() + ", z: " + this.getZ() + "]"; 
	}
	
	public Vector2<T> toVec2() {
		return new Vector2<T>(x, y);
	}
	
	public static Vector3<Float> scale(Vector3<Float> v, float s){
		return new Vector3<Float>(v.getX()*s, v.getY()*s, v.getZ()*s);
	}
	
	public static Vector3<Float> mult(Vector3<Float> v){
		
		return null;
	}
	
	public static Vector3<Float> normalize(Vector3<Float> v) {
		float d = (float) getLength(v);
		return new Vector3<Float>(v.getX() / d, v.getY() / d, v.getZ() / d);
	}

	public static double getLength(Vector3<Float> v) {
		return Math.sqrt(Math.pow((double) v.getX(), 2) + Math.pow((double) v.getY(), 2) + Math.pow((double) v.getZ(), 2));
	}
	
	public static double getDistance(Vector3<Float> v1, Vector3<Float> v2) {
		return Math.sqrt(Math.pow(v2.getX()-v1.getX(), 2) + Math.pow(v2.getY()-v1.getY(), 2) + Math.pow(v2.getZ()-v1.getZ(), 2));
	}
	
	public static double calcAngle() {
		return 0;
	}
	
	public static Vector3<Float> add(Vector3<Float> v1, Vector3<Float> v2) {
		return new Vector3<Float>(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
	}
	
	public static Vector3<Float> subtract(Vector3<Float> v1, Vector3<Float> v2) {
		return new Vector3<Float>(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
	}
}
