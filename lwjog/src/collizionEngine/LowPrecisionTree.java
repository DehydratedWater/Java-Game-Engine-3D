package collizionEngine;

import org.lwjgl.util.vector.Vector3f;

public class LowPrecisionTree 
{
	BountingBox BB;
	BountingBox[] tab;
	Ramp[] RTab;
	
	public LowPrecisionTree(BountingBox bB, BountingBox[] Tab, Ramp[] rr) 
	{
		BB = bB;
		tab = Tab;
		RTab = rr;
	}
	
	public void MoveTree(Vector3f d)
	{
		//System.out.println("Przemieszczanie BBT");
		BB.move(d);
		if(tab!=null)
		{
		for(int i = 0; i < tab.length; i++)
		{
			tab[i].move(d);
			tab[i].drawCords();
		}
		}
		if(RTab!=null)
		{
		for(int i = 0; i < RTab.length; i++)
		{
			RTab[i].move(d);
			RTab[i].drawCords();
		}
	}
		//BB.drawCords();
	}
	
	public BountingBox[] getBoxes()
	{
		return tab;
	}
	
	
	public BountingBox getBB() {
		return BB;
	}

	public void setBB(BountingBox bB) {
		BB = bB;
	}

	public BountingBox[] getTab() {
		return tab;
	}

	public void setTab(BountingBox[] tab) {
		this.tab = tab;
	}
	public Ramp[] getRTab() {
		return RTab;
	}

	public void setRTab(Ramp[] rTab) {
		RTab = rTab;
	}
}
