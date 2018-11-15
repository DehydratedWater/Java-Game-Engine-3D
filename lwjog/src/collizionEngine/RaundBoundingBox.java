package collizionEngine;

import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;

public class RaundBoundingBox extends BountingBox
{
	private static  Vector3f An;
	private static  Vector3f Bn;

	private Vector3f[] krawedzie;

	private Vector3f kat;
	public RaundBoundingBox(Vector3f a, Vector3f b, String name, Vector3f kat) 
	{
		super(a, b, name);
		An = a;
		Bn = b;
		krawedzie = getMinKrawedzie();
		this.setKat(kat);
	}
	public Vector3f getKat() {
		return kat;
	}
	public void setKat(Vector3f kat) {
		this.kat = kat;
	}
	
	public Vector3f[] getMinKrawedzie()
	{
		krawedzie[0] = An;
		krawedzie[1] = Bn;
		krawedzie[2] = getC();
		krawedzie[3] = getD();
		krawedzie[4] = getE();
		krawedzie[5] = getF();
		krawedzie[6] = getG();
		krawedzie[7] = getH();
		return krawedzie;
	}
	public Vector3f RaundPoint(Vector3f point, Vector3f kat)
	{
		return Maths.RaundXYZ(point, kat);
	}
	public void raundBox()
	{
		krawedzie = getMinKrawedzie();
		for(int i = 0; i < 8; i++)
		{
			krawedzie[i] = RaundPoint(krawedzie[i], kat);
			sum(krawedzie[i], getCenter());
		}
	}
	
	public Vector3f sum(Vector3f a, Vector3f b)
	{
		return new Vector3f((a.x+b.x), (a.y+b.y), (a.z+b.z));
	}
	public Vector3f getA()
	{
		return An;
	}
	
	public Vector3f getB()
	{
		return Bn;
	}
	public Vector3f getC()
	{
		return new Vector3f(An.x,Bn.y,Bn.z);
	}
	public Vector3f getD()
	{
		return new Vector3f(An.x,An.y,Bn.z);
	}
	public Vector3f getE()
	{
		return new Vector3f(Bn.x,An.y,Bn.z);
	}
	public Vector3f getF()
	{
		return new Vector3f(Bn.x,An.y,An.z);
	}
	public Vector3f getG()
	{
		return new Vector3f(Bn.x,Bn.y,An.z);
	}
	public Vector3f getH()
	{
		return new Vector3f(An.x,Bn.y,An.z);
	}
}
