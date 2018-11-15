package collizionEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector3f;

import Entity.Entity;
import Entity.Player;
import RenderEngine.DisplayManager;

public class CollizionManger 
{
	public static float pod = 3.5f;
	private static Vector3f max;
	private static Vector3f min;
	public static int kolizja(Vector3f A, Vector3f B, Vector3f[] krawedzie)
	{
		for(int i = 0; i < 8; i ++)
		{
		if(A.x<krawedzie[i].x||krawedzie[i].x<B.x||A.y<krawedzie[i].y||krawedzie[i].y<B.y||A.z<krawedzie[i].z||krawedzie[i].z<B.z)
		{
		}
		else
		{
			return i;
		}
		}
		return -1;
	}
	public static BountingBox createBBox(List<Vector3f> V, String name)
	{
		Vector3f min = new Vector3f(V.get(0).x,V.get(0).y,V.get(0).z);
		Vector3f max = new Vector3f(V.get(0).x,V.get(0).y,V.get(0).z);
		
		for(int j = 0; j<V.size();j++)
		{
		if ( V.get(j).x < min.x ) min.x = V.get(j).x;
		if ( V.get(j).y < min.y ) min.y = V.get(j).y;
		if ( V.get(j).z < min.z ) min.z = V.get(j).z;
		if ( V.get(j).x > max.x ) max.x = V.get(j).x;
		if ( V.get(j).y > max.y ) max.y = V.get(j).y;
		if ( V.get(j).z > max.z ) max.z = V.get(j).z;
		}
		BountingBox BB = new BountingBox(min, max, name);
		System.out.println();
		System.out.println("wczytano: "+name+" "+min.x+" "+min.y+" "+min.z+" "+max.x+" "+max.y+" "+max.z);
		return BB;
	}
	
	public static BountingBox createBBoxframTab(Vector3f[] V, String name)
	{
		Vector3f min = new Vector3f(V[0].x,V[0].y,V[0].z);
		Vector3f max = new Vector3f(V[0].x,V[0].y,V[0].z);
		
		for(int j = 0; j<V.length;j++)
		{
		if ( V[j].x < min.x ) min.x = V[j].x;
		if ( V[j].y < min.y ) min.y = V[j].y;
		if ( V[j].z < min.z ) min.z = V[j].z;
		if ( V[j].x > max.x ) max.x = V[j].x;
		if ( V[j].y > max.y ) max.y = V[j].y;
		if ( V[j].z > max.z ) max.z = V[j].z;
		}
		BountingBox BB = new BountingBox(min, max, name);
		System.out.println();
		System.out.println("wczytano: "+name+" "+min.x+" "+min.y+" "+min.z+" "+max.x+" "+max.y+" "+max.z);
		return BB;
	}
	
	public static int getDistanceBetwenPoints(Vector3f c, Vector3f v)
	{
		int wyn = (int) Math.sqrt(Math.pow((c.x-v.x), 2)+Math.pow((c.y-v.y), 2)+Math.pow((c.z-v.z), 2));
		return wyn;
	}
	
	public static int getDistanceBetwenAB(float c, float v)
	{
		int wyn = (int) Math.sqrt(Math.pow((c-v), 2));
		return wyn;
	}
	//Stare???????????????????????????????????????????????????????????????
//	public static void checkCollizions(Entity[] obj, Player player)
//	{
//		for(int i = 0; i< obj.length; i++)
//		{
//			if(obj[i].isHasBountingBox())
//			{
//			Vector3f min = obj[i].getBB().getA();
//			Vector3f max = obj[i].getBB().getB();
//			Vector3f kraw[] = player.getBB().getKrawedzie();
//			int wynik = CollizionManger.kolizja(max, min, kraw);
//			if(wynik>=0)
//			{
//				System.out.println("Kolizja "+wynik+" "+ obj[i].getBB().getName());
//				//obj[i].getBB().drawCords();
//				//player.getBB().drawCords();
//				player.setKolizja(true);
//			}
//			else
//			{
//				Vector3f min2 = player.getBB().getA();
//				Vector3f max2 = player.getBB().getB();
//				Vector3f kraw2[] = obj[i].getBB().getKrawedzie();
//				int wynik2 = CollizionManger.kolizja(max2, min2, kraw2);
//				if(wynik2>=0)
//				{
//					System.out.println("Kolizja "+wynik2+" "+ obj[i].getBB().getName());
//					//obj[i].getBB().drawCords();
//					//player.getBB().drawCords();
//					player.setKolizja(true);
//				}
//			}
//			}
//		}
//	}
	//Stare???????????????????????????????????????????????????????????????
	public static void increasePlayer(Player player, float a1, float a2, float a3, boolean t1, boolean t2, boolean t3)
	{
		int num = 0;
		boolean up = false;
		float min;
		if(a1 < a2)
		{
			if(a1 < a3)
			{
				//najmniejsze a1
				min = a1;
				num = 1;
			}
			else
			{
				//najmniejsze a3
				min = a3;
				num = 3;
			}
		}
		else
		{
			if(a2 < a3)
			{
				//najmniejsze a2
				min = a2;
				num = 2;
			}
			else
			{
				//najmniejsze a3
				min = a3;
				num = 3;
			}
		}
		
		if(a2<pod)
		{
			up = true;
		}
		if(up&&player.getIsInAir()==false&&t2==false)
		{
			player.movePlayer(new Vector3f(0, a2, 0));
		}
		if(num == 1&&up==false)
		{
			if(t1)
			{
				player.movePlayer(new Vector3f(-min, 0, 0));
			}
			else
			{
				player.movePlayer(new Vector3f(min, 0, 0));
			}
		}
		if(num == 2)
		{
			if(t2)
			{
				player.movePlayer(new Vector3f(0, -min*DisplayManager.getDelta(), 0));
				player.setSpeedY(0);
			}
			else
			{
				player.setKolizja(true);
				player.setIsInAir(false);
				player.movePlayer(new Vector3f(0, min*DisplayManager.getDelta(), 0));
			}
		}
		if(num == 3&&up==false)
		{
			if(t3)
			{
				player.movePlayer(new Vector3f(0, 0, -min));
			}
			else
			{
				player.movePlayer(new Vector3f(0, 0, min));
			}
		}
		
	}
	
