import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Annee  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4954591113295951560L;
	public int anne = 2012;
	private int jours = 365;
	public List<Semaine> listeSemaine = new LinkedList<Semaine>();
	private int tabN []={31,28,31,30,31,30,31,31,30,31,30,31};
	private int tabB []={31,29,31,30,31,30,31,31,30,31,30,31};
	
	public Annee(int anne){
		this.anne=anne;
		creeSemaine(this.anne);
		
	}
	
	
	public void creeSemaine(int a){
		int buff=0;
		int var = 0;
		int decal = 0;
		int cpt= 0;
		int[] tab = tabN;
		if( anne % 4 == 0 ){
			jours = 366;
			tab=tabB;
		}
		for(int i=0;i<a-2012;i++){
			if(((2012+i) % 4) == 0){
				buff+=366;
			}else{
				buff+=365;
			}
		}
		
		for(int i=1;i<=9;i++){
			if(((buff+i) % 7) == 0){
				decal = i;
				break;
			}
		}
		Semaine s;
		int x=0;
		int v=0;
		int y=0;
		int l=100;
		int h=100;
		for(int i = 0;i<=jours;i+=7){
			if((decal+(i+7))-cpt>tab[var]){
				
				listeSemaine.add(listeSemaine.size(),s = new Semaine(a,var+1,var+2,decal+i-cpt,decal+(i+7)-(cpt+tab[var])));
				
				s.setX(x+var*100+50);
				s.setY(y+400+50);
				v=0;
				cpt+=tab[var];
				var++;
				if(var==12)
					break;
			}else{
				
				listeSemaine.add(s = new Semaine(a,var+1,var+1,decal+i-cpt,decal+(i+7)-cpt));
				s.setX(x+var*100);
				s.setY(y+v*100+50);
				v++;
			}
		}
	}
	public int getPrix(){
		int tot=0;
		for(int i =0;i<listeSemaine.size();i++){
			if(listeSemaine.get(i).occup){
				tot+=listeSemaine.get(i).getPrix();
			}
		}
		return tot;
	}
}
