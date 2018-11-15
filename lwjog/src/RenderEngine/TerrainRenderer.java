package RenderEngine;

import java.util.List;

import models.RawModel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;
import textures.TerrainTexturePacket;
import toolbox.Maths;
import Shaders.TerrainShader;

public class TerrainRenderer 
{
	
	private TerrainShader shader;
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix)
	{
		this.shader =shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.ConnectTextureUnits();
		shader.stop();
	}
	
	public void render(List<Terrain> terrains)
	{
		for(Terrain terrain:terrains)
		{
			prepareTerain(terrain);
			LoadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModelTerenu().getVertexCounter(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();			
		}
	}
	//wgrywa tekstury do okreœl;onych miejsc w pamiêci
	private void bindTextures(Terrain terrain)
	{
		TerrainTexturePacket terrainTexturePacket = terrain.getTexturePacket();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrainTexturePacket.getBackgraundTextures().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrainTexturePacket.getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrainTexturePacket.getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrainTexturePacket.getbTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
		
	}
	
	private void prepareTerain(Terrain terrain)
	{
		RawModel rawmodel = terrain.getModelTerenu(); 
		//£aduje do pamiêci numer VAO
		GL30.glBindVertexArray(rawmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTextures(terrain);
		shader.loadShineVaribles(terrain.getTexturePacket().getShineDamper(), terrain.getTexturePacket().getReflectivity());
		shader.loadAreaBrightness(terrain.getTexturePacket().getAreaBrightness());
	}
	private void unbindTexturedModel()
	{
		//Koñczy wyœwietlanie
		GL20.glDisableVertexAttribArray(0);
		//Koñczy wyœwietlanie
		GL20.glDisableVertexAttribArray(1);
		//Zamyka dane do wyœwietlania
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		//Zamyka dane do wyœwietlania
	}
	private void LoadModelMatrix(Terrain terain)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terain.getX(),0,terain.getZ()), 
				0, 0, 0, 1);
		
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
