package RenderEngine;

import java.util.List;
import java.util.Map;

import models.RawModel;
import models.TextureModel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import textures.ModelTextures;
import toolbox.Maths;
import Entity.Entity;
import Shaders.StaticShader;

public class EntityRenderer 
{

	private StaticShader shader;
	
	public EntityRenderer(StaticShader ss, Matrix4f projectionMatrix)
	{
		this.shader = ss;
				ss.start();
		ss.loadProjectionMatrix(projectionMatrix);
		ss.stop();
	}
		
	
	public void render(Map<TextureModel, List<Entity>> entities)
	{
		for(TextureModel model:entities.keySet())
		{
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCounter(), GL11.GL_UNSIGNED_INT, 0);	
			}
			unbindTexturedModel();
			
		}
	}
	
	
	
	private void prepareTexturedModel(TextureModel model)
	{
		RawModel rawmodel = model.getRawModel(); 
		//£aduje do pamiêci numer VAO
		GL30.glBindVertexArray(rawmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTextures texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if(texture.isTransparency())
		{
			MasterRender.NoLoadModelBack();
		}
		shader.loadSetLightUp(texture.isSetLightUp());
		shader.loadShineVaribles(texture.getShineDamper(), texture.getReflectivity());
		shader.loadAreaBrightness(texture.getAreaBrightness());//ustanawia oœwietlenie otoczenia moja funkcja nareszcie ogarno³em te shadery
		//Generuje podany typ figur po podaniu iloœci wierzcho³ków
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCounter());
		//w³¹cza typ teksturowania
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().GetID());
	}
	private void unbindTexturedModel()
	{
		MasterRender.LoadModelBack();
		//Koñczy wyœwietlanie
		GL20.glDisableVertexAttribArray(0);
		//Koñczy wyœwietlanie
		GL20.glDisableVertexAttribArray(1);
		//Zamyka dane do wyœwietlania
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		//Zamyka dane do wyœwietlania
	}
	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), 
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadOffSet(entity.getTexturesXOffset(), entity.getTexturesYOffset());
	}

}
