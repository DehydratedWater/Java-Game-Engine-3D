package Entity;

import models.TextureModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;
import RenderEngine.DisplayManager;

public class Player extends Entity
{
	private boolean isInAir = false, lewo = false, prawo = false;
	private float move_speed = 50, currentMoveSpeed = 0;
	private float move_speedBOK = 30, currentMoveSpeedBOK = 0;
	private float turn_speed = 160, currentTurnSpeed = 0;
	private Vector3f gravity = new Vector3f(0, -50, 0);
	private float wysokosc_skou = 40;
	private float upwardsSpeedX = 0, upwardsSpeedY = 0, upwardsSpeedZ = 0;
	private float predkoscMyszy = 100;
	private boolean kolizja = false;
	private boolean onRamp = false;
	private Vector3f direction;
	
	
	
	public Player(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Terrain terrain)
	{
		Vector3f MoveDirection = new Vector3f();
		if(kolizja&&isInAir==false)
		{
			upwardsSpeedY = 0;
		}
//		if(kolizja)
//		{
//			increasePosition(-direction.x, -direction.y, -direction.z);
//			getBB().move(new Vector3f(-direction.x, -direction.y, -direction.z));
//			//moveTo(new Vector3f(0,0,0));
//			kolizja = false;
//		}
//		else
//		{
		checkInputsKeys();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getDelta(), 0);
		float distance = currentMoveSpeed * DisplayManager.getDelta();
		float distanceBOK = currentMoveSpeedBOK * DisplayManager.getDelta();
		float dx =(float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz =(float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		
		if(lewo)
		{
		dx +=(float) (distanceBOK * Math.sin(Math.toRadians(super.getRotY()+90)));
		dz +=(float) (distanceBOK * Math.cos(Math.toRadians(super.getRotY()+90)));
		}
		if(prawo)
		{
		dx -=(float) (distanceBOK * Math.sin(Math.toRadians(super.getRotY()+90)));
		dz -=(float) (distanceBOK * Math.cos(Math.toRadians(super.getRotY()+90)));
		}
//		super.increasePosition(dx, 0, dz);
		MoveDirection.x +=dx;
		MoveDirection.z +=dz;
		getBB().move(new Vector3f(dx, 0, dz));
		if(kolizja==false||isInAir)
		{
		upwardsSpeedX += gravity.x * DisplayManager.getDelta();
		upwardsSpeedY += gravity.y * DisplayManager.getDelta();
		upwardsSpeedZ += gravity.z * DisplayManager.getDelta();
		}
//		super.increasePosition
//		(upwardsSpeedX * DisplayManager.getDelta(), 
//		upwardsSpeedY * DisplayManager.getDelta(), 
//		upwardsSpeedZ * DisplayManager.getDelta());
		MoveDirection.x +=upwardsSpeedX * DisplayManager.getDelta();
		MoveDirection.y +=upwardsSpeedY * DisplayManager.getDelta();
		MoveDirection.z +=upwardsSpeedZ * DisplayManager.getDelta();
		getBB().move(new Vector3f(upwardsSpeedX * DisplayManager.getDelta(), 
				upwardsSpeedY * DisplayManager.getDelta(), 
				upwardsSpeedZ * DisplayManager.getDelta()));
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight)
		{
			upwardsSpeedX = 0;
			upwardsSpeedY = 0;
			upwardsSpeedZ = 0;
			isInAir = false;
			float roz = terrainHeight - super.getPosition().y;
			super.getPosition().y = terrainHeight;
			getBB().move(new Vector3f(0, roz, 0));
		}
		
		super.increasePosition(MoveDirection.x, MoveDirection.y, MoveDirection.z);
		currentTurnSpeed = 0;
		direction = MoveDirection;
		kolizja = false;
		onRamp = false;
		//System.out.println("kierunek"+MoveDirection);
//		}
	}
	public boolean isKolizja() {
		return kolizja;
	}
	public void setIsInAir(boolean skok)
	{
		isInAir = skok;
	}
	public boolean getIsInAir()
	{
		return isInAir;
	}
	public void movePlayer(Vector3f direction)
	{
		increasePosition(direction.x, direction.y, direction.z);
		getBB().move(new Vector3f(direction.x, direction.y, direction.z));
	}
	
	public void setKolizja(boolean kolizja) {
		this.kolizja = kolizja;
	}

	private void jump()
	{
		if(!isInAir)
		{
		this.upwardsSpeedY = wysokosc_skou;
		isInAir = true;
		}
	}
	
	public void moveTo(Vector3f point)
	{
		Vector3f przes = new Vector3f(point.x-getPosition().x, point.y-getPosition().y, point.z-getPosition().z);
		increasePosition(przes.x, przes.y, przes.z);
		getBB().move(przes);
	}
	
	private void checkInputsKeys()
	{
		
		float obrot = Mouse.getDX();
		
			currentTurnSpeed -= obrot*predkoscMyszy ;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			this.currentMoveSpeed = move_speed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			this.currentMoveSpeed = -move_speed;
		}
		else
		{
			this.currentMoveSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
		//	currentTurnSpeed = turn_speed;
			lewo = true;
			this.currentMoveSpeedBOK = move_speedBOK;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
		//	currentTurnSpeed = -turn_speed;
			lewo = true;
			this.currentMoveSpeedBOK = -move_speedBOK;
		}
		else
		{
			lewo = false;
			prawo = false;
			this.currentMoveSpeedBOK = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			jump();
		}

	}
	public float getPlayerMove_speed() {
		return move_speed;
	}

	public void setPlayerMove_speed(float move_speed) {
		this.move_speed = move_speed;
	}

	public float getPlayerTurn_speed() {
		return turn_speed;
	}

	public void setPlayerTurn_speed(float turn_speed) {
		this.turn_speed = turn_speed;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}

	public boolean isOnRamp() {
		return onRamp;
	}

	public void setOnRamp(boolean onRamp) {
		this.onRamp = onRamp;
	}
	public void setSpeedY(float speed)
	{
		upwardsSpeedY = speed;
	}
}