	public static void increasePlayerRamp(Player player, float a1, float a2, float a3, boolean t1, boolean t2, boolean t3, Ramp R, float h)
	{
		int num = 0;
		float min;
		if(a1 < a2)
		{
			if(a1 < a3)
			{
				//najmniejsze a1
				min = a1;
				num = 1;
			}
			else
			{
				//najmniejsze a3
				min = a3;
				num = 3;
			}
		}
		else
		{
			if(a2 < a3)
			{
				//najmniejsze a2
				min = a2;
				num = 2;
			}
			else
			{
				//najmniejsze a3
				min = a3;
				num = 3;
			}
		}
		if(R.getHeigh(player.getPosition())+h>player.getPosition().y+R.getHeight()/2)
		{
			player.setIsInAir(false);
		}
//		
		if(R.getHeigh(player.getPosition())+h>player.getPosition().y+R.getHeight()/2&&player.isKolizja()==false)
			{
			if(num == 1&&min<1)
			{
				if(t1&&R.getRotation()!=2)
				{
					player.movePlayer(new Vector3f(-min, 0, 0));
					if(player.isOnRamp()==false)
					player.setIsInAir(true);
				}
				else if(t1==false&&R.getRotation()!=4)
				{
					player.movePlayer(new Vector3f(min, 0, 0));
					if(player.isOnRamp()==false)
					player.setIsInAir(true);
				}
			}
			if(num == 2||min>1)
			{
					player.moveTo(new Vector3f(player.getPosition().x, R.getHeigh(player.getPosition())+h, player.getPosition().z));
					player.setKolizja(true);
					player.setIsInAir(false);
					player.setOnRamp(true);
			}
			if(num == 3&&min<1)
			{
				if(t3&&R.getRotation()!=1)
				{
					player.movePlayer(new Vector3f(0, 0, -min));
					if(player.isOnRamp()==false)
					player.setIsInAir(true);
				}
				else if(t3==false&&R.getRotation()!=3)
				{
					player.movePlayer(new Vector3f(0, 0, min));
					if(player.isOnRamp()==false)
					player.setIsInAir(true);
				}
			}
		}
		else if(R.getHeigh(player.getPosition())<player.getPosition().y+R.getHeight()/2+h&&player.getIsInAir()==false)
		{
			player.moveTo(new Vector3f(player.getPosition().x, R.getHeigh(player.getPosition())+h, player.getPosition().z));
			player.setKolizja(true);
			player.setIsInAir(false);
			player.setOnRamp(true);
		}
	}
	
	public static void checkCollizionsN(Entity[] obj, Player player)
	{
		Vector3f A = player.getBB().getB();
		Vector3f B = player.getBB().getA();

		
		for(int i = 0; i< obj.length; i++)
		{
			if(obj[i].isCollider())
			{
			Vector3f An = obj[i].getBB().getB();
			Vector3f Bn = obj[i].getBB().getA();
			
			if(checkAABB(A, B, An, Bn))
			{
				if(obj[i].isHasBountingBox())
				{
					checkAABBColision(A, B, An, Bn, player);
				}
				else if(obj[i].isRamp())
				{
					checkAABBColisionRamp(A, B, An, Bn, player, obj[i]);
				}
				else if(obj[i].isLowPrecisionTree())
				{
					System.out.println("Sprawdzanie z drzewem");
					checkLowPrecisionTree(A, B, obj[i].getLPT(), player);
				}
			}
		}
		}
	}
	
