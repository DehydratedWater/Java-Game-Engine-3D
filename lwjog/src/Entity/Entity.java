package Entity;

import org.lwjgl.util.vector.Vector3f;

import collizionEngine.BountingBox;
import collizionEngine.CollizionManger;
import collizionEngine.LowPrecisionTree;
import collizionEngine.Ramp;
import models.TextureModel;

public class Entity 
{
	//wszystkie w³aœciwoœci przekszta³cenia graficznego
	private TextureModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private int texktureIndex = 0;
	private BountingBox BB;
	private LowPrecisionTree LPT;
	private Ramp R;
	private boolean Collider = false, hasBountingBox = false, isRamp = false, isLowPrecisionTree = false;


	//konstruktor który ustawia w³aœciwoœci przekszta³cenia
	public Entity(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		System.out.println();
		System.out.println("tworzenie Objektu ");
		this.model = model;
		if(model.getRawModel().isHasBB()==true)
		{
			setCollider(true);
			this.BB = CollizionManger.createBBox(model.getRawModel().getVertecis(), model.getRawModel().getModelName());
			System.out.println("tworzenie bounting box "+BB.getName());
			setHasBountingBox(true);
			BB.drawCords();
			BB.getA().x*=scale;
			BB.getA().y*=scale;
			BB.getA().z*=scale;
			BB.getB().x*=scale;
			BB.getB().y*=scale;
			BB.getB().z*=scale;
			
			System.out.println("Przeskalowane");
		
			System.out.println("Skalowanie Bounting Box "+BB.getName());
			BB.drawCords();
			BB.getA().x+=position.x;
			BB.getA().y+=position.y;
			BB.getA().z+=position.z;
			BB.getB().x+=position.x;
			BB.getB().y+=position.y;
			BB.getB().z+=position.z;
			
			BB.drawCords();
		}
		else
		{
			setHasBountingBox(false);
		}
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int rotation) {
		System.out.println();
		System.out.println("tworzenie Objektu ");
		this.model = model;
		if(model.getRawModel().isHasBB()==true)
		{
			setCollider(true);
			this.BB = CollizionManger.createBBox(model.getRawModel().getVertecis(), model.getRawModel().getModelName());
			System.out.println("tworzenie bounting box "+BB.getName());
			//setHasBountingBox(true);
			BB.drawCords();
			BB.getA().x*=scale;
			BB.getA().y*=scale;
			BB.getA().z*=scale;
			BB.getB().x*=scale;
			BB.getB().y*=scale;
			BB.getB().z*=scale;
			
			System.out.println("Przeskalowane");
		
			System.out.println("Skalowanie Bounting Box "+BB.getName());
			BB.drawCords();
			BB.getA().x+=position.x;
			BB.getA().y+=position.y;
			BB.getA().z+=position.z;
			BB.getB().x+=position.x;
			BB.getB().y+=position.y;
			BB.getB().z+=position.z;
			
			BB.drawCords();
			R = new Ramp(BB.getA(), BB.getB(), "RAMP", rotation, position.y-BB.getA().y);
			setRamp(true);
		}
		else
		{
			setRamp(false);
		}
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(TextureModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
	{
		System.out.println("tworzenie Objektu ");
		this.texktureIndex = index;
		this.model = model;
		if(model.getRawModel().isHasBB()==true)
		{
			setCollider(true);
			this.BB = CollizionManger.createBBox(model.getRawModel().getVertecis(), model.getRawModel().getModelName());
			System.out.println("tworzenie bounting box "+BB.getName());
			setHasBountingBox(true);
			BB.drawCords();
			BB.getA().x*=scale;
			BB.getA().y*=scale;
			BB.getA().z*=scale;
			BB.getB().x*=scale;
			BB.getB().y*=scale;
			BB.getB().z*=scale;
		
			System.out.println("Przeskalowane");
						
			
			System.out.println("Skalowanie Bounting Box "+BB.getName());
			BB.drawCords();
			BB.getA().x+=position.x;
			BB.getA().y+=position.y;
			BB.getA().z+=position.z;
			BB.getB().x+=position.x;
			BB.getB().y+=position.y;
			BB.getB().z+=position.z;
		
			System.out.println("Przesuniecie Bounting Box "+BB.getName());
			BB.drawCords();
		}
		else
		{
		setHasBountingBox(false);
		}
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name) {
		System.out.println();
		System.out.println("tworzenie Objektu ");
		this.model = model;
		if(model.getRawModel().isHasBB()==true)
		{
			setCollider(true);
			setLowPrecisionTree(true);
			this.BB = CollizionManger.createBBox(model.getRawModel().getVertecis(), model.getRawModel().getModelName());
			System.out.println("Tworzenie LPT");
			this.LPT = CollizionManger.GenerateLPT(name, BB);
			System.out.println("tworzenie bounting box "+BB.getName());
			LPT.getBB().drawCords();
			LPT.getBB().getA().x*=scale;
			LPT.getBB().getA().y*=scale;
			LPT.getBB().getA().z*=scale;
			LPT.getBB().getB().x*=scale;
			LPT.getBB().getB().y*=scale;
			LPT.getBB().getB().z*=scale;
			
			System.out.println("Przeskalowane");
		
			System.out.println("Skalowanie Bounting Box "+LPT.getBB().getName());

			
			LPT.getBB().drawCords();
			System.out.println("Skalowanie ma³ych BB");
			for(int i = 0; i < LPT.getTab().length; i++)
			{
				System.out.println("Stare Kordynaty Ma³ych BB");
				LPT.getTab()[i].drawCords();
				LPT.getTab()[i].getA().x*=scale;
				LPT.getTab()[i].getA().y*=scale;
				LPT.getTab()[i].getA().z*=scale;
				LPT.getTab()[i].getB().x*=scale;
				LPT.getTab()[i].getB().y*=scale;
				LPT.getTab()[i].getB().z*=scale;
				
				System.out.println("Przeskalowane Nowe kordynaty ");
				LPT.getTab()[i].drawCords();
			}
			LPT.MoveTree(position);
		}
		else
		{
			setLowPrecisionTree(false);
		}
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void moveEntity(Vector3f direction)
	{
		increasePosition(direction.x, direction.y, direction.z);
		if(Collider)
		{
		if(hasBountingBox)
		BB.move(direction);
		if(isRamp)
		{
			R.move(direction);
		}
		if(isLowPrecisionTree())
		{
			LPT.MoveTree(direction);
		}
		}
	}
	
	public BountingBox getBB() {
		if(hasBountingBox)
		return BB;
		if(isRamp)
			return new BountingBox(R.getA(), R.getB(), R.getName());
		if(isLowPrecisionTree)
		return LPT.getBB();
		else
		return BB;
	}
	public float getTexturesXOffset()
	{
		int column = texktureIndex%model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	
	public float getTexturesYOffset()
	{
		int row = texktureIndex/model.getTexture().getNumberOfRows();
		return row/model.getTexture().getNumberOfRows();
	}
	
	//ta funkcja zmienia po³o¿enie objektu
	public void increasePosition(float dx, float dy, float dz)
	{
		this.position.x +=dx;
		this.position.y +=dy;
		this.position.z +=dz;
	}
	
	//tafunkcja zmienia k¹t objotu objektu
	public void increaseRotation(float dx, float dy, float dz)
	{
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	
	public TextureModel getModel() {
		return model;
	}

	public void setModel(TextureModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	public boolean isHasBountingBox() {
		return hasBountingBox;
	}
	public void setHasBountingBox(boolean hasBountingBox) {
		this.hasBountingBox = hasBountingBox;
	}

	public Ramp getR() {
		return R;
	}

	public void setR(Ramp r) {
		R = r;
	}

	public boolean isRamp() {
		return isRamp;
	}

	public void setRamp(boolean isRamp) {
		this.isRamp = isRamp;
	}

	public boolean isLowPrecisionTree() {
		return isLowPrecisionTree;
	}

	public void setLowPrecisionTree(boolean isLowPrecisionTree) {
		this.isLowPrecisionTree = isLowPrecisionTree;
	}

	public LowPrecisionTree getLPT() {
		return LPT;
	}

	public void setLPT(LowPrecisionTree lPT) {
		LPT = lPT;
	}

	public boolean isCollider() {
		return Collider;
	}

	public void setCollider(boolean collider) {
		Collider = collider;
	}
	
	
}
