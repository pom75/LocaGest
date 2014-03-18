import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;



public class Semaine extends ElementAff  implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = 6819984918218988370L;
public int anne;
 public int moisD;
 public int moisF;
 public int jourD;
 public int jourF;
 
 
 public Color color = Color.green;
 
 private int prix;
 public int accompteD=0;
 public boolean occup = false;
 public boolean attenteC = true;
 public boolean attenteAccompt = true; 
 public Client c = null;

 
 
 public Semaine (int anne,int moisD,int moisF,int jourD,int jourF){
	 this.anne=anne;
	 this.moisD=moisD;
	 this.moisF=moisF;
	 this.jourD=jourD;
	 this.jourF=jourF;
	 
	 
 }

 public void MAJColor(){
	 if(attenteC){
		 color=Color.yellow;		 
	 }else if(attenteAccompt){
		 color=Color.orange;
	 }else if(occup){
		 color=Color.red;
	 }
 }
 
public int getPrix() {
	return prix;
}

public void setPrix(int prix) {
	this.prix = prix;
}

public boolean isOccup() {
	return occup;
}

public void setOccup(boolean occup) {
	this.occup = occup;
}

public boolean isAttenteC() {
	return attenteC;
}

public void setAttenteC(boolean attenteC) {
	this.attenteC = attenteC;
}

public boolean isAccompt() {
	return attenteAccompt;
}

public void setAccompt(boolean accompt) {
	this.attenteAccompt = accompt;
}

public Client getC() {
	return c;
}

public void setC(Client c) {
	this.c = c;
}
public Color getColor() {
	return color;
}
public void setColor(Color color) {
	this.color = color;
}

void affiche(Graphics2D g, int largeur_reelle, int hauteur_reelle,int i) {
	Graphics2D g2;
	g2 = (Graphics2D) g;
	g2.setColor(getColor());
	Rectangle square = new Rectangle(getX(),getY(),getL(),getH());
	g2.fill(square);
	g2.setColor(Color.black);
	Rectangle squar = new Rectangle(getX(),getY(),getL(),getH());
	g2.draw(squar);

	g2.drawString(""+jourD,X+5, Y+12);
	if(moisD != moisF){
		g2.drawString(""+jourF,X+90, Y+12);
	}else{
		g2.drawString(""+jourF,X+5, Y+30);
	}
	g2.setColor(Color.red);
	g2.drawString(""+(i+2),X+50, Y+50);
	g2.setColor(Color.black);
	g2.drawString("Prix :"+prix,X+10, Y+63);
	g2.setColor(Color.black);
	if(c!=null){
	g2.drawString("Versé:"+this.accompteD,X, Y+75);
	g2.drawString("Solde:"+(prix-this.accompteD),X, Y+89);
	g2.drawString(c.getNom(),X, Y+50);
	}
	
	
	
	
	

}
}
