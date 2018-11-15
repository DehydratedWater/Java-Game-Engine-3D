package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import RenderEngine.Loader;
import textures.TerrainTexture;
import textures.TerrainTexturePacket;
import toolbox.Maths;
import models.RawModel;

public class Terrain 
{
	private static final float size = 800;
	private static final int max_height = 40;
	private static final int max_color = 255 * 255 * 255;
	private float[][] MapHeights;
	
	private float x, z;
	private RawModel modelTerenu;
	private TerrainTexturePacket texturePacket;
	private TerrainTexture blendMap;
	
	public Terrain(int pozX, int pozZ, Loader loader, TerrainTexturePacket texturePacket,TerrainTexture blendMap, String HeightMap)
	{
		this.blendMap = blendMap;
		this.texturePacket = texturePacket;
		this.x = pozX;
		this.z = pozZ;
		this.modelTerenu = generateTerrain(loader, HeightMap);
	}
	
	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModelTerenu() {
		return modelTerenu;
	}



	public TerrainTexturePacket getTexturePacket() {
		return texturePacket;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	//generuje i zwraca objekt typu Raw model za pomoca metody do tworzenia rawmodeli z loadera
	private RawModel generateTerrain(Loader loader, String heightmap)
	{
		BufferedImage map = null;
		 try {
			map = ImageIO.read(new File("res/"+heightmap+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int vertexCounter = map.getHeight();
		
		
		int count = vertexCounter * vertexCounter;
		MapHeights = new float[vertexCounter][vertexCounter];
		
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(vertexCounter-1)*(vertexCounter*1)];
		int vertexPointer = 0;
		for(int i=0;i<vertexCounter;i++){
			for(int j=0;j<vertexCounter;j++){
				vertices[vertexPointer*3] = (float)j/((float)vertexCounter - 1) * size;//szerokoœæ punktu
				float height = getHeight(j, i, map);
				MapHeights[j][i] = height;
				vertices[vertexPointer*3+1] = height;//wysokoœæ punktu
				vertices[vertexPointer*3+2] = (float)i/((float)vertexCounter - 1) * size;//d³ugoœæ punktu
				Vector3f normal = calculateNormals(j, i, map);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)vertexCounter - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)vertexCounter - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<vertexCounter-1;gz++){
			for(int gx=0;gx<vertexCounter-1;gx++){
				int topLeft = (gz*vertexCounter)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*vertexCounter)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.LoadToVAO(vertices, textureCoords, normals, indices);
	}
	
	private float getHeight(int x, int y, BufferedImage mapa)
	{
		if(x < 0 || x >= mapa.getHeight() || y < 0 || y >= mapa.getHeight())
		{
			return 0;
		}
		float height = mapa.getRGB(x, y);
		height += max_color/2f;
		height /= max_color/2f;
		height *= max_height;
		return height;
	}
	
	private Vector3f calculateNormals(int x, int y, BufferedImage mapa)
	{
		float heightL = getHeight(x-1, y, mapa);
		float heightR = getHeight(x+1, y, mapa);
		float heightD = getHeight(x, y-1, mapa);
		float heightU = getHeight(x, y+1, mapa);
		Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD-heightU);
		normal.normalise();
		return normal;
		
	}
	public float getHeightOfTerrain(float worldX, float worldZ)
	{
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float faces = size / (float)(MapHeights.length-1);
		int gridX = (int) Math.floor(terrainX/faces);
		int gridZ = (int) Math.floor(terrainZ/faces);
		if(gridX >= MapHeights.length - 1 || gridZ >= MapHeights.length-1 || gridX < 0 || gridZ < 0)
		{
			return 0;
		}
		float xCord = (terrainX % faces)/faces;
		float zCord = (terrainZ % faces)/faces;	
		float answer;
		if (xCord <= (1-zCord)) {
			answer = Maths
					.barryCentric(new Vector3f(0, MapHeights[gridX][gridZ], 0), new Vector3f(1,
							MapHeights[gridX + 1][gridZ], 0), new Vector3f(0,
							MapHeights[gridX][gridZ + 1], 1), new Vector2f(xCord, zCord));
		} else {
			answer = Maths
					.barryCentric(new Vector3f(1, MapHeights[gridX + 1][gridZ], 0), new Vector3f(1,
							MapHeights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
							MapHeights[gridX][gridZ + 1], 1), new Vector2f(xCord, zCord));
		}
		return answer;
	}
}
