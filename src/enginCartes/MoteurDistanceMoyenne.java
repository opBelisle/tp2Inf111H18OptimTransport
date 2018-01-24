package enginCartes;

/**
 * Cette classe implémente le moteur de calcul de distance moyenne entre 2 villes. Elle est un singleton
 * dont la référence est partagé par toutes les cartes.
 * 
 * L'algorithme utilisé consiste à construire, pour chaque ville, un arbre descendant vers
 * toutes les autres villes en suivant les liens. Les noeuds contiennent la distance de la tête.
 * 
 * Quand l'arbre est terminé, la distance de la tête de tous les noeuds est transféré dans une matrice de distance.
 * Dans le cas où 2 villes ne sont pas connecté, la matrice de distance garde sa valeur par défaut qui est égal à 0.0
 * 
 * Prendre note que l'arbre est développé de façon itérative et non pas de façon récursive.
 * 
 * Prendre note que les Noeuds de l'arbre est définit comme une classe locale
 * 
 * Liste des méthodes publiques: 
 * 		- MoteurDistanceMoyenne
 * 		- calculDistanceMoyenne
 * 		- getDistanceMoyenne
 * 		- getNbNonConnecte
 *
 * @author Fred Simard | ETS, 
 * @version Hiver 2018
 */

import problemeVilles.PopulationVilles;
import problemeVilles.Ville;

import java.util.ArrayList;

import listeChainee.ListeDChainee;


/**
 * Cette classe privée implémente un Noeud de l'arbre
 * Chaque noeud contient la ville du noeud (source) de même qu'une liste
 * de toutes les villes destination auxquel la source est connecté.
 * 
 * Chaque noeud contient également sa distance par rapport à la ville tête
 * 
 * Liste des méthodes publiques:
 * 	- Noeud, constructeur avec paramètre
 *  - getSource, obtient la ville du noeud
 *  - getdistanceDeTete, obtient la distance depuis la tête
 *  - getniveau, obtient le niveau de l'arbre ou se trouve le noeud
 *  - addDestination, ajoute une destination au noeud
 *  - getNbDestinations, obtient le nombre de destinations
 *  - getDestination, obtient une destination donnée
 *  - toString, obtient une représentation en chaine de caractères du noeud
 */
class Noeud{
	
	Ville source;
	int niveau = 0;
	double distanceDeTete;
	ArrayList<Ville> destinations = new ArrayList<Ville>();
	
	/**
	 * Constructeur avec paramètres
	 * 
	 * @param source, ville du noeud
	 * @param distanceDeTete, distance accumulé depuis la tête
	 * @param niveau, niveau de l'arbre
	 */
	public Noeud(Ville source, double distanceDeTete, int niveau){
		this.source = source;
		this.distanceDeTete = distanceDeTete;
		this.niveau = niveau;
	}

	/**
	 * Obtient la ville source
	 * @return référence sur ville source
	 */
	public Ville getSource(){
		return this.source;
	}

	/**
	 * Obtient la distance de la tête 
	 * @return distance de la tête
	 */
	public double getdistanceDeTete(){
		return this.distanceDeTete;
	}

	/**
	 * Obtient le niveau du noeud
	 * @return niveau du noeud
	 */
	public int getniveau(){
		return this.niveau;
	}

	/**
	 * Ajoute une destination au noeud
	 * @return destination, ville destination
	 */
	public void addDestination(Ville destination){
		this.destinations.add(destination);
	}

	/**
	 * getNbDestinations
	 * @return obtient le nombre de destination du noeud
	 */
	public int getNbDestinations(){
		return this.destinations.size();
	}

	/**
	 * obtient la destination à l'index
	 * @param index, index de la destination voulu
	 * @return ville destination
	 */
	public Ville getDestination(int index){
		return this.destinations.get(index);
	}

	/**
	 * Obtient représentation de l'objet en chaine de caractère
	 * @return chaine de caractère
	 */
	public String toString(){
		String chaine = new String("Source: " + this.source.toString() + "\n");
		chaine += "niveau: " + this.niveau + "\n";
		chaine += "distance: " + this.distanceDeTete + "\n";
		return chaine;
	}
}

public class MoteurDistanceMoyenne {

	PopulationVilles popVilles;
	ListeDChainee listeGenes;
	
	double distanceMoyenne = 0.0;
	int nbDeconnecte = 0;
	
	/**
	 * Constructeur par paramètre
	 * @param popVilles, liste des villes
	 */
	public MoteurDistanceMoyenne(PopulationVilles popVilles){
		this.popVilles = popVilles;
	}
	
	/**
	 * Méthode principale, calcule la distance moyenne pour la liste de lien reçu
	 * Attention, un appel à cette méthode efface la distance préalablement calculé
	 * @param listeLiens, liste des liens de la carte
	 * @param afficher, true pour afficher la matrice des distances
	 */
	public void calculDistanceMoyenne(ListeDChainee listeLiens, boolean afficher){

		// initialise la matrice de distances
		double[][] distances = new double[popVilles.getNbVille()][popVilles.getNbVille()];
		
		// à partir de la population de villes
		// pour chaque ville... 
		for(int i=0;i<popVilles.getNbVille();i++){

			ArrayList<Noeud> arbre = construitArbre(listeLiens, i);
			
			// rempli la ligne de la matrice de distances
			for(int j=0;j<arbre.size();j++){
				Noeud ceNoeud = arbre.get(j);		
				distances[i][ceNoeud.getSource().getIndex()] = ceNoeud.getdistanceDeTete();
			}
			
		}
		
		// affiche matrice de distances si demandé
		if(afficher){
			afficherMatriceDistance(distances);
		}
		
		// calcule la distance moyenne et détermine s'il y a des noeuds non-connecté
		finaliseCalcul(distances);
	}
	
