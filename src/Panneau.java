
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Timer;



public class Panneau extends JComponent {

	//Variable Lecture Niveau
	private BufferedReader reader;
	private int Lbrique = 100;
	private int Hbrique = 30;
	private String ligne;

	//Varibale Graphique
	private JFrame frame = new JFrame("LocaGest V1 (By Stephane Ferreira)");
	private JLabel MeilleurScoreL = new JLabel("Meilleur score : ");
	private JLabel vieL = new JLabel("Nombre de vies : ");
	private JLabel ptsActuel = new JLabel("Score : ");
	//Menu
	private JMenu Jeu;
	private JMenu Niveaux;
	private JMenu SubLvl;
	private JMenuBar menuBar = new JMenuBar(); 
	private JMenuItem NewA = new JMenuItem("Nouvel appartement");
	private JMenu ListeA = new JMenu("Mes appartements");
	private JMenuItem quit = new JMenuItem("Quitter");
	private JMenuItem editLvl = new JMenuItem("Editeur de Niveau");
	
	private List<JMenuItem> It = new LinkedList<JMenuItem>(); 
	private int L = 1000;
	private int H = 1000;
	private Graphics2D g2;


	//Variables diverses
	public Timer t = new Timer(30, new Refresh());

	//Variables Partie 
	private List<String> listefichiers  = new LinkedList<String>(); // Nom de tous les niveaux


	//Constructeur
	public Panneau() throws IOException{

		//Graphic
		frame.setSize(L,H);
		frame.addWindowListener(new Close());
		frame.add(this);


		//Menu DŽroulant
		Jeu = new JMenu("Appartements");
		Jeu.add(NewA); 
		Jeu.add(new JSeparator()); 
		Jeu.add(ListeA); 
		Jeu.add(new JSeparator()); 
		Jeu.add(quit); 
		menuBar.add(Jeu);
		Niveaux = new JMenu("Clients");
		SubLvl = new JMenu("Jouer un Niveau");
		this.listeReper("appartements");
		Niveaux.add(SubLvl); 
		Niveaux.add(new JSeparator()); 
		Niveaux.add(editLvl); 
		menuBar.add(Niveaux);
		//extra
		JMenu Pub = new JMenu("Pub");
		JMenuItem Site = new JMenuItem("Site");
		Pub.add(Site);
		menuBar.add(Pub);
		//Ecouteurs
		Site.addActionListener(new Pub());
		NewA.addActionListener(new NewA());
		ListeA.addActionListener(new ListeApp());
		quit.addActionListener(new Ferme());
		editLvl.addActionListener(new EditLvl());



		//affichage Graphique frame
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		frame.addMouseListener(new Start());
	}
	
	// Lecteur RŽpertoir Menu Niveaux
		public void listeReper(String chemin){ 

			File repertoire = new File(chemin); 
			int i; 
			int j =0; 
			AllezA allezA = new AllezA();
			String [] listefichier;
			listefichier=repertoire.list();


			for(i=0;i<listefichier.length;i++){ 
				if(listefichier[i].endsWith(".rtf") || listefichier[i].endsWith(".txt")){ 
					this.listefichiers.add(listefichier[i]);
					It.add(j, new JMenuItem(listefichier[i])); 
					It.get(j).addActionListener(allezA);
					ListeA.add(It.get(j));
					j++;
				}
			}
		}

		public Appart lire(String s) throws FileNotFoundException, IOException, ClassNotFoundException{	
			Appart n=null;
			try{
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(s));
				n = (Appart) in.readObject();
				in.close();
			}catch(NotSerializableException e){
				System.out.println(e);
			}
			return n;

		}


		//Refresh de l'image + Gestion NIveau
		private class Refresh implements ActionListener{
			public void actionPerformed(ActionEvent event)
			{	
				frame.repaint();	

			}



		}



		// Fermeture de la fentre
		private class Close extends WindowAdapter { 
			public void windowClosing(WindowEvent event) {
				System.exit(0); 
			}
		}

		// Mousses Listener


		class Start extends MouseAdapter{

			public void mouseClicked(MouseEvent event){ 
				if(! t.isRunning()){
					t.start();
				}
			}
		}
	private class AllezA implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				//On recupere le numŽro du LVl
					Appart a=null;
					try {
						AfficheAp app = new AfficheAp(lire("appartements/"+((JMenuItem)event.getSource()).getText()));
					} catch (FileNotFoundException e) {
						
					} catch (IOException e) {
						
					} catch (ClassNotFoundException e) {
						
					}finally {
						
					}
					
				
					
			
		}
	}
	private class Pub implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				Desktop d = Desktop.getDesktop();
				d.browse(new URI("http://SooZig.com"));

			} catch ( URISyntaxException e ) {
				e.printStackTrace();

			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}
	private class Ferme implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
	private class NewA implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int y = 0;
			String buff = JOptionPane.showInputDialog("EntrŽ le numŽreau de l'appartement ");
			for(int i = 0;i<listefichiers.size();i++){
				if(listefichiers.get(i).contentEquals("appartement "+buff+".txt")){
				y = 1;
				}
			}

			if(y==0){
				Appart a = new Appart(buff);
				try {


					a.ecrire("appartements/appartement "+a.noA+".txt");
					ListeA.removeAll();
					listeReper("appartements");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(frame,"L'appartmeent est dŽja existant. Veuillez le suprimer pour le re crŽer");
			}
		}

	}
	private class ListeApp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
		
		}
	}
	private class EditLvl implements ActionListener {


		public void actionPerformed(ActionEvent event) {

		}
	}
}