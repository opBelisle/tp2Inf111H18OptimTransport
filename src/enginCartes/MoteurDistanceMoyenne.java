package enginCartes;

/**
 * Cette classe implémente le moteur de calcul de distance moyenne entre 
 * 2 villes. Elle est un singleton dont la référence est partagée par toutes
 * les cartes.
 * 
 * L'algorithme utilisé consiste à construire, pour chaque ville, un arbre 
 * descendant vers toutes les autres villes en suivant les liens. 
 * 
 * Les noeuds contiennent la distance de la tête.
 * 
 * Quand l'arbre est terminé, la distance de la tête de tous les noeuds est 
 * transféré dans une matrice de distance.
 * 
 * Dans le cas où 2 villes ne sont pas connectées, la matrice de distance garde
 * sa valeur par défaut qui est égal à 0.0.
 * 
 * Prendre note que l'arbre est développé de façon itérative et non pas de
 * façon récursive.
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
	 * Méthode principale qui calcule la distance moyenne pour la liste de liens 
	 * reçue.  Attention, un appel à cette méthode efface la distance 
	 * préalablement calculée.
	 * 
	 * @param listeLiens, Liste des liens de la carte.
	 * @param afficher, Mis à true pour afficher la matrice des distances.
	 */
	public void calculDistanceMoyenne(ListeDChainee listeLiens, boolean afficher){

		// Initialise la matrice de distances.
		double[][] distances = 
				new double[popVilles.getNbVille()][popVilles.getNbVille()];
		
		// À partir de la population de villes
		// pour chaque ville... 
		for(int i=0;i<popVilles.getNbVille();i++){

			ArrayList<Noeud> arbre = construitArbre(listeLiens, i);
			
			// Remplit la ligne de la matrice de distances
			for(int j=0;j<arbre.size();j++){
				
				Noeud ceNoeud = arbre.get(j);		
				distances[i][ceNoeud.getSource().getIndex()] = 
						ceNoeud.getdistanceDeTete();
			}
			
		}
		
		// Affiche matrice de distances si demandée.
		if(afficher){
			afficherMatriceDistance(distances);
		}
		
		// Calcule la distance moyenne et détermine s'il y a des 
		// noeuds non-connecté.
		finaliseCalcul(distances);
	}
	
	/**
	 * Obtient la distance moyenne calculé.
	 * @return distance moyenne
	 */
	public double getDistanceMoyenne(){
		return distanceMoyenne;
	}

	/**
	 * Obtient le nombre de villes non connecté.
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
	 * Développe un noeud de l'arbre. Les noeud suivant sont développé à partir
	 * des liens existant entre la ville source et ses destinations. Seule les
	 * villes par encore dans l'arbre sont développé (arbre).
	 * 
	 * @param arbre, Référence à la liste de tous les noeuds.
	 * @param listeSource, Référence à la liste des sources déjà développés.
	 * @param listeLiens, Liste de tous les liens.
	 * @param prochainNoeud, Index du noeud à développer.
	 */
	private void developpeNoeud(ArrayList<Noeud> arbre, 
			                    ArrayList<Ville> listeSource, 
			                    ListeDChainee listeLiens, 
			                    int noeudADevelopper){
		
		// Part du prochain noeud.
		Noeud noeudCourant = arbre.get(noeudADevelopper);
		Ville villeCourante = noeudCourant.getSource();
		listeLiens.deplacerDebut();
		
		// Passe au travers de toutes la liste des liens.
		while(listeLiens.deplacerSuivant()){
			
			// Obtient une référence sur le lien.
			Lien lien = (Lien)listeLiens.getElement();
			
			// Vérifie si la ville courante est membre du lien.
			if(lien.estMembre(villeCourante)){
				
				// Oui, obtient la destination.
				Ville destination = lien.getDest(villeCourante);
				
				// Ajoute destination au noeud.
				noeudCourant.addDestination(destination);
				
				// Si destination pas dans l'arbre comme source
				if(!listeSource.contains(destination)){
					
					// Ajoute à l'arbre.
					arbre.add(new Noeud(destination, 
							villeCourante.calculDistanceDe(destination) + 
										noeudCourant.getdistanceDeTete(),
							noeudCourant.getniveau()+1));

					// ajoute à la liste des sources développés
					listeSource.add(destination);
					
				}
			}
		}
		
	}
	

	/**
	 * Finalise les calculs en calculant la distance moyenne sur la
	 * matrice des distances et en comptant le nombre de villes non-connectées.
	 * 
	 * @param distances, matrice de distances
	 */
	private void finaliseCalcul(double distances[][]){
		
		distanceMoyenne = 0.0;
		nbDeconnecte = 0;
		
		// Fait la somme des distance de la matrice triangulaire.
		for(int i=1;i<distances.length;i++){
			for(int j=0;j<i;j++){
				
				// Compte les villes non connectées.
				if(distances[i][j]!=0.0){
					distanceMoyenne+=distances[i][j];
				}else{
					nbDeconnecte += 1;
				}
			}
		}
		
		// Calcul de moyenne en divisant par le nombre d'élément (n^2)/2.
		distanceMoyenne /= (distances.length*distances.length -distances.length)/2;
	}
	

	/**
	 * Affiche l'arbre (utilisé en debug).
	 * 
	 * @param arbre, L'arbre de connections.
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
	 * Affiche la matrice de distances.
	 * 
	 * @param distances, Matrice de distances.
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


