package models;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;


//Ta klasa przechowuje liczbe wierzcho³ków oraz numer w tablicy VAO
public class RawModel 
{
private int vaoID;//Przechowuje numer w tablicy z VAO
private int vertexCounter; // Przechowuje liczbê wierzcho³ków/punktów/rogów/co kolwiek
private boolean hasBB = false;
private String modelName = "";
private List<Vector3f> Vertecis;
	public RawModel(int vaoID, int vertex)
	{
		//Ustanawia wartoœci modelu
		this.vaoID = vaoID;
		vertexCounter = vertex;
	}
	public RawModel(int vaoID, int vertex, List<Vector3f> V, String name)
	{
		//Ustanawia wartoœci modelu
		this.setModelName(name);
		this.vaoID = vaoID;
		this.Vertecis = V;
		vertexCounter = vertex;
		setHasBB(true);
	}
	public int getVaoID() {
		//Zwraca numer pakietu VAO
		return vaoID;
	}

	public int getVertexCounter() {
		//Zwraca liczbe rogów
		return vertexCounter;
	}
	public List<Vector3f> getVertecis() {
		return Vertecis;
	}

	public boolean isHasBB() {
		return hasBB;
	}
	public void setHasBB(boolean hasBB) {
		this.hasBB = hasBB;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


}
