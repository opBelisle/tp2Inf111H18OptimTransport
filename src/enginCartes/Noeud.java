package enginCartes;

import java.util.ArrayList;

import problemeVilles.Ville;

/**
 * Cette classe implémente un Noeud de l'arbre.
 * 
 * Chaque noeud contient la ville du noeud (source) de même qu'une liste
 * de toutes les villes destination auxquelles la source est connectée.
 * 
 * Chaque noeud contient également sa distance par rapport à la ville tête.
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
public class Noeud{
	
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
