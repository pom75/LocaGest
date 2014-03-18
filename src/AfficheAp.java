import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.Timer;





public class AfficheAp extends JComponent {
	//Varibale Graphique
	private JPanel paneM = new JPanel(new GridLayout(7, 0));
	private JFrame frame ;
	private int L = 1200;
	private int H = 700;
	private Graphics2D g2;
	private Timer t = new Timer(30, new Refresh());
	private int sec =1;
	private String buff;
	private Appart a;
	private Annee an;

	//Menu
	private JMenu Jeu;
	private JMenu Niveaux;
	private JMenuItem SubLvl;
	private JMenuBar menuBar = new JMenuBar(); 
	private JMenuItem NewA = new JMenuItem("Ajouter une anné");
	private JMenu ListeA = new JMenu("Afficher une anné");
	private JMenuItem quit = new JMenuItem("Quitter");
	private JMenuItem editLvl = new JMenuItem("Modifier Une semaine");
	private List<JMenuItem> It = new LinkedList<JMenuItem>(); 
	



	//Variable a enregistré


	public AfficheAp(Appart a){
		//Graphic
		this.a=a;
		frame = new JFrame("appartement "+a.noA);
		frame.setSize(L,H);
		frame.add(this);

		//Menu Déroulant
		Jeu = new JMenu("Appartement");
		Jeu.add(NewA); 
		Jeu.add(new JSeparator()); 
		Jeu.add(ListeA); 
		Jeu.add(new JSeparator()); 
		Jeu.add(quit); 
		menuBar.add(Jeu);
		Niveaux = new JMenu("Semaine");
		SubLvl = new JMenuItem("Ajouter un Client dans une semaine");
		listeReper();
		Niveaux.add(SubLvl); 
		Niveaux.add(new JSeparator()); 
		Niveaux.add(editLvl); 
		menuBar.add(Niveaux);
		//extra
		JMenu Pub = new JMenu("Pub");
		JMenuItem Site = new JMenuItem("Site");
		Pub.add(Site);
		menuBar.add(Pub);

		//Ecouteur
		NewA.addActionListener(new NewA());
		SubLvl.addActionListener(new AjoutC());
		
		frame.addMouseListener((MouseListener) new Click());
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		frame.repaint();
		t.start();
	}

	
	public boolean NomNiveauPresent(String chemin,String s){ 

		File repertoire = new File(chemin); 
		int i; 
		int j =0; 
		String [] listefichiers;
		listefichiers=repertoire.list();


		for(i=0;i<listefichiers.length;i++){ 
			if(listefichiers[i].endsWith(".rtf") || listefichiers[i].endsWith(".txt")){ 
				if(s.equalsIgnoreCase(listefichiers[i])){
					return true;

				}
			}
		}
		return false;
	}

	public void paintComponent(Graphics g){
		
		paint(g);


	}
	
