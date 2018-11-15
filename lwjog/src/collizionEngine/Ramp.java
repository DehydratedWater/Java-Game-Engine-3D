package collizionEngine;

import org.lwjgl.util.vector.Vector3f;

public class Ramp extends BountingBox
{
	private int rotation;
	private float proporcja, height;
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public Ramp(Vector3f a, Vector3f b, String name, int rot, float Height) {
		super(a, b, name);
		setRotation(rot);
		if(rotation%2==0)
		{
			setProporcja(countDY()/countDX());
		}
		else
		{
			setProporcja(countDY()/countDZ());
		}
		height = Height;
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	public Vector3f getA()
	{
		return super.getA();
	}
	
	public Vector3f getB()
	{
		return super.getB();
	}
	
	public float countDX()
	{
		return CollizionManger.getDistanceBetwenAB(getA().x, getB().x);
	}

	public float countDY()
	{
		return CollizionManger.getDistanceBetwenAB(getA().y, getB().y);
	}
	
	public float countDZ()
	{
		return CollizionManger.getDistanceBetwenAB(getA().z, getB().z);
	}
	
	public float getHeigh(Vector3f C)
	{
		float distance;
		if(rotation==1)
		{
			if(C.z-getA().z < 0)
			{
				return height;
			}
			distance = CollizionManger.getDistanceBetwenAB(C.z, getA().z);
			if(distance>countDZ())
			{
				return countDY()- height;
			}
		}else if(rotation == 2)
		{
			if(C.x-getA().x < 0)
			{
				return height;
			}
			distance = CollizionManger.getDistanceBetwenAB(C.x, getA().x);
			if(distance>countDX())
			{
				return countDY()- height;
			}
		}else if(rotation == 3)
		{
			if(C.z-getB().z > 0)
			{
				return height;
			}
			distance = CollizionManger.getDistanceBetwenAB(C.z, getB().z);
			if(distance>countDZ())
			{
				return countDY()- height;
			}
		}else{
			if(C.x-getB().x > 0)
			{
				return height;
			}
			distance = CollizionManger.getDistanceBetwenAB(C.x, getB().x);
			if(distance>countDX())
			{
				return countDY()- height;
			}
		}
		
		return proporcja*distance - height;

		
	}
	
	public float getProporcja() {
		return proporcja;
	}
	public void setProporcja(float proporcja) {
		this.proporcja = proporcja;
	}
	

}
