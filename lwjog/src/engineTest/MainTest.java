package engineTest;

 import models.RawModel;
import models.TextureModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import collizionEngine.CollizionManger;
import terrains.Terrain;
import textures.ModelTextures;
import textures.TerrainTexture;
import textures.TerrainTexturePacket;
import Entity.Camera;
import Entity.Entity;
import Entity.Light;
import Entity.Player;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRender;
import RenderEngine.ObjectLoader;

public class MainTest 
{
	
	
	public static void main(String[] args) 
	{

	//Tworzy ekran
	DisplayManager.CreateDisplay();
	Loader loader = new Loader();
	
	RawModel palmaMod = ObjectLoader.loadObject("palma", loader, true);
	ModelTextures palmaTex = new ModelTextures(loader.loadTexture("palma"));
	palmaTex.setSetLightUp(false);
	palmaTex.setTransparency(true);
	TextureModel palmaTexMod = new TextureModel(palmaMod,palmaTex);
	Entity palma = new Entity(palmaTexMod, new Vector3f(80, 0, -20), 0, 0, 0, 3, "PalmaBB");
	
	RawModel fernTexture = ObjectLoader.loadObject("fern", loader, true);

	ModelTextures textfern = new ModelTextures(loader.loadTexture("fern"));
	textfern.setNumberOfRows(2);
	TextureModel fernModel = new TextureModel(fernTexture, textfern);

	Entity fern1 = new Entity(fernModel, 0, new Vector3f(20, 0, 20), 0, 0, 0, 1);
	Entity fern2 = new Entity(fernModel, 1, new Vector3f(40, 0, 40), 0, 0, 0, 1);
	Entity fern3 = new Entity(fernModel, 2, new Vector3f(60, 0, 60), 0, 0, 0, 1);
	Entity fern4 = new Entity(fernModel, 3, new Vector3f(80, 0, 80), 0, 0, 0, 1);
	
	RawModel rampa1 = ObjectLoader.loadObject("rampa1", loader, true);
	RawModel rampa2 = ObjectLoader.loadObject("rampa2", loader, true);
	ModelTextures rampa = new ModelTextures(loader.loadTexture("rampa1"));
	TextureModel Rampa1 = new TextureModel(rampa1, rampa);
	TextureModel Rampa2 = new TextureModel(rampa2, rampa);
	Entity ramp = new Entity(Rampa1, new Vector3f(100, 10, 100), 0, 0, 0, 5, 3);
	Entity ramp2 = new Entity(Rampa2, new Vector3f(150, 0, 150), 0, 0, 0, 5, 3);
	
	
	RawModel rampa3 = ObjectLoader.loadObject("rampa3", loader, true);
	RawModel rampa4 = ObjectLoader.loadObject("rampa4", loader, true);
	RawModel rampa5 = ObjectLoader.loadObject("rampa5", loader, true);
	TextureModel raMpa1 = new TextureModel(rampa2, rampa);
	TextureModel raMpa2 = new TextureModel(rampa3, rampa);
	TextureModel raMpa3 = new TextureModel(rampa4, rampa);
	TextureModel raMpa4 = new TextureModel(rampa5, rampa);
	
	Entity ramPa1 = new Entity(raMpa1, new Vector3f(150, 0, 150), 0, 0, 0, 5, 3);
	Entity ramPa2 = new Entity(raMpa2, new Vector3f(200, 0, 150), 0, 0, 0, 5, 4);
	Entity ramPa3 = new Entity(raMpa3, new Vector3f(280, 0, 150), 0, 0, 0, 5, 2);
	Entity ramPa4 = new Entity(raMpa4, new Vector3f(300, 0, 150), 0, 0, 0, 5, 1);
	
	TerrainTexture backgraundTexture = new TerrainTexture(loader.loadTexture("grassy"));
	TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
	TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
	TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
	 
	TerrainTexturePacket textPacket = new TerrainTexturePacket(backgraundTexture, rTexture, gTexture, bTexture);
	
	TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
	RawModel model = ObjectLoader.loadObject("dragon", loader, true);
	System.out.println("zaladowano model");
	
	ModelTextures texture = new ModelTextures(loader.loadTexture("img"));
	texture.setShineDamper(10);
	texture.setReflectivity(1);
	texture.setAreaBrightness(0.1f);
	
	TextureModel textModel = new TextureModel(model, texture );
	System.out.println("Zaladowano teksture");
	
	
	RawModel stall = ObjectLoader.loadObject("stanfordBunny", loader, true);
	System.out.println("zaladowano model");
	
	TextureModel TeksturaPostaci = new TextureModel(stall,  new ModelTextures(loader.loadTexture("white")));
	TeksturaPostaci.getTexture().setAreaBrightness(0.1f);
	TeksturaPostaci.getTexture().setTransparency(false);
	TeksturaPostaci.getTexture().setSetLightUp(false);

	
	Player player = new Player(TeksturaPostaci, new Vector3f(0, 1 ,-50), 0, 0, 0, 1);
	
	RawModel ObjMapy = ObjectLoader.loadObject("mapa", loader, true);
	ModelTextures TeksMapy = new ModelTextures(loader.loadTexture("teksturyMapyY"));
	TextureModel ModelMapy = new TextureModel(ObjMapy,TeksMapy);
	Entity MAPA = new Entity(ModelMapy, new Vector3f(300, 60, -300), 0, 0, 0, 3, "PalmaBB");
	
	
	RawModel SkyBOx = ObjectLoader.loadObject("skyBoxC", loader, false);
	TextureModel skymod = new TextureModel(SkyBOx,  new ModelTextures(loader.loadTexture("skyBox3ok")));
	skymod.getTexture().setTransparency(true);
	skymod.getTexture().setAreaBrightness(1f);
	skymod.getTexture().setSetLightUp(true);
	Entity SkyBoxModel = new Entity(skymod, new Vector3f(800, -800, -800),  0 , 0, 0, 1600);
	
	RawModel stall2 = ObjectLoader.loadObject("stall", loader, true);
	TextureModel stalll2 = new TextureModel(stall2,  new ModelTextures(loader.loadTexture("stallTexture")));
	Entity stal = new Entity(stalll2, new Vector3f(50,0,50), 0, 0, 0, 1);
	
	Entity entity3 = new Entity(textModel, new Vector3f(-7, 0, -25),  0 , 0, 0, 1);
	Entity entity2 = new Entity(textModel, new Vector3f(7, 0, -25),  0 , 0, 0, 1);
	entity2.increaseRotation(0, -180, 0);
	Light light = new Light(new Vector3f(4000,4000,3000), new Vector3f(1,1,1));
	Terrain terrain = new Terrain(0, -800, loader, textPacket, blendMap, "heightmap");
	Camera cam = new Camera(player);
	MasterRender rend = new MasterRender();

	
	RawModel map = ObjectLoader.loadObject("choinka", loader, true);
	TextureModel map2 = new TextureModel(map,  new ModelTextures(loader.loadTexture("choinka2")));
	Entity map3 = new Entity(map2, new Vector3f(-80, 0, -55),  0 , 0, 0, 5);
	Entity[] obj = new Entity[16];
	obj[0]=entity2;
	obj[1]=entity3;
	obj[2]=stal;
	obj[3]=map3;
	obj[4]=fern1;
	obj[5]=fern2;
	obj[6]=fern3;
	obj[7]=fern4;
	obj[8]=ramp;
	obj[9]=ramp2;
	obj[10]=palma;
	obj[11]=ramPa1;
	obj[12]=ramPa2;
	obj[13]=ramPa3;
	obj[14]=ramPa4;
	obj[15]=MAPA;

	
	while(Display.isCloseRequested()==false)
	{
		
		entity3.increaseRotation(0, 1, 0);
		entity2.increaseRotation(0, -1, 0);
		CollizionManger.checkCollizionsN(obj, player);
		cam.move();
		
		player.move(terrain);
		rend.putEntity(SkyBoxModel);
		rend.addTerain(terrain);
		rend.putEntity(player);
		rend.putEntity(SkyBoxModel);
		
		

		for(int i = 0; i < obj.length; i++)
		{
			rend.putEntity(obj[i]);
		}
		rend.render(light, cam);
		DisplayManager.UbdateDisplay();	
		
	}
	rend.clenUP();
	//Czyœci tablice VAO i VBO
	loader.cleanUP();
	//Niszczy ekran
	DisplayManager.DestDisplay();
	}

	
}
