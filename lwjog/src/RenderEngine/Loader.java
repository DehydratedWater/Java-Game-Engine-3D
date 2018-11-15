package RenderEngine;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class Loader
{

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	private float zasiegMitMappingu = -0.6f;
	
		//Ta funkcja zwraca objekt typu RawModel(czyli tego który przechowuje wierzcho³ki oraz numer w tablicy VAO)
		//Przyjmuje tablice z wierzcho³kami
		public RawModel LoadToVAO(float[] pozycja, float[] kordTekst, float[] normals, int[] polaczenia)
		{
			//tworzy zmienna przechowujac¹ tymczasowo numer VAO i generuje ten numer za pomoca funkcji createVao()
			int VAOid = createVao();
			//Ustanawia po³aczenia pomiedzy wierzcho³kami
			BindIndiceasBuffer(polaczenia);
			//Generuje tablice VAO która ma zostaæ wyswietlona
			storeDataAtributteList(0, 3, pozycja);
			//generuje po³o¿enie tekstury
			storeDataAtributteList(1, 2, kordTekst);
			//Koñczy dzia³anie tablicy VAO
			storeDataAtributteList(2, 3, normals);
			unbindVAO();
			//Zwraca objekt RawModel z odnoœnikem do tablicy VAO i iloœæ wierzcho³ków 
			return new RawModel(VAOid, polaczenia.length);
		}
		
		public RawModel LoadToVAOwithBB(float[] pozycja, float[] kordTekst, float[] normals, int[] polaczenia, List<Vector3f> V, String name)
		{
			//tworzy zmienna przechowujac¹ tymczasowo numer VAO i generuje ten numer za pomoca funkcji createVao()
			int VAOid = createVao();
			//Ustanawia po³aczenia pomiedzy wierzcho³kami
			BindIndiceasBuffer(polaczenia);
			//Generuje tablice VAO która ma zostaæ wyswietlona
			storeDataAtributteList(0, 3, pozycja);
			//generuje po³o¿enie tekstury
			storeDataAtributteList(1, 2, kordTekst);
			//Koñczy dzia³anie tablicy VAO
			storeDataAtributteList(2, 3, normals);
			unbindVAO();
			//Zwraca objekt RawModel z odnoœnikem do tablicy VAO i iloœæ wierzcho³ków 
			
			return new RawModel(VAOid, polaczenia.length, V, name);
		}
		//£aduje teksture
		public int loadTexture(String nazwa)
		{
			//Tworzy objekt openGl typu texture
			Texture texture = null;
			try {
				//³aduje teksture typu PNG z miejsca z pachu
				texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+nazwa+".png"));
				GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, zasiegMitMappingu );
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//tworzy zmienn¹ która bêdzie przechowywaæ ID tekstury
			int textureID = texture.getTextureID();
			//dodaje to id do listy ID tekstur
			textures.add(textureID);
			//zwraca wartoœæ ID tekstury
			return textureID;
		}
		
		//Czyœci tablice VAO i VBO
		public void cleanUP()
		{
			//Usuwa tablice danych o numerach z Listy VBO
			for(int vbo:vbos)
			{
				GL15.glDeleteBuffers(vbo);
			}
			//Usuwa tablice danych o numerach z Listy VAO
			for(int vao:vaos)
			{
				GL30.glDeleteVertexArrays(vao);
			}
			//usuwa numery tekstur z tablic
			for(int texture:textures)
			{
				GL11.glDeleteTextures(texture);
			}
			
		}
		//Ta funkcja tworzy numer VAO
		private int createVao() 
		{
			//Tworzy zmienn¹ która przechowuje wartoœæ wygenerowan¹ przez generator numerów w tablicy VAO
			int vaoID = GL30.glGenVertexArrays();
			//Dodaje tê wartoœæ do listy przechowuj¹cej numery VAO
			vaos.add(vaoID);
			//Rezerwuje pole tablicy VAO o wygenerowanym numerze
			GL30.glBindVertexArray(vaoID);
			//Zwraca wygenerowan¹ wartoœæ
			return vaoID;
		}
		
		//Tworzy tablice VBO z modelem przekazanym w tablicy 
		private void storeDataAtributteList(int atributNumbar, int rozmiarKord, float[] pozycja) 
		{
			//Generowanie numeru VBO
			int vboID = GL15.glGenBuffers();
			//Dodanie numeru VBO do listy VBO
			vbos.add(vboID);
			//Tworzy tablice w VOB pod numerem wygenerowanym poprzednio
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
			//Tworzy buffor z danymi z tablicy
			FloatBuffer buffer = storeDataInFloatBuffer(pozycja);
			//Dodaje dane do VBO i ustala jakiego s¹ typu
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
			//Umieszcza VBO w VAO , pobiera numer VBO,pakiety danych (w trójk¹cie po trzy wiercho³ki,
			//Typ przyjmowanych danych, czy dane s¹ normalizowane? , dustans pomiêdzy wierzcho³kami, pocz¹tek danych
			GL20.glVertexAttribPointer(atributNumbar, rozmiarKord, GL11.GL_FLOAT, false, 0, 0);
			//Zamkniêcie zapisu VBO
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		
		//Ta funkcja konwetuje dane z tablicy na buffor z danymi typu float
		private FloatBuffer storeDataInFloatBuffer(float[] data) 
		{
			//Tworzy objekt buffer który dopasowuje rozmiar bufforu do d³ugoœci tablicy
			FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
			//Dodaje tablicê do bufforu
			buffer.put(data);
			//Koñczy formowanie bufforu ustanawia indeks na 0 oraz ustanawia koñcow¹ zawartoœæ
			buffer.flip();
			//Zwraca koñcowy buffor
			return buffer;
		}
		
		//koñczy zapis VAO
		private void unbindVAO() 
		{
			GL30.glBindVertexArray(0);			
		}
		
		//Ta funkcja konwetuje dane z tablicy na buffor z danymi typu int
				private IntBuffer storeDataInIntBuffer(int[] data) 
				{
					//Tworzy objekt buffer który dopasowuje rozmiar bufforu do d³ugoœci tablicy
					IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
					//Dodaje tablicê do bufforu
					buffer.put(data);
					//Koñczy formowanie bufforu ustanawia indeks na 0 oraz ustanawia koñcow¹ zawartoœæ
					buffer.flip();
					//Zwraca koñcowy buffor
					return buffer;
				}
		
		//Powiadamia openGL o sposobie ³¹czeñ punktów
		private void BindIndiceasBuffer(int[] polaczenia)
		{
			//tworzy numer vbo które bêdzie zawiera³o tablice pol¹czeñ
			int IDvbo = GL15.glGenBuffers();
			//dodaje do Listy vbo numer vbo
			vbos.add(IDvbo);
			//Dodaje do okreslonego vbo okreslenie typu danych
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, IDvbo);
			//Konertuje tablice int na buffor
			IntBuffer buffer = storeDataInIntBuffer(polaczenia);
			//Zamieszcza dane z tablicy po³¹czeñ i podaje typ danych
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		}
		
}
