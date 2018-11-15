package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Entity.Camera;

public class Maths 
{
	public static Matrix4f createTransformationMatrix(Vector3f transkrypcja, float rx, float ry, float rz, float rskale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(transkrypcja, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(rskale, rskale, rskale), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera cam)
	{
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(cam.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix); //pochylenie kamery
		Matrix4f.rotate((float) Math.toRadians(cam.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix); // obrót kamery
		Matrix4f.rotate((float) Math.toRadians(cam.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix); // przyblirzenie
		Vector3f cameraPos = cam.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
	public static Vector3f RaundX(Vector3f punkt, float kat)
	{
		float x = punkt.x;
		float y = (float) (punkt.y*Math.cos(Math.toRadians(kat)) - punkt.z*Math.sin(Math.toRadians(kat)));
		float z = (float) (punkt.y*Math.sin(Math.toRadians(kat)) + punkt.z*Math.cos(Math.toRadians(kat)));
		Vector3f wyn =  new Vector3f(x, y, z);
		return wyn;
	}
	
	public static Vector3f RaundY(Vector3f punkt, float kat)
	{
		float x = (float) (punkt.z*Math.sin(Math.toRadians(kat)) + punkt.x*Math.cos(Math.toRadians(kat)));
		float y = punkt.y;
		float z = (float) (punkt.z*Math.cos(Math.toRadians(kat)) - punkt.x*Math.sin(Math.toRadians(kat)));
		Vector3f wyn =  new Vector3f(x, y, z);
		return wyn;
	}
	
	public static Vector3f RaundZ(Vector3f punkt, float kat)
	{
		float x = (float) (punkt.x*Math.cos(Math.toRadians(kat)) - punkt.y*Math.sin(Math.toRadians(kat)));
		float y = (float) (punkt.x*Math.sin(Math.toRadians(kat)) + punkt.y*Math.cos(Math.toRadians(kat)));
		float z = punkt.z;
		Vector3f wyn =  new Vector3f(x, y, z);
		return wyn;
	}
	
	public static Vector3f RaundXYZ(Vector3f punkt, Vector3f kat)
	{
		Vector3f wyn = new Vector3f(punkt);
		wyn = RaundX(wyn, kat.x);
		wyn = RaundY(wyn, kat.y);
		wyn = RaundZ(wyn, kat.z);
		return wyn;
	}
}
