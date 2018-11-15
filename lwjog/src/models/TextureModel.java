package models;

import textures.ModelTextures;

public class TextureModel 
{
	private RawModel rawModel;
	private ModelTextures texture;
	


	public TextureModel (RawModel model, ModelTextures textures)
	{
		this.rawModel = model;
		this.texture = textures;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTextures getTexture() {
		return texture;
	}
	
}
