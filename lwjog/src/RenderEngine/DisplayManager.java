package RenderEngine;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager 
{
private static int HEIGHT = 720;
private static int WEIGH = 1280;
private static final int FPS_CAP = 120;
private static long lastFPS = 0, lastFPST;
private static int fps;
private static float  delta;
private static boolean fullskreen = false;

public static long getTime()
{
	return ((Sys.getTime()*1000)/Sys.getTimerResolution());
}

public static long getHeigh()
{
	return HEIGHT;
}

public static long getWeigh()
{
	return WEIGH;
}

public static void ubdateFPS()
{
	if((getTime() - lastFPS)> 1000)
	{
		Display.setTitle("Nowe Okno FPS: "+fps);
		fps = 0;
		lastFPS = getTime();
	}
	fps++;
}
public static float getDelta()
{
	return delta;
}
	public static void CreateDisplay()
	{
		if(fullskreen )
		{
		Toolkit zestaw = Toolkit.getDefaultToolkit();
		Dimension wymiary = zestaw.getScreenSize();
		int wysokosc = wymiary.height;
		int szerokosc = wymiary.width;
		HEIGHT = wysokosc;
		WEIGH = szerokosc;
		}
		lastFPS = getTime();
		lastFPST = getTime();
		ContextAttribs atrybuty = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			
			Display.setDisplayMode(new DisplayMode(WEIGH, HEIGHT));
			Display.setTitle("Pierwsze Okno");
			Display.create(new PixelFormat(), atrybuty);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WEIGH, HEIGHT);
	}
	public static void UbdateDisplay()
	{
		Display.sync(FPS_CAP);
		Display.update();
		long cuurentFrameTime = getTime();
		delta = (cuurentFrameTime - lastFPST)/1000f;
		lastFPST = cuurentFrameTime;
		ubdateFPS();
	}
	public static void DestDisplay()
	{
		Display.destroy();
	}
}
