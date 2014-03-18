import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6453274316061243698L;
	private int id = 0;
	private String nom = "";
	private String info = "";
	private int nbP = 0;
	private int acompte = 0 ;
	private static int idT =0;
	//private List<Semaine> historique = new LinkedList<Semaine>(); //Metre le Client de la semaine a null
	
	public Client(String nom,String info,int nbP){
		this.setNom(nom);
		this.setInfo(info);
		this.setNbP(nbP);
		id=idT;
		idT++;
		
	}
	
	public void ecrire(String s) throws IOException{
		try{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(s));
		out.writeObject(this);
		out.close();
		}catch(NotSerializableException e){
			System.out.println(e);
		}catch(NullPointerException e){
			System.out.println(e);
		}
		
	}
	
	public Client lire(String s) throws FileNotFoundException, IOException, ClassNotFoundException{	
		Client n=null;
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(s));
			n = (Client) in.readObject();
			in.close();
			}catch(NotSerializableException e){
				System.out.println(e);
			}
		return n;
			
	}

	public int getAcompte() {
		return acompte;
	}

	public void setAcompte(int acompte) {
		this.acompte = acompte;
	}

	public int getNbP() {
		return nbP;
	}

	public void setNbP(int nbP) {
		this.nbP = nbP;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
