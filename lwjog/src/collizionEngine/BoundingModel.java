package collizionEngine;

public class BoundingModel 
{
	private int Numer = 0;
	private BountingBox BB;
	private Ramp R;
	public BoundingModel(int numer, BountingBox bB) 
	{
		setNumer(numer);
		setBB(bB);
	}
	public BoundingModel(int numer, BountingBox bB, Ramp r) 
	{
		setNumer(numer);
		setBB(bB);
		setR(r);
	}
	public int getNumer() {
		return Numer;
	}
	public void setNumer(int numer) {
		Numer = numer;
	}
	public BountingBox getBB() {
		return BB;
	}
	public void setBB(BountingBox bB) {
		BB = bB;
	}
	public Ramp getR() {
		return R;
	}
	public void setR(Ramp r) {
		R = r;
	}
	
}