	public void clik(int x, int y){

		for(int i = 0;i< an.listeSemaine.size();i++){
			if(an.listeSemaine.get(i).X<=x && an.listeSemaine.get(i).X+100>=x && an.listeSemaine.get(i).Y<=y && an.listeSemaine.get(i).Y+100>=y){
		
				String buff = JOptionPane.showInputDialog("0 - Modifiacation prix \n1 - Ajout Client \n2 - Modification Semaine \n3 - Information Client\n4 - Libérer la Semaine");
				if(buff.contentEquals("0")){
					buff = JOptionPane.showInputDialog("Veuillez tapez le prix de cette semaine");
					an.listeSemaine.get(i).setPrix(Integer.parseInt(buff));
					try {
						a.ecrire("appartements/appartement "+a.noA+".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(buff.contentEquals("1")){
					an.listeSemaine.get(i).attenteAccompt=true;
					an.listeSemaine.get(i).attenteC=true;
					an.listeSemaine.get(i).occup=false;
					an.listeSemaine.get(i).accompteD=0;
					an.listeSemaine.get(i).c=null;
					an.listeSemaine.get(i).color=Color.green;
					
					String buff1 = JOptionPane.showInputDialog("Entrer le nom du client");
					String buff2 = JOptionPane.showInputDialog("Entrer les information clients");
					String buff3 = JOptionPane.showInputDialog("Entrer le nombre de personnes");
					
					Client c = new Client(buff1,buff2,Integer.parseInt(buff3));
					
					an.listeSemaine.get(i).setC(c);
					an.listeSemaine.get(i).MAJColor();
					try {
						a.ecrire("appartements/appartement "+a.noA+".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(buff.contentEquals("2")){
					String bu = JOptionPane.showInputDialog("1 - Confirmation Réservation \n2 - Modification Versement ");
					if(bu.contentEquals("1")){
						an.listeSemaine.get(i).attenteC=false;
						an.listeSemaine.get(i).MAJColor();
					}else if(bu.contentEquals("2")){
						String bu3 = JOptionPane.showInputDialog("Tapez le montant de l'acompte");
						an.listeSemaine.get(i).attenteC=false;
						an.listeSemaine.get(i).attenteAccompt=false;
						an.listeSemaine.get(i).occup=true;
						an.listeSemaine.get(i).accompteD=(Integer.parseInt(bu3));
						an.listeSemaine.get(i).MAJColor();
					}
					
							
					try {
						a.ecrire("appartements/appartement "+a.noA+".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(buff.contentEquals("3")){
					JOptionPane.showMessageDialog(frame,"Inormation du client "+an.listeSemaine.get(i).c.getNom()+"\n Information : "+an.listeSemaine.get(i).c.getInfo()+"\n Nombre de personnes : "+an.listeSemaine.get(i).c.getNbP()+"\n Acompte versé : "+an.listeSemaine.get(i).accompteD);
				}else if(buff.contentEquals("4")){
					an.listeSemaine.get(i).attenteAccompt=true;
					an.listeSemaine.get(i).attenteC=true;
					an.listeSemaine.get(i).occup=false;
					an.listeSemaine.get(i).accompteD=0;
					an.listeSemaine.get(i).c=null;
					an.listeSemaine.get(i).color=Color.green;
					
					
					
					try {
						a.ecrire("appartements/appartement "+a.noA+".txt");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	
	public void paint(Graphics g){
		int largeur_reelle = getWidth();
		int hauteur_reelle = getHeight();
		
		for(int i = 0;i<an.listeSemaine.size();i++){
			an.listeSemaine.get(i).affiche((Graphics2D) g, largeur_reelle, hauteur_reelle,i);
		}
		
		Graphics2D g2;
		g2 = (Graphics2D) g;
		g2.setColor(Color.gray);
		Rectangle square = new Rectangle(0,0,100,50);
		g2.fill(square);
		g2.setColor(Color.black);
		Rectangle squar = new Rectangle(0,0,100,50);
		g2.drawString("Janvier",30,30);
		g2.draw(squar);
		
		Graphics2D g21;
		g21 = (Graphics2D) g;
		g21.setColor(Color.gray);
		Rectangle square1 = new Rectangle(100,0,100,50);
		g21.fill(square1);
		g21.setColor(Color.black);
		Rectangle squar1 = new Rectangle(100,0,100,50);
		g21.drawString("Fevrier",130,30);
		g21.draw(squar1);
		
		Graphics2D g211;
		g211 = (Graphics2D) g;
		g211.setColor(Color.gray);
		Rectangle square11 = new Rectangle(200,0,100,50);
		g211.fill(square11);
		g211.setColor(Color.black);
		Rectangle squar11 = new Rectangle(200,0,100,50);
		g211.drawString("Mars",230,30);
		g211.draw(squar11);
		
		Graphics2D g2111;
		g2111 = (Graphics2D) g;
		g2111.setColor(Color.gray);
		Rectangle square4 = new Rectangle(300,0,100,50);
		g2111.fill(square4);
		g2111.setColor(Color.black);
		Rectangle squar4 = new Rectangle(300,0,100,50);
		g2111.drawString("Avril",330,30);
		g2111.draw(squar4);
		
		Graphics2D g21111;
		g21111 = (Graphics2D) g;
		g21111.setColor(Color.gray);
		Rectangle square5 = new Rectangle(400,0,100,50);
		g21111.fill(square5);
		g21111.setColor(Color.black);
		Rectangle squar5 = new Rectangle(400,0,100,50);
		g21111.drawString("Mai",430,30);
		g21111.draw(squar5);
		
		Graphics2D g22;
		g22 = (Graphics2D) g;
		g22.setColor(Color.gray);
		Rectangle square6 = new Rectangle(500,0,100,50);
		g22.fill(square6);
		g22.setColor(Color.black);
		Rectangle squar6 = new Rectangle(500,0,100,50);
		g22.drawString("Juin",530,30);
		g22.draw(squar6);
		
		Graphics2D g27;
		g27 = (Graphics2D) g;
		g27.setColor(Color.gray);
		Rectangle square7 = new Rectangle(600,0,100,50);
		g27.fill(square7);
		g27.setColor(Color.black);
		Rectangle squar7 = new Rectangle(600,0,100,50);
		g27.drawString("Juillet",630,30);
		g27.draw(squar7);
		
		Graphics2D g28;
		g28 = (Graphics2D) g;
		g28.setColor(Color.gray);
		Rectangle square8 = new Rectangle(700,0,100,50);
		g28.fill(square8);
		g28.setColor(Color.black);
		Rectangle squar8 = new Rectangle(700,0,100,50);
		g28.drawString("Aout",730,30);
		g28.draw(squar8);
		
		Graphics2D g29;
		g29 = (Graphics2D) g;
		g29.setColor(Color.gray);
		Rectangle square9 = new Rectangle(800,0,100,50);
		g29.fill(square9);
		g29.setColor(Color.black);
		Rectangle squar9 = new Rectangle(800,0,100,50);
		g29.drawString("Septembre",830,30);
		g29.draw(squar9);
		
		Graphics2D g2a;
		g2a = (Graphics2D) g;
		g2a.setColor(Color.gray);
		Rectangle squarea = new Rectangle(900,0,100,50);
		g2a.fill(squarea);
		g2a.setColor(Color.black);
		Rectangle squara = new Rectangle(900,0,100,50);
		g2a.drawString("Octobre",930,30);
		g2a.draw(squara);
		
		Graphics2D g21z;
		g21z = (Graphics2D) g;
		g21z.setColor(Color.gray);
		Rectangle square1z = new Rectangle(1000,0,100,50);
		g21z.fill(square1z);
		g21z.setColor(Color.black);
		Rectangle squar1z = new Rectangle(1000,0,100,50);
		g21z.drawString("Novembre",1030,30);
		g21z.draw(squar1z);
		
		Graphics2D g211q;
		g211q = (Graphics2D) g;
		g211q.setColor(Color.gray);
		Rectangle squareq = new Rectangle(1100,0,100,50);
		g211q.fill(squareq);
		g211q.setColor(Color.black);
		Rectangle squarq = new Rectangle(1100,0,100,50);
		g211q.drawString("Decemnbre",1130,30);
		g211q.draw(squarq);
		
		Graphics2D g211q1;
		g211q1 = (Graphics2D) g;
		g211q1.setColor(Color.green);
		Rectangle squareqe = new Rectangle(0,550,100,100);
		g211q1.fill(squareqe);
		g211q1.setColor(Color.black);
		Rectangle squarqe = new Rectangle(0,550,100,100);
		g211q1.drawString("Semaine Libre",0,600);
		g211q1.draw(squarqe);
		
		Graphics2D g211q11;
		g211q11 = (Graphics2D) g;
		g211q11.setColor(Color.yellow);
		Rectangle squareqed = new Rectangle(100,550,100,100);
		g211q11.fill(squareqed);
		g211q11.setColor(Color.black);
		Rectangle squarqed = new Rectangle(100,550,100,100);
		g211q11.drawString("En attente de",100,600);
		g211q11.draw(squarqed);
		g211q11.drawString("Confirmation",100,630);
		g211q11.draw(squarqed);
		
		Graphics2D g211q111;
		g211q111 = (Graphics2D) g;
		g211q111.setColor(Color.orange);
		Rectangle squareqede = new Rectangle(200,550,100,100);
		g211q111.fill(squareqede);
		g211q111.setColor(Color.black);
		Rectangle squarqede = new Rectangle(200,550,100,100);
		g211q111.drawString("En attente de",200,600);
		g211q111.draw(squarqede);
		g211q111.drawString("Accompte",200,630);
		g211q111.draw(squarqed);
		
		Graphics2D g211q1111;
		g211q1111 = (Graphics2D) g;
		g211q1111.setColor(Color.red);
		Rectangle squareqedee = new Rectangle(300,550,100,100);
		g211q1111.fill(squareqedee);
		g211q1111.setColor(Color.black);
		Rectangle squarqedee = new Rectangle(300,550,100,100);
		g211q1111.drawString("Semaine occupé",300,600);
		g211q1111.draw(squarqedee);
		
		g211q1111.drawString("  Année : "+an.anne,400,600);
		int test= 0;
		for(int i = 0;i<an.listeSemaine.size();i++){
			if(an.listeSemaine.get(i).occup){
				test++;
			}
		}
		g211q1111.drawString("Nombre d'apartement occupé :"+test+"/"+an.listeSemaine.size(),600,560);
		test=0;
		for(int i = 0;i<an.listeSemaine.size();i++){
			if(an.listeSemaine.get(i).getPrix()>an.listeSemaine.get(i).accompteD && an.listeSemaine.get(i).attenteC==false){
				test++;
			}
		}
		g211q1111.drawString("Nombre d'appartement en attente de versement total : "+test+"/"+an.listeSemaine.size(),600,574);
		
		test=0;
		for(int i = 0;i<an.listeSemaine.size();i++){
			
				test+=an.listeSemaine.get(i).accompteD;

		}
		g211q1111.drawString("Argent total récupéré pour l'anné "+an.anne+" : "+test,600,588);
		
	}
	
	private class Refresh implements ActionListener{
		public void actionPerformed(ActionEvent event)
		{	
			
				
			frame.repaint();
				
		}

	}
	private class NewA implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String buff = JOptionPane.showInputDialog("Entré l'année (ex : 2012) ");
			
			a.addAnne(Integer.parseInt(buff));
			ListeA.removeAll();
			listeReper();
			try {
				a.ecrire("appartements/appartement "+a.noA+".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void listeReper(){ 
		int j =0; 
		AllezA allezA = new AllezA();

		for(int i=0;i<a.listeAnne.size();i++){ 
			It.add(j, new JMenuItem(""+a.listeAnne.get(i).anne)); 
			It.get(j).addActionListener(allezA);
			ListeA.add(It.get(j));
			j++;
		}
	}


	private class AllezA implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for(int i = 0;i<a.listeAnne.size();i++){
				if(a.listeAnne.get(i).anne==Integer.parseInt(((AbstractButton) event.getSource()).getText())){
					an=a.listeAnne.get(i);
					
				}
			}
			
		}
	}

	private class AjoutC implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String buff = JOptionPane.showInputDialog("Entrer le numéro de la semaine");
			String buff1 = JOptionPane.showInputDialog("Entrer le nom du client");
			String buff2 = JOptionPane.showInputDialog("Entrer les information clients");
			String buff3 = JOptionPane.showInputDialog("Entrer le nombre de personnes");
			
			Client c = new Client(buff1,buff2,Integer.parseInt(buff3));
			
			an.listeSemaine.get(Integer.parseInt(buff)).setC(c);
			an.listeSemaine.get(Integer.parseInt(buff)).MAJColor();
			try {
				a.ecrire("appartements/appartement "+a.noA+".txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Point p = java.awt.MouseInfo.getPointerInfo().getLocation();
			int Xr = (p.x-frame.getX());
			int Yr = (p.y-frame.getY()-46);
			
			clik(Xr,Yr);
			
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			
		}
	}
}
