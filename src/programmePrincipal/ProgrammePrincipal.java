package programmePrincipal;

/**
 * Cette classe 
 * Liste des méthodes publiques: 
 *     - main (static), programme principal
 *
 * @author Fred Simard | ETS, 
 * @version Hiver 2018
 */

import problemeVilles.PopulationVilles;
import enginCartes.MoteurCartes;
import enginCartes.CONFIGURATION;

public class ProgrammePrincipal{
    
	/**
	 * Développe le problème, exécute la méthode de résolution et
	 * affiche le résultat
	 * 
	 * @param args, inutilisé
	 */
    public static void main(String[] args){
        
        // crée une population de villes
    	PopulationVilles popVilles = 
    			new PopulationVilles(CONFIGURATION.NOMBRE_VILLES, 
    					CONFIGURATION.MAX_X, CONFIGURATION.MAX_Y);
        
        // crée une population de génomes
        MoteurCartes enginCartes = new MoteurCartes(popVilles);
        
        System.out.println(popVilles);
        
        // évalue les scores une première fois
    	enginCartes.evalueLesScores();
        
        // maintenant, on procède à la boucle d'optimization 
    	// pour trouver la solution       
        for(int i=0;i<CONFIGURATION.NB_ITERATIONS;i++)
        {
        	// affiche l'itération courante
        	System.out.println("" + i);
        	
        	// itération d'optimisatioargsn: élargi, évalue, réduit
        	enginCartes.elargieLaPopulation();
        	enginCartes.evalueLesScores();
        	enginCartes.reduitLaPopulation();
        	
        	// affiche le meilleur score courant
        	enginCartes.afficheMeilleurScore();
            
        }

        // affiche la population des villes 
        System.out.println(popVilles);
        
        // affiche la meilleur solution
        enginCartes.afficheMeilleurSolution();
    }
    
}

