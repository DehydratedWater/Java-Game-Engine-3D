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
//Ta klasa zajmuje si� �adowniem modeli 3D
public class ObjectLoader 
{
	//Statyczna klasa kt�ra dekoduje plik z modelem 3D i przekszta�ca go do objektu RawModel
	public static RawModel loadObject(String fileName, Loader loader, boolean hasBountingBox)
	{
		//�adowany jest plik
		//System.out.println("ladownie pliku");
		FileReader fileReder = null;
		try {
			//Tworzenie filereadera do odczytu pliku
			fileReder = new FileReader(new File("res/"+fileName+".obj"));
		} catch (FileNotFoundException e) {
			//no co mo�e by� b��d xD
			System.out.println("Nie mo�na odnale�� pliku");
			e.printStackTrace();
		}
		//System.out.println("zaladowano");
		//Je�eli uda�o si� utworzy� FileReader to tworzy si� BufferReader
		BufferedReader br = new BufferedReader(fileReder);
		//String kt�ry b�dzie przechowywa� aktualnie czytan� lini�
		String line;
		//Listy kt�re b�d� przechowywa�y objekty okre�laj�ce po�o�enie tekstur, 
		//po��cze� oraz wierzcho�k�w a tak�e u�o�enia tych danych wzgl�dem siebie
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		//Wyj�ciowe tablice za pomoca kt�rych zostanie utworzony objekt RawModel
		float[] verticesArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;
		//System.out.println("utworzenie zmiennych i objektow");
		//P�tla kt�ra b�dzie odczytywa� plik a nast�pnie zapisywa� poszczeg�lne dane do poszczeg�lnych list
		try
		{
			while(true)
			{
				//odczytuje lini� tekstu z pliku
				line = br.readLine();
				//Dzieli te lini� na tablic� z danymi, okre�laj�c jako znak przerwania spacje 
				//(hymm ciekawa funkcja nie zna�em jej wcze�niej)
				String[] aktLinia = line.split(" ");
				//Je�eli linia zaczyna si� od v to oznacza �e dalej zapisany jest jej wierzcho�ek
				if(line.startsWith("v "))
				{
					//Tworzy objekt typu wektor3d i zapisuje jako warto�ci xyz trzy kolejne liczby 
					Vector3f vertex = new Vector3f(Float.parseFloat(aktLinia[1]),
							Float.parseFloat(aktLinia[2]),Float.parseFloat(aktLinia[3]));
					//dodaje ten wektor jako nowy wierzcholek
					vertices.add(vertex);
				}
				else if(line.startsWith("vt "))
				{
					//Tworzy objekt typy wektor2d i zapisuje jego wato�ci x y
					Vector2f texture = new Vector2f(Float.parseFloat(aktLinia[1]),
							Float.parseFloat(aktLinia[2]));
					//dodaje ten objekt do lity z polozeniem tekstur
					textures.add(texture);
				}
				else if(line.startsWith("vn "))
				{
					//pobiera po��czenia i tworzy z nich wektor
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
				//System.out.println("Pomy�lnie za�adowano i podzielono lini�");
				//rozpoczeto petle odczytujaca polaczenia pomi�dzy wierzcho�kami teksturami i po�aczeniami
				while(line!=null)
				{
					//je�eli linia nie zacznie si� od f to p�tla zostanie zako�czona na zawsze b�ahahaha
					if(!line.startsWith("f "))
					{
						line = br.readLine();
						continue;
					}
					//tworzy tablice przechowujace dane z trzech wierzcho�k�w dziel�c tekst na / 
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
		//tworzy tablic� zawieraj�ce wierzcho�ki oraz po�aczenia kt�re zosta�y wcze�niej pouk�adane w dobrej kolejno�ci
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		
		for(Vector3f vectex:vertices)
		{
			//tworzy tabli� wsp�rz�dnych wierzcho�k�w z tablicy wektor�w
			verticesArray[vertexPointer++] = vectex.x;
			verticesArray[vertexPointer++] = vectex.y;
			verticesArray[vertexPointer++] = vectex.z;
		}
		for(int i = 0; i < indices.size(); i++)
		{
			//tworzy tablic� po��cze� z przepisanej listy w dobrej kolejno�ci
			indicesArray[i] = indices.get(i);
		}
		//tworzy objekt kt�ry zwraca dane pod postaci� RawModel zawieraj�cy poszeczeg�le tablice z danymi
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
