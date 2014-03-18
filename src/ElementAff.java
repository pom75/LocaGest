import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;


public abstract class ElementAff implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1922986839960547821L;
	protected int X;
	protected int Y;
	protected int H=100;
	protected int L=100;

	
	
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	public int getL() {
		return L;
	}
	public void setL(int l) {
		L = l;
	}

	
	


}