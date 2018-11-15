package textures;

public class ModelTextures 
{
	//przechowuje numer tekstury
	private int textureID;
	private float shineDamper = 10;
	private float reflectivity = 0.2f;
	private  float AreaBrightness = 0.2f;
	private boolean transparency = false;
	private boolean setLightUp = false;
	
	private int numberOfRows = 1;
	//Ustanawia numer tekstury
	public ModelTextures(int id)
	{
		this.textureID = id;
	}
	//Zwraca numer tekstury
	public int GetID()
	{
		return this.textureID;
	}
	
	public boolean isTransparency() 
	{
		return transparency;
	}
	public void setTransparency(boolean transparency) 
	{
		this.transparency = transparency;
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
	public boolean isSetLightUp() {
		return setLightUp;
	}
	public void setSetLightUp(boolean setLightUp) {
		this.setLightUp = setLightUp;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
}
