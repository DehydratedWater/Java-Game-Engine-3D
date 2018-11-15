package RenderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
//Ta klasa zajmuje siê ³adowniem modeli 3D
public class ObjectLoader 
{
	//Statyczna klasa która dekoduje plik z modelem 3D i przekszta³ca go do objektu RawModel
	public static RawModel loadObject(String fileName, Loader loader, boolean hasBountingBox)
	{
		//³adowany jest plik
		//System.out.println("ladownie pliku");
		FileReader fileReder = null;
		try {
			//Tworzenie filereadera do odczytu pliku
			fileReder = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			//no co mo¿e byæ b³¹d xD
			System.out.println("Nie mo¿na odnaleŸæ pliku");
			e.printStackTrace();
		}
		//System.out.println("zaladowano");
		//Je¿eli uda³o siê utworzyæ FileReader to tworzy siê BufferReader
		BufferedReader br = new BufferedReader(fileReder);
		//String który bêdzie przechowywa³ aktualnie czytan¹ liniê
		String line;
		//Listy które bêd¹ przechowywa³y objekty okreœlaj¹ce po³o¿enie tekstur, 
		//po³¹czeñ oraz wierzcho³ków a tak¿e u³o¿enia tych danych wzglêdem siebie
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		//Wyjœciowe tablice za pomoca których zostanie utworzony objekt RawModel
		float[] verticesArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
		//System.out.println("utworzenie zmiennych i objektow");
		//Pêtla która bêdzie odczytywaæ plik a nastêpnie zapisywaæ poszczególne dane do poszczególnych list
		try
		{
			while(true)
			{
				//odczytuje liniê tekstu z pliku
				line = br.readLine();
				//Dzieli te liniê na tablicê z danymi, okreœlaj¹c jako znak przerwania spacje 
				//(hymm ciekawa funkcja nie zna³em jej wczeœniej)
				String[] aktLinia = line.split(" ");
				//Je¿eli linia zaczyna siê od v to oznacza ¿e dalej zapisany jest jej wierzcho³ek
				if(line.startsWith("v "))
				{
					//Tworzy objekt typu wektor3d i zapisuje jako wartoœci xyz trzy kolejne liczby 
					Vector3f vertex = new Vector3f(Float.parseFloat(aktLinia[1]),
							Float.parseFloat(aktLinia[2]),Float.parseFloat(aktLinia[3]));
					//dodaje ten wektor jako nowy wierzcholek
					vertices.add(vertex);
				}
				else if(line.startsWith("vt "))
				{
					//Tworzy objekt typy wektor2d i zapisuje jego watoœci x y
					Vector2f texture = new Vector2f(Float.parseFloat(aktLinia[1]),
							Float.parseFloat(aktLinia[2]));
					//dodaje ten objekt do lity z polozeniem tekstur
					textures.add(texture);
				}
				else if(line.startsWith("vn "))
				{
					//pobiera po³¹czenia i tworzy z nich wektor
					Vector3f normal = new Vector3f(Float.parseFloat(aktLinia[1]),
							Float.parseFloat(aktLinia[2]),Float.parseFloat(aktLinia[3]));
					normals.add(normal);
				}
				else if(line.startsWith("f "))
				{
					//Tworzy tablice tekstur i polaczen
					texturesArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
				}
			}
				//System.out.println("Pomyœlnie za³adowano i podzielono liniê");
				//rozpoczeto petle odczytujaca polaczenia pomiêdzy wierzcho³kami teksturami i po³aczeniami
				while(line!=null)
				{
					//je¿eli linia nie zacznie siê od f to pêtla zostanie zakoñczona na zawsze b³ahahaha
					if(!line.startsWith("f "))
					{
						line = br.readLine();
						continue;
					}
					//tworzy tablice przechowujace dane z trzech wierzcho³ków dziel¹c tekst na / 
					String[] aktulinia = line.split(" ");
					String[] vertex1 = aktulinia[1].split("/");
					String[] vertex2 = aktulinia[2].split("/");
					String[] vertex3 = aktulinia[3].split("/");
					//dopasowuje dane do podanego punktu
					procesVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
					procesVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
					procesVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
					line = br.readLine();
				}
			
			br.close();
			System.out.println("Zakonczono odczyt "+fileName);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//tworzy tablicê zawieraj¹ce wierzcho³ki oraz po³aczenia które zosta³y wczeœniej pouk³adane w dobrej kolejnoœci
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		
		for(Vector3f vectex:vertices)
		{
			//tworzy tabliê wspó³rzêdnych wierzcho³ków z tablicy wektorów
			verticesArray[vertexPointer++] = vectex.x;
			verticesArray[vertexPointer++] = vectex.y;
			verticesArray[vertexPointer++] = vectex.z;
		}
		for(int i = 0; i < indices.size(); i++)
		{
			//tworzy tablicê po³¹czeñ z przepisanej listy w dobrej kolejnoœci
			indicesArray[i] = indices.get(i);
		}
		//tworzy objekt który zwraca dane pod postaci¹ RawModel zawieraj¹cy poszeczególe tablice z danymi
		if(hasBountingBox == false)
		{
			return loader.LoadToVAO(verticesArray, texturesArray, normalsArray, indicesArray);
		}
		if(hasBountingBox)
		{
			return loader.LoadToVAOwithBB(verticesArray, texturesArray, normalsArray, indicesArray, vertices, fileName);
		}
		return null;
	}
	
	
	private static void procesVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,
			List<Vector3f> normals, float[] textureArray, float[] normalArray)
	{
		int currentVertexPointer = Integer.parseInt(vertexData[0])-1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalArray[currentVertexPointer*3]= currentNorm.x;
		normalArray[currentVertexPointer*3+1]= currentNorm.y;
		normalArray[currentVertexPointer*3+2]= currentNorm.z;
		
	}
}
