package Entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private Vector3f position = new Vector3f(0,20,50);
	private float PlayerDistane = 50;
	private float angleAroundPlayer = 0;
	private float pitch = 90; // pochylenie
	private float yaw ; //obrót
	private float roll; //obrót do góry nogami xD
	private Player player;
	private float wysokoscKameryNadGraczem = 10;
	public Camera(Player player){ this.player = player;}
	
	public void move()
	{
		obliczOdlegloscKamery();
		obliczObrotKamery();
		obliczPochylenieKamery();
		float horyzontalanaOdleglosc = obliczHoryzontalnie();
		float verdykalanaOdleglosc = obliczVerdykanie();
		obliczPozcjeKamery(horyzontalanaOdleglosc, verdykalanaOdleglosc);
	}
	
	private void obliczPozcjeKamery(float horOdl, float VerODL)
	{
		float thea = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horOdl * Math.sin(Math.toRadians(thea)));
		float offsetZ = (float) (horOdl * Math.cos(Math.toRadians(thea)));
		yaw = 180 - (player.getRotY() + angleAroundPlayer);
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y+VerODL + wysokoscKameryNadGraczem ;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void obliczOdlegloscKamery()
	{
		float zoom = Mouse.getDWheel() * 0.1f;
		PlayerDistane -= zoom;
	}
	
	
	private void obliczPochylenieKamery()
	{
			float pochylenie = Mouse.getDY() * 0.3f;
			pitch -= pochylenie;
	}
	private void obliczObrotKamery()
	{
		if(Mouse.isButtonDown(0))
		{
			float obrot = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= obrot;
		}
	}
	private float obliczHoryzontalnie()
	{
		return (float) (PlayerDistane * Math.cos(Math.toRadians(pitch)));
	}
	
	private float obliczVerdykanie()
	{
		return (float) (PlayerDistane * Math.sin(Math.toRadians(pitch)));
	}
	
}
