package collizionEngine;

import org.lwjgl.util.vector.Vector3f;

public class BoxTree 
{
	private BountingBox BB;
	private BoundingModel tab[];
	private ConnectionsTree CT[];
	
	public BoxTree(BountingBox BB, BoundingModel tab[], ConnectionsTree CT[]) 
	{
		this.setBB(BB);
		this.setTab(tab);
		this.setCT(CT);
	}

	public BoundingModel getCollidingBox(Vector3f A, Vector3f B)
	{
		BoundingModel BBB = faundBox(CT, A, B);
		return BBB;
	}
	
	public BoundingModel faundBox(ConnectionsTree[] tree, Vector3f A, Vector3f B)
	{
		//pêtla przeszukj¹ca wszystkie pola tablicy
		for(int i = 0; i < tree.length; i++)
		{
			//Tworzenie BoundingBox
			BountingBox b = tab[tree[i].getNumer()].getBB();
			//Je¿eli BoundingBox koliduje z graczem
			if(CollizionManger.checkAABB(A, B, b.getA(), b.getB()))
			{
				//Je¿eli pole drzewa jest ostatnie to...
				if(tree[i].isLast())
				{
					//zwraca ModelBoxa o numerze drzewa
					return tab[tree[i].getNumer()];
				}
				else
				{
					//jeœli nie to pobiera drzewo z drzewa z listy i sprawdza kolizje
					faundBox(tree[i].getTree(), A, B);
				}
			}
		}
		//Jeœli w ca³ej tablicy nie wykryto kolizji to zwraca null
		return null;
	}
	
	public BoundingModel getBBTabModel(int i)
	{
		return tab[i];
	}
	
	public BountingBox getBB() {
		return BB;
	}

	public void setBB(BountingBox bB) {
		BB = bB;
	}

	public BoundingModel[] getTab() {
		return tab;
	}

	public void setTab(BoundingModel tab[]) {
		this.tab = tab;
	}

	public ConnectionsTree[] getCT() {
		return CT;
	}

	public void setCT(ConnectionsTree cT[]) {
		CT = cT;
	}
	
	
}