	/**
	 * obtient la distance moyenne calculé
	 * @return distance moyenne
	 */
	public double getDistanceMoyenne(){
		return distanceMoyenne;
	}

	/**
	 * obtient le nombre de villes non connecté
	 * @return nombre de non-connecté
	 */
	public int getNbNonConnecte(){
		return nbDeconnecte;
	}
	
	/**
	 * Construit l'arbre pour la ville i
	 * 
	 * @param listeLiens, liste des liens de la carte
	 * @param i, index de la ville, pour laquelle on développe l'arbre
	 * @return Liste de noeuds, contenant l'arbre
	 */
	private ArrayList<Noeud> construitArbre(ListeDChainee listeLiens, int i){

		ArrayList<Noeud> arbre = new ArrayList<Noeud>();
		ArrayList<Ville> listeSource = new ArrayList<Ville>();
		
		// cette ville est notre premier noeud
		arbre.add(new Noeud(popVilles.getVille(i),0.0,0));
		listeSource.add(popVilles.getVille(i));
		
		int prochainNoeud = 0;
	
		// tant qu'il reste au moins 1 noeud à développer
		while(arbre.size() > prochainNoeud){

			developpeNoeud(arbre, listeSource, listeLiens, prochainNoeud);
			prochainNoeud += 1;
			
		}
	
		// Debug affiche l'arbre
		//afficherArbre(arbre);
		
		return arbre;
		
	}

	/**
	 * Développe un noeud de l'arbre. Les noeud suivant sont développé à partir des liens
	 * existant entre la ville source et ses destinations. Seule les villes par encore
	 * dans l'arbre sont développé (arbre)
	 * 
	 * @param arbre, référence à la liste de tous les noeuds
	 * @param listeSource, référence à la liste des source déjà développé
	 * @param listeLiens, liste de tous les liens
	 * @param prochainNoeud, index du noeud à développer
	 */
	private void developpeNoeud(ArrayList<Noeud> arbre, 
			                    ArrayList<Ville> listeSource, 
			                    ListeDChainee listeLiens, 
			                    int noeudADevelopper){
		
		//part du prochain noeud
		Noeud noeudCourant = arbre.get(noeudADevelopper);
		Ville villeCourante = noeudCourant.getSource();
		listeLiens.deplacerDebut();
		
		// passe au travers de toutes la liste des liens
		while(listeLiens.deplacerSuivant()){
			
			// obtient une référence sur le lien
			Lien lien = (Lien)listeLiens.getElement();
			
			// vérifie si la ville courante est membre du lien
			if(lien.estMembre(villeCourante)){
				
				// oui, obtient la destination
				Ville destination = lien.getDest(villeCourante);
				
				// ajoute destination au noeud
				noeudCourant.addDestination(destination);
				
				// si destination pas dans l'arbre comme source
				if(!listeSource.contains(destination)){
					
					// ajoute à l'arbre
					arbre.add(new Noeud(destination, 
							villeCourante.calculDistanceDe(destination) + noeudCourant.getdistanceDeTete(),
							noeudCourant.getniveau()+1));

					// ajoute à la liste des sources développés
					listeSource.add(destination);
					
				}
			}
		}
		
	}
	

	/**
	 * finalise les calculs en calculant la distance moyenne sur la matrice des distances
	 * et en comptant le nombre de villes non-connecté
	 * @param distances, matrice de distances
	 */
	private void finaliseCalcul(double distances[][]){
		
		distanceMoyenne = 0.0;
		nbDeconnecte = 0;
		
		// fait la somme des distance de la matrice triangulaire
		for(int i=1;i<distances.length;i++){
			for(int j=0;j<i;j++){
				
				// compte les villes non connecté
				if(distances[i][j]!=0.0){
					distanceMoyenne+=distances[i][j];
				}else{
					nbDeconnecte += 1;
				}
			}
		}
		
		// calcul de moyenne en divisant par le nombre d'élément (n^2)/2
		distanceMoyenne /= (distances.length*distances.length -distances.length)/2;
	}
	

	/**
	 * Affiche l'arbre (utilisé en debug)
	 * @param arbre, arbre de connections
	 */
	private void afficherArbre(ArrayList<Noeud> arbre){
	
		for(int i=0;i<arbre.size();i++){
			Noeud ceNoeud = arbre.get(i);
			System.out.println("");
			System.out.println("Noeud: " + i);
			System.out.println(ceNoeud.toString());
		}
		
	}

	/**
	 * Affiche la matrice de distances
	 * @param distances, matrice de distances
	 */
	private void afficherMatriceDistance(double distances[][]){
	
		for(int i=0;i<distances.length;i++){
			for(int j=0;j<i;j++){
				System.out.printf("%.3f\t",distances[i][j]);
			}
			System.out.println("");
		}
		
	}
	
}
