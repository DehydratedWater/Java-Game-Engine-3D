package Shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Entity.Camera;
import Entity.Light;
import toolbox.Maths;

public class StaticShader extends ShaderProgram
{
	
	private static final String Vertex_File = "src/Shaders/vertexShader.txt";
	private static final String Fragment_File = "src/Shaders/fragmentShader.txt";
	
	private int lok_transformationMatriks;
	private int lok_matrixProjection;
	private int lok_viewMatrix;
	private int lok_lightColor;
	private int lok_lightPosition;
	private int lok_shineDamper;
	private int lok_reflectivity;
	private int lok_areaBrightness;
	private int lok_SetLightUp;
	private int lok_skyColor;
	private int lok_gestoscMgly;
	private int lok_NumberOfRows;
	private int lok_offset;
	//Przesy³a lokalizacjê plików
	public StaticShader() 
	{
		super(Vertex_File, Fragment_File);
	}

	@Override
	protected void bindAtributes() 
	{
		//Wywo³yje shader zapisany pod numerem i aktywuje funkcje
		super.BindAtributs(0, "position");
		super.BindAtributs(1, "texturesCoordinates");
		super.BindAtributs(2, "normal");
	}
	
	//ustanawia Id przekszta³cenia pobiera jego wartoœæ z shaderaa
	protected void getAllUniformLocations() 
	{
		lok_transformationMatriks = super.getUniformLocation("transformationMatrix");
		lok_matrixProjection = super.getUniformLocation("projectionMatrix");
		lok_viewMatrix = super.getUniformLocation("viewMatrix");
		lok_lightPosition = super.getUniformLocation("lightPosition");
		lok_lightColor = super.getUniformLocation("lightColour");
		lok_shineDamper = super.getUniformLocation("shineDamper");
		lok_reflectivity = super.getUniformLocation("reflectivity");
		lok_areaBrightness = super.getUniformLocation("areaBrightness");
		lok_SetLightUp = super.getUniformLocation("SetLightUp");
		lok_skyColor = super.getUniformLocation("skyColour");
		lok_gestoscMgly = super.getUniformLocation("gestoscMgly");
		lok_NumberOfRows = super.getUniformLocation("numberOfRows");
		lok_offset = super.getUniformLocation("offset");
	}
	
	public void loadNumberOfRows(int numeberOfRows)
	{
		super.loadFloat(lok_NumberOfRows, numeberOfRows);
	}
	public void loadOffSet(float x, float y)
	{
		super.load2DVector(lok_offset, new Vector2f(x,y));
	}
	public void setGestoscMgly(float gestosc)
	{
		super.loadFloat(lok_gestoscMgly, gestosc);
	}
	public void loadSkyColor(float r, float g, float b)
	{
		super.loadVector(lok_skyColor, new Vector3f(r,g,b));
	}
	
	public void loadSetLightUp(boolean setLightUp)
	{
		super.loadBoolean(lok_SetLightUp, setLightUp);
	}
	
	public void loadShineVaribles(float damper, float reflectivity)
	{
		super.loadFloat(lok_shineDamper, damper);
		super.loadFloat(lok_reflectivity, reflectivity);
	}
	public void loadAreaBrightness(float brightness)
	{
		super.loadFloat(lok_areaBrightness, brightness);
	}
	//przekszta³ca i ³aduje efekty œwietlne
	public void loadTransMatrix(Matrix4f matrix)
	{
		super.loadMatrix(lok_transformationMatriks, matrix);
	}
	//³aduje efekty  œwietlne
	public void loadLight(Light light)
	{
		
		super.loadVector(lok_lightPosition, light.getPosition());
		super.loadVector(lok_lightColor, light.getColor());
	}
	//wywo³uje przekszta³cenia w³aœciwoœci
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(lok_transformationMatriks, matrix);
	}
	
	public void loadViewMatrix(Camera cam)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(cam);
		super.loadMatrix(lok_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(lok_matrixProjection, projection);
	}

}
