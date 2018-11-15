package collizionEngine;

public class ConnectionsTree 
{
	private int numer;
	private boolean last;
	private ConnectionsTree tree[];
	
	public ConnectionsTree(ConnectionsTree tree[], boolean last, int numer) 
	{
		this.setTree(tree);
		this.setLast(last);
		this.setNumer(numer);
	}
	public int getNumer() {
		return numer;
	}
	public void setNumer(int numer) {
		this.numer = numer;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public ConnectionsTree[] getTree() {
		return tree;
	}
	public void setTree(ConnectionsTree tree[]) {
		this.tree = tree;
	}
}
