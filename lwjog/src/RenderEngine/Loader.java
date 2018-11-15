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
	
		//Ta funkcja zwraca objekt typu RawModel(czyli tego kt�ry przechowuje wierzcho�ki oraz numer w tablicy VAO)
		//Przyjmuje tablice z wierzcho�kami
		public RawModel LoadToVAO(float[] pozycja, float[] kordTekst, float[] normals, int[] polaczenia)
		{
			//tworzy zmienna przechowujac� tymczasowo numer VAO i generuje ten numer za pomoca funkcji createVao()
			int VAOid = createVao();
			//Ustanawia po�aczenia pomiedzy wierzcho�kami
			BindIndiceasBuffer(polaczenia);
			//Generuje tablice VAO kt�ra ma zosta� wyswietlona
			storeDataAtributteList(0, 3, pozycja);
			//generuje po�o�enie tekstury
			storeDataAtributteList(1, 2, kordTekst);
			//Ko�czy dzia�anie tablicy VAO
			storeDataAtributteList(2, 3, normals);
			unbindVAO();
			//Zwraca objekt RawModel z odno�nikem do tablicy VAO i ilo�� wierzcho�k�w 
			return new RawModel(VAOid, polaczenia.length);
		}
		
		public RawModel LoadToVAOwithBB(float[] pozycja, float[] kordTekst, float[] normals, int[] polaczenia, List<Vector3f> V, String name)
		{
			//tworzy zmienna przechowujac� tymczasowo numer VAO i generuje ten numer za pomoca funkcji createVao()
			int VAOid = createVao();
			//Ustanawia po�aczenia pomiedzy wierzcho�kami
			BindIndiceasBuffer(polaczenia);
			//Generuje tablice VAO kt�ra ma zosta� wyswietlona
			storeDataAtributteList(0, 3, pozycja);
			//generuje po�o�enie tekstury
			storeDataAtributteList(1, 2, kordTekst);
			//Ko�czy dzia�anie tablicy VAO
			storeDataAtributteList(2, 3, normals);
			unbindVAO();
			//Zwraca objekt RawModel z odno�nikem do tablicy VAO i ilo�� wierzcho�k�w 
			
			return new RawModel(VAOid, polaczenia.length, V, name);
		}
		//�aduje teksture
		public int loadTexture(String nazwa)
		{
			//Tworzy objekt openGl typu texture
			Texture texture = null;
			try {
				//�aduje teksture typu PNG z miejsca z pachu
				texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+nazwa+".png"));
				GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
				GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, zasiegMitMappingu );
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//tworzy zmienn� kt�ra b�dzie przechowywa� ID tekstury
			int textureID = texture.getTextureID();
			//dodaje to id do listy ID tekstur
			textures.add(textureID);
			//zwraca warto�� ID tekstury
			return textureID;
		}
		
		//Czy�ci tablice VAO i VBO
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
			//Tworzy zmienn� kt�ra przechowuje warto�� wygenerowan� przez generator numer�w w tablicy VAO
			int vaoID = GL30.glGenVertexArrays();
			//Dodaje t� warto�� do listy przechowuj�cej numery VAO
			vaos.add(vaoID);
			//Rezerwuje pole tablicy VAO o wygenerowanym numerze
			GL30.glBindVertexArray(vaoID);
			//Zwraca wygenerowan� warto��
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
			//Dodaje dane do VBO i ustala jakiego s� typu
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
			//Umieszcza VBO w VAO , pobiera numer VBO,pakiety danych (w tr�jk�cie po trzy wiercho�ki,
			//Typ przyjmowanych danych, czy dane s� normalizowane? , dustans pomi�dzy wierzcho�kami, pocz�tek danych
			GL20.glVertexAttribPointer(atributNumbar, rozmiarKord, GL11.GL_FLOAT, false, 0, 0);
			//Zamkni�cie zapisu VBO
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
		
		//Ta funkcja konwetuje dane z tablicy na buffor z danymi typu float
		private FloatBuffer storeDataInFloatBuffer(float[] data) 
		{
			//Tworzy objekt buffer kt�ry dopasowuje rozmiar bufforu do d�ugo�ci tablicy
			FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
			//Dodaje tablic� do bufforu
			buffer.put(data);
			//Ko�czy formowanie bufforu ustanawia indeks na 0 oraz ustanawia ko�cow� zawarto��
			buffer.flip();
			//Zwraca ko�cowy buffor
			return buffer;
		}
		
		//ko�czy zapis VAO
		private void unbindVAO() 
		{
			GL30.glBindVertexArray(0);			
		}
		
		//Ta funkcja konwetuje dane z tablicy na buffor z danymi typu int
				private IntBuffer storeDataInIntBuffer(int[] data) 
				{
					//Tworzy objekt buffer kt�ry dopasowuje rozmiar bufforu do d�ugo�ci tablicy
					IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
					//Dodaje tablic� do bufforu
					buffer.put(data);
					//Ko�czy formowanie bufforu ustanawia indeks na 0 oraz ustanawia ko�cow� zawarto��
					buffer.flip();
					//Zwraca ko�cowy buffor
					return buffer;
				}
		
		//Powiadamia openGL o sposobie ��cze� punkt�w
		private void BindIndiceasBuffer(int[] polaczenia)
		{
			//tworzy numer vbo kt�re b�dzie zawiera�o tablice pol�cze�
			int IDvbo = GL15.glGenBuffers();
			//dodaje do Listy vbo numer vbo
			vbos.add(IDvbo);
			//Dodaje do okreslonego vbo okreslenie typu danych
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, IDvbo);
			//Konertuje tablice int na buffor
			IntBuffer buffer = storeDataInIntBuffer(polaczenia);
			//Zamieszcza dane z tablicy po��cze� i podaje typ danych
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		}
		
}
