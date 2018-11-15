package RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import terrains.Terrain;
import models.TextureModel;
import Entity.Camera;
import Entity.Entity;
import Entity.Light;
import Shaders.StaticShader;
import Shaders.TerrainShader;

public class MasterRender 
{
	private StaticShader shader = new StaticShader();
	private EntityRenderer entRender;
	
	private TerrainRenderer terRenderer;
	private TerrainShader terShader = new TerrainShader();
	private Matrix4f projectionMatrix;
	private Map<TextureModel, List<Entity>> entities = new HashMap<TextureModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private static final float FOV = 70;
	private static final float Near_Plane = 0.1f;
	private static final float Fear_Plane = 10000;
	private float RED = 0.1f, GREEN = 0.4f, BLU = 0.9f;
	private float gestoscMgly = 0.000003f;
	private boolean triangleMod = false;
	public MasterRender()
	{
		LoadModelBack();
		createProjectionMatrix();
		entRender = new EntityRenderer(shader, projectionMatrix);
		terRenderer = new TerrainRenderer(terShader, projectionMatrix);
	}
	
	public static void LoadModelBack()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void NoLoadModelBack()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public static void LoadTriangleModel()
	{
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	public void render(Light light, Camera cam)
	{
		prepare(RED, GREEN, BLU);
		if(triangleMod )
		{
			LoadTriangleModel();
		}
		shader.start();
		shader.loadSkyColor(RED, GREEN, BLU);
		shader.setGestoscMgly(gestoscMgly);
		shader.loadLight(light);
		shader.loadViewMatrix(cam);
		entRender.render(entities);
		shader.stop();
		terShader.start();
		terShader.loadSkyColor(RED, GREEN, BLU);
		terShader.setGestoscMgly(gestoscMgly);
		terShader.loadLight(light);
		terShader.loadViewMatrix(cam);
		terRenderer.render(terrains);
		terShader.stop();
		terrains.clear();
		entities.clear();
		
	}
	
	public void addTerain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	//Przygotowuje openGL do wyœwietlenia danych
		public void prepare(float Red, float Green, float Blu)
		{
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			//Czyœci buffor kolorów
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
			//Czyœci obraz wype³niaj¹c go jednorodnym kolorem ekran
			GL11.glClearColor(Red, Green, Blu, 1);
		}
	public void putEntity(Entity entity)
	{
		TextureModel entModel = entity.getModel();
		List<Entity> batch = entities.get(entModel);
		if(batch!=null)
		{
			batch.add(entity);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entModel, newBatch);
		}
	}
	private void createProjectionMatrix()
	{
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
		float x_scale = y_scale / aspectRatio;
		float frustum_length = Fear_Plane - Near_Plane;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((Fear_Plane+ Near_Plane)/ frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2*Near_Plane*Fear_Plane)/frustum_length);
		projectionMatrix.m33 = 0;
	}
	public void clenUP()
	{
		shader.clenUp();
		terShader.clenUp();
	}
}
