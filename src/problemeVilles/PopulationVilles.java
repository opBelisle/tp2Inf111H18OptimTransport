package problemeVilles;

/**
 * Cette classe est le conteneur de la population de villes. Elle est destiné à
 * être un singleton (aucune mise en oeuvre) et être immuable après 
 * l'initialisation.
 * 
 * Liste des méthodes publiques: 
 *     - PopulationVilles, Constructeur qui reçoit le nombre de villes à créer, 
 *                         et coordonnée X et Y max.
 *     - getVille, Obtient la ville à l'index.
 *     - getNbVille, Obtient le nombre de villes.
 *     - toString, Obtient représentation chaine de caractère de la population 
 *                 de villes.
 *
 * @author Fred Simard | ETS, 
 * 
 * @revision Pierre Bélisle | ETS
 * 
 *              principalement : Mettre le code sur 80 colonne max.
 *                               Aération.
 *                               Correction orthographique. 
 * 
 * @version Hiver 2018
 */

import java.util.Random;

public class PopulationVilles{
    
	private Ville[] popVilles;
    private Random rand = new Random();
    
    /**
     * Constructeur avec paramètres permettant d'initialiser la population 
     * de villes.
     * 
     * @param nbVilles, le nombre de villes à initialiser
     * @param maxX, coordonnée max en X
     * @param maxY, coordonnée max en Y
     */
    public PopulationVilles(int nbVilles, double maxX, double maxY){
        
    	// population de villes maintenu dans un tableau
        popVilles = new Ville[nbVilles];
        
        // initialiuse chacune des villes
        for(int i=0; i<nbVilles; i++){
            
        	// initialise la ville (index i)
            popVilles[i] = new Ville(i, rand.nextDouble() * maxX,
            		                    rand.nextDouble() * maxY);
        }
    }

    /**
     * Méthode permettant d'obtenir la ville à l'index donnée.
     * 
     * @param index, index de la ville à retourner
     * @return référence à la ville à l'index
     */
    public Ville getVille(int index){
        return popVilles[index];
    }

    /**
     * Méthode permettant d'obtenir le nombre de villes.
     * 
     * @return Le nombre de villes définies.
     */
    public int getNbVille(){
        return popVilles.length;
    }

    /**
     * Obtient une représentation String de la population de villes.
     * 
     * @return String représentant la population de villse
     */
    public String toString(){
    	String str = new String();
    	str += "Liste des villes\n";
    	
    	// Enchaine la représentation de chaque villes.
    	for(int i=0;i<popVilles.length;i++){
    		str += popVilles[i];
    		str += "\n";
    	}
    	return str;
    }
    
}
