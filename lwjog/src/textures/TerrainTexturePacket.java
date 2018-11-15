package textures;


public class TerrainTexturePacket 
{
	private float shineDamper = 10;
	private float reflectivity = 0.2f;
	private  float AreaBrightness = 0.2f;
	private TerrainTexture BackgraundTextures;
	private TerrainTexture rTexture;
	private TerrainTexture gTexture;
	private TerrainTexture bTexture;
	public TerrainTexturePacket(TerrainTexture backgraundTextures, TerrainTexture rTexture, 
			TerrainTexture gTexture, TerrainTexture bTexture) 
	{
		BackgraundTextures = backgraundTextures;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}
	public TerrainTexture getBackgraundTextures() {
		return BackgraundTextures;
	}
	public TerrainTexture getrTexture() {
		return rTexture;
	}
	public TerrainTexture getgTexture() {
		return gTexture;
	}
	public TerrainTexture getbTexture() {
		return bTexture;
	}
	
	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getAreaBrightness() {
		return AreaBrightness;
	}
	public void setAreaBrightness(float areaBrightness) {
		AreaBrightness = areaBrightness;
	}
	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
}
