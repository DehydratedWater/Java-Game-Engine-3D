package collizionEngine;

import org.lwjgl.util.vector.Vector3f;

public class BountingBox 
{
	private Vector3f A;
	private Vector3f B;
	
	private Vector3f sA;
	private Vector3f sB;
	
	private Vector3f Center;
//	private float scale = 1;
//	private Vector3f[] krawedzie;
	private String name;
	public BountingBox(Vector3f a, Vector3f b, String name) 
	{
		Vector3f nA = a;
		Vector3f nB = b;
		this.A = nA;
		this.B = nB;
		this.sA = nA;
		this.sB = nB;
		Center = new Vector3f();
//		krawedzie = new Vector3f[8];
		this.name = name;
	}
	public void move(Vector3f move)
	{
		A.x += move.x;
		A.y += move.y;
		A.z += move.z;
		B.x += move.x;
		B.y += move.y;
		B.z += move.z;
		Center.x += move.x;
		Center.y += move.y;
		Center.z += move.z;
		System.out.println("Center "+Center.x+" "+Center.y+" "+Center.z);
		drawCords();
	}
	
	public void scale(float s)
	{
		A.x *= s;
		A.y *= s;
		A.z *= s;
		B.x *= s;
		B.y *= s;
		B.z *= s;
		//scale *= s;
	}
	
	public Vector3f getCA()
	{
		Vector3f w = new Vector3f((sA.x+Center.x), (sA.y+Center.y), (sA.z+Center.z));
		return w;
	}
	
	public Vector3f getCB()
	{
		Vector3f w = new Vector3f((sB.x+Center.x), (sB.y+Center.y), (sB.z+Center.z));
		return w;
	}
	
	public void scaleC(float s)
	{
		A.x = (sA.x * s)+Center.x;
		A.y = (sA.y * s)+Center.y;
		A.z = (sA.z * s)+Center.z;
		B.x = (sB.x * s)+Center.x;
		B.y = (sB.y * s)+Center.y;
		B.z = (sB.z * s)+Center.z;
		//scale *= s;
	}
	public void drawCords()
	{
		System.out.println(A.x+" "+A.y+" "+A.z+" "+B.x+" "+B.y+" "+B.z);
	}
	
	public Vector3f getA()
	{
		return A;
	}
	
	public Vector3f getB()
	{
		return B;
	}
//	public Vector3f getC()
//	{
//		return new Vector3f(A.x,B.y,B.z);
//	}
//	public Vector3f getD()
//	{
//		return new Vector3f(A.x,A.y,B.z);
//	}
//	public Vector3f getE()
//	{
//		return new Vector3f(B.x,A.y,B.z);
//	}
//	public Vector3f getF()
//	{
//		return new Vector3f(B.x,A.y,A.z);
//	}
//	public Vector3f getG()
//	{
//		return new Vector3f(B.x,B.y,A.z);
//	}
//	public Vector3f getH()
//	{
//		return new Vector3f(A.x,B.y,A.z);
//	}
//	
	
	
//	public Vector3f[] getKrawedzie()
//	{
//		krawedzie[0] = A;
//		krawedzie[1] = B;
//		krawedzie[2] = getC();
//		krawedzie[3] = getD();
//		krawedzie[4] = getE();
//		krawedzie[5] = getF();
//		krawedzie[6] = getG();
//		krawedzie[7] = getH();
//		return krawedzie;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setA(Vector3f a) {
		A = a;
	}
	public void setB(Vector3f b) {
		B = b;
	}
	public Vector3f getsA() {
		return sA;
	}
	public void setsA(Vector3f sA) {
		this.sA = sA;
	}
	public Vector3f getsB() {
		return sB;
	}
	public Vector3f getCenter() {
		return Center;
	}
	public void setCenter(Vector3f center) {
		Center = center;
	}
	public void setsB(Vector3f sB) {
		this.sB = sB;
	}
}
