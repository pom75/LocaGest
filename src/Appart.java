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


public class Appart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8255897832037547417L;
	public String noA ="";
	public int argentTot= 0;
	public List<Annee> listeAnne = new LinkedList<Annee>();
	
	public Appart(String no){
		noA=no;
		
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
	
	
	public void addAnne(int a){
		listeAnne.add(new Annee(a));
	}
	
	public void supAnne(int a){
		for(int i = 0;i<listeAnne.size();i++){
			if(listeAnne.get(i).anne == a){
				listeAnne.remove(i);
			}
		}
	}

	public void calculTot(){
		
		for(int i=0;i<listeAnne.size();i++){
			argentTot+=listeAnne.get(i).getPrix();
		}
	}
}
