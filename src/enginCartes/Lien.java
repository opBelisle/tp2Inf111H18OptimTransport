package enginCartes;


/**
 * Cette classe implémente le lien entre 2 villes. Les villes sont référencés à la 
 * population
 * 
 * 
 * Liste des méthodes publiques: 
 *     - Lien, constructeur par paramètres
 *     - Lien, constructeur par copie
 *     - estMembre, determine si tel ville est dans le lien
 *     - getDest, obtient la ville complémentaire du lien
 *     - mute, permet de changer une ville du lien
 *     - getLongueur, obtient longueur du lien
 *     - copie, obtient copie de l'objet
 *     - toString, obtient chaine de caractères
 *
 * @author Fred Simard | ETS, 
 * @version Hiver 2018
 */

import problemeVilles.Ville;

public class Lien{
    
    private Ville[] villesLies = new Ville[2];
    
    /**
     * Constructeur avec paramètres
     * 
     * @param villeA, premiere ville
     * @param villeB, seconde ville
     */
    public Lien(Ville villeA, Ville villeB){
        this.villesLies[0] = villeA;
        this.villesLies[1] = villeB;
    }

    /**
     * Constructeur par copie
     * 
     * @param villeA, premiere ville
     * @param villeB, seconde ville
     */
    public Lien(Lien autreLien){
    	this.villesLies[0] = autreLien.villesLies[0];
    	this.villesLies[1] = autreLien.villesLies[1];
    }
    
    /**
     * Détermine si la ville est membre de l'objet
     * 
     * @param ville, ville candidate
     * @return true si la ville candidate est membre
     */
    public boolean estMembre(Ville ville){
    	
    	// compare les références
    	if(villesLies[0] == ville || villesLies[1] == ville){
    		return true;
    	}
    	return false;
    }

    /**
     * Obtient la ville qui complémente la ville passé en paramètre
     * 
     * @param ville, ville source
     * @return ville destination
     */
    public Ville getDest(Ville ville){
    	
    	if(villesLies[0] == ville){
    		return villesLies[1];
    	
    	}else if(villesLies[1] == ville){
    		return villesLies[0];
    	}
    	
    	return null;
    }
    
    /**
     * Méthode permettant de changer (muter) une ville dans le lien
     * S'assure de ne pas produire un lien qui mène à lui-même
     * 
     * @param nouvelVille, nouvelle ville à ajouter
     * @param position, position {0,1} où placer la ville
     * @return true, si la mutation est accepté
     */
    public boolean mute(Ville nouvelVille, int position){
        
        boolean mutationValide = false;
        
        // valide la position
        if(position == 0 || position == 1){
                
        	// s'assure que la ville n'est pas membre du lien
            if(nouvelVille!=villesLies[0] && nouvelVille!=villesLies[1]){
                villesLies[position] = nouvelVille;
                mutationValide = true;
            }
        }
        
        return mutationValide;
    }
    
    /**
     * Obtient la longeur du lien
     * @return distance entre les villes du lien
     */
    public double getLongueur(){
        return villesLies[0].calculDistanceDe(villesLies[1]);
    }

    /**
     * permet de copier le lien 
     * @return une copie du lien
     */
    public Object copie(){
        return new Lien(this);
    }
    

    /**
     * obtient une représentation chaine de caractère de l'objet
     * @return une chaine de caractère
     */
	public String toString(){
		return new String(villesLies[0] + " lie à " + villesLies[1]);
	}
    
}
