package math;

public class vec2 {
	private float x, y;
	
	public vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static vec2 multiply(vec2 first, vec2 second) {
		return new vec2(first.x*second.x, first.y*second.y);
	}
	public vec2 multiply(vec2 another) {
		return new vec2(this.x*another.x, this.y*another.y);
	}
	public static void multiply(vec2 result, vec2 first, vec2 second) {
		result.x = first.x*second.x;
		result.y = first.y*second.y;
	}
	public static vec2 add(vec2 first, vec2 second) {
		return new vec2(first.x+second.x, first.y+second.y);
	}
	public static void add(vec2 result, vec2 first, vec2 second) {
		result.x = first.x+second.x;
		result.y = first.y+second.y;
	}
	public vec2 add(vec2 another) {
		return new vec2(this.x+another.x, this.y+another.y);
	}
	
	public void printVector(String info) {
		System.out.println(info + ": " + "(" + x + ", " + y + ")");
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public vec2 getNormalized() {
		float magnitude = getMagnitude();
		
		return new vec2(x/magnitude, y/magnitude);
	}
	
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	public static int getSizeInBytes() {
		return Float.BYTES*2;
	}
}
