package Shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

//schemat klasy abstrakcyjnej kt�ra przechowuje wszystkie metody do obs�ugi
public abstract class ShaderProgram 
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	//konsrtuktor klasy
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		//Generuje numer VertexShadera kt�ry przechowuje po�o�enie wierzcho�k�w
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		//Generuje mumer FragmentShadera kt�ry generuje kolor pikseli
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		//Tworzy dzia�aj�cy program z przes�anych do openGl shader�w
		programID = GL20.glCreateProgram();
		//dodaje shader Vertex
		GL20.glAttachShader(programID, vertexShaderID);
		//tworzy shader fragment
		GL20.glAttachShader(programID, fragmentShaderID);
		//Informuje o po�o�eniu programu
		bindAtributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName)
	{
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	public void start()
	{
		//uruchamia shadery
		GL20.glUseProgram(programID);
	}
	
	public void stop()
	{
		//zatrzymuje shadery
		GL20.glUseProgram(0);
	}
	
	public void clenUp()
	{
		//usuwa shadery
		stop();
		//wy��cza shadery
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		//usuwa shadery
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		//usuwa skompilowany program
		GL20.glDeleteProgram(programID);
	}
	
	protected abstract void bindAtributes();
	
	protected void BindAtributs(int atrybut, String variableName)
	{
		//za�adowanie shader�w
		GL20.glBindAttribLocation(programID, atrybut, variableName);
	}
	//�aduje przekszta�cenie
	protected void loadFloat(int location, float value)
	{
		GL20.glUniform1f(location, value);
	}
	
	protected void loadInt(int location, int value)
	{
		GL20.glUniform1i(location, value);
	}
	//nak�ada po�o�enie modelu
	protected void loadVector(int location, Vector3f vector)
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void load2DVector(int location, Vector2f vector)
	{
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	
	protected void loadBoolean(int location, boolean value)
	{
		float toLoad = 0;
		if(value)
		{
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix)
	{
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
		
	}
	
	//funkcja kt�ra �aduje i kompiluje shader a nast�pnie zwraca jego numer w openGL
	private static int loadShader(String plik, int type)
	{
		//tworzy obiekt kt�ry b�dzie przechowywa� kod shadera
		StringBuilder loadShader =  new StringBuilder();
		try{
			//Tworzy objekt do odczytu danych z shaderow
			BufferedReader odczyt = new BufferedReader(new FileReader(plik));
			String line;
			//P�tla kt�ra odczytuje dane z pliku i dopisuje te dane do StringBuildera
			while((line = odczyt.readLine())!=null)
			{
				loadShader.append(line).append("\n");
			}
			//Zamyka BufferedReader
			odczyt.close();
		}catch(IOException e)
		{
			//B��d
			System.err.println("Nie mozna odczytac pliku");
			e.printStackTrace();
			System.exit(-1);
		}
		//Generuje numer shadera 
		int shaderID = GL20.glCreateShader(type);
		//Wprowadza si� informacje do kom�rki o numerze shaderID
		GL20.glShaderSource(shaderID, loadShader);
		//Kompiluje program
		GL20.glCompileShader(shaderID);
		//Sprawdza czy nie wyst�pi�b��d kompilacji
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE)
		{
			//Kod b��du kompilacji
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Nie mozna skompilowac shadera");
			System.exit(-1);
		}
		//zwraca numer shadera
		return shaderID;
	}
}