	public static boolean checkAABB(Vector3f A, Vector3f B, Vector3f An, Vector3f Bn)
	{
		if((B.x<An.x)&&(A.x>Bn.x)&&(B.y<An.y)&&(A.y>Bn.y)&&(B.z<An.z)&&(A.z>Bn.z))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		

	public static void checkAABBColision(Vector3f A, Vector3f B, Vector3f An, Vector3f Bn, Player player)
	{
		float x1, y1, z1;
		float x2, y2, z2;
		boolean p1,p2,p3;
		if((B.x<An.x)&&(A.x>Bn.x))
		{
			x1 = An.x - B.x;
			x2 = A.x - Bn.x;
			if(x1 > x2)
			{
				p1 = true;
			}else{p1 = false;}
			x1 = Math.min(x1, x2);
		//	System.out.println("1x "+A.x+" "+B.x+" "+An.x+" "+Bn.x+" "+obj[i].getBB().getName());
			//##########################################################
			if((B.y<An.y)&&(A.y>Bn.y))
			{
				y1 = An.y - B.y;
				y2 = A.y - Bn.y;
				if(y1 > y2)
				{
					p2 = true;
				}else{p2 = false;}
				y1 = Math.min(y1, y2);
			//System.out.println("2y "+A.y+" "+B.y+" "+An.y+" "+Bn.y+" "+obj[i].getBB().getName());
			
			//##########################################################
				if((B.z<An.z)&&(A.z>Bn.z))
				{
					z1 = An.z - B.z;
					z2 = A.z - Bn.z;
					if(z1 > z2)
					{
						p3 = true;
					}else{p3 = false;}
					z1 = Math.min(z1, z2);
					//System.out.println("3z "+A.z+" "+B.z+" "+An.z+" "+Bn.z+" "+obj[i].getBB().getName());
					increasePlayer(player, x1, y1, z1, p1, p2, p3);

					//##########################################################
					//System.out.println("distance; "+x1+" "+y1+" "+z1);
				}
			}
		}
	}

	
	public static void checkAABBColisionRamp(Vector3f A, Vector3f B, Vector3f An, Vector3f Bn, Player player, Entity O)
	{
		Ramp R = O.getR();
		float x1, y1, z1;
		float x2, y2, z2;
		boolean p1,p2,p3;
		if((B.x<An.x)&&(A.x>Bn.x))
		{
			x1 = An.x - B.x;
			x2 = A.x - Bn.x;
			if(x1 > x2)
			{
				p1 = true;
			}else{p1 = false;}
			x1 = Math.min(x1, x2);
		//	System.out.println("1x "+A.x+" "+B.x+" "+An.x+" "+Bn.x+" "+obj[i].getBB().getName());
			//##########################################################
			if((B.y<An.y)&&(A.y>Bn.y))
			{
				y1 = An.y - B.y;
				y2 = A.y - Bn.y;
				if(y1 > y2)
				{
					p2 = true;
				}else{p2 = false;}
				y1 = Math.min(y1, y2);
			//System.out.println("2y "+A.y+" "+B.y+" "+An.y+" "+Bn.y+" "+obj[i].getBB().getName());
			
			//##########################################################
				if((B.z<An.z)&&(A.z>Bn.z))
				{
					z1 = An.z - B.z;
					z2 = A.z - Bn.z;
					if(z1 > z2)
					{
						p3 = true;
					}else{p3 = false;}
					z1 = Math.min(z1, z2);
					//System.out.println("3z "+A.z+" "+B.z+" "+An.z+" "+Bn.z+" "+obj[i].getBB().getName());
					increasePlayerRamp(player, x1, y1, z1, p1, p2, p3, R, O.getPosition().y);

					//##########################################################
					//System.out.println("distance; "+x1+" "+y1+" "+z1);
				}
			}
		}
	}
	
	public static void checkAABBColisionRamp(Vector3f A, Vector3f B, Vector3f An, Vector3f Bn, Player player, Ramp R, float height)
	{
		float x1, y1, z1;
		float x2, y2, z2;
		boolean p1,p2,p3;
		if((B.x<An.x)&&(A.x>Bn.x))
		{
			x1 = An.x - B.x;
			x2 = A.x - Bn.x;
			if(x1 > x2)
			{
				p1 = true;
			}else{p1 = false;}
			x1 = Math.min(x1, x2);
		//	System.out.println("1x "+A.x+" "+B.x+" "+An.x+" "+Bn.x+" "+obj[i].getBB().getName());
			//##########################################################
			if((B.y<An.y)&&(A.y>Bn.y))
			{
				y1 = An.y - B.y;
				y2 = A.y - Bn.y;
				if(y1 > y2)
				{
					p2 = true;
				}else{p2 = false;}
				y1 = Math.min(y1, y2);
			//System.out.println("2y "+A.y+" "+B.y+" "+An.y+" "+Bn.y+" "+obj[i].getBB().getName());
			
			//##########################################################
				if((B.z<An.z)&&(A.z>Bn.z))
				{
					z1 = An.z - B.z;
					z2 = A.z - Bn.z;
					if(z1 > z2)
					{
						p3 = true;
					}else{p3 = false;}
					z1 = Math.min(z1, z2);
					//System.out.println("3z "+A.z+" "+B.z+" "+An.z+" "+Bn.z+" "+obj[i].getBB().getName());
					increasePlayerRamp(player, x1, y1, z1, p1, p2, p3, R, height);

					//##########################################################
					//System.out.println("distance; "+x1+" "+y1+" "+z1);
				}
			}
		}
	}
	public static void checkLowPrecisionTree(Vector3f A, Vector3f B, LowPrecisionTree LPT, Player player)
	{
				LPT.getBB().drawCords();
				BountingBox[] BBox = LPT.getBoxes();
				if(BBox!=null)
				{
				for(int i = 0; i < BBox.length; i++)
				{
					//BBox[i].drawCords();
					if(checkAABB(A, B, BBox[i].getB(), BBox[i].getA()))
					{
						//System.out.println("Sprawdzanie kolizji z "+i);
						BBox[i].drawCords();
					checkAABBColision(A, B, BBox[i].getB(), BBox[i].getA(), player);
					}
				}
				}
				Ramp[] RBox = LPT.getRTab();
				if(RBox!=null)
				{
				for(int i = 0; i < RBox.length; i++)
				{
					RBox[i].drawCords();
					if(checkAABB(A, B, RBox[i].getB(), RBox[i].getA()))
					{
						System.out.println("Sprawdzanie kolizji z "+i);
						RBox[i].drawCords();
					checkAABBColisionRamp(A, B, RBox[i].getB(), RBox[i].getA(), player, RBox[i], 0);
					}
				}
				}
				System.out.println("Pozycja gracza: ");
				player.getBB().drawCords();
	}
	
	public static Vector3f[] generateMaxAABB(Vector3f[] obbb)
	{
		min = null;
		max = null;
		Vector3f[] obb = obbb;
		min.x=max.x=obb[0].x;
		min.y=max.y=obb[0].y;
		min.z=max.z=obb[0].z;
		for (int i=1;i<8;i++)
		{
		       if (obb[i].x < min.x) min.x=obb[i].x;
		       if (obb[i].x > max.x) max.x=obb[i].x;
		       if (obb[i].y < min.y) min.y=obb[i].y;
		       if (obb[i].y > max.y) max.y=obb[i].y;
		       if (obb[i].z < min.z) min.z=obb[i].z;
		       if (obb[i].z > max.z) max.z=obb[i].z;
		}
		Vector3f[] wyn = {min, max};
		return wyn;
	}
	
	public static LowPrecisionTree GenerateLPT(String name, BountingBox BB)
	{
		File plik = new File("res/BB/"+name+".obj");
		Scanner skan = null;
		try {
			skan = new Scanner(plik);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Utworzono Sckaner");
		
		int ilosc = skan.nextInt();
		BountingBox kol[] = new BountingBox[ilosc];
		System.out.println("Iloœæ"+ilosc);
		for(int k = 0; k < ilosc; k++)
		{
			System.out.println("Poczatek P");
			int numer = skan.nextInt();
			skan.nextLine();
			System.out.println("Wczytano numer: "+numer);
			if(numer == 1)
			{
				System.out.println("Tablica");
				Vector3f tab[] = new Vector3f[8];
				for(int j = 0; j < 8; j++)
				{
					System.out.println(j);
					String line = skan.nextLine();
					System.out.println("Nowa linia:"+line);
					String[] aktLinia = line.split(" ");
					
					if(line.startsWith("v "))
					{
						//Tworzy objekt typu wektor3d i zapisuje jako wartoœci xyz trzy kolejne liczby 
						Vector3f vertex = new Vector3f(Float.parseFloat(aktLinia[1]),
								Float.parseFloat(aktLinia[2]),Float.parseFloat(aktLinia[3]));
						//dodaje ten wektor jako nowy wierzcholek
						System.out.println(vertex+" "+j);
						tab[j] = vertex;
					}
				}
				BountingBox b = createBBoxframTab(tab, name);
				System.out.println("Dodano BB ");
				b.drawCords();
				kol[k] = b;
				
			}
			
			System.out.println("Koniec P");
		}
		LowPrecisionTree LPT = new LowPrecisionTree(BB, kol, null);
		return LPT;
	}
}
