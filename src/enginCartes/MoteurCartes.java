package enginCartes;

/**
 * Cette classe Implemente le moteur de gestions de la population de cartes candidates
 * 
 * Liste des méthodes publiques: 
 *     - MoteurCartes, constructeur par paramètre
 *     - reduitLaPopulation, opération qui réduit la population à NB_CARTES_BASES
 *     - elargieLaPopulation, opération qui elargie la population à NB_CARTES_MAX
 *     - evalueLesScores, calcul les scores des cartes
 *     - afficheMeilleurSolution, affiche la meilleur solution
 *     - afficheMeilleurScore, affiche le meilleur score
 *     - toString, représentation de l'objet en chaine de caractère
 *
 * @author Fred Simard | ETS, 
 * @version Hiver 2018
 */

import java.util.Random;
import java.util.Vector;

import listeChainee.ListeDChainee;
import problemeVilles.PopulationVilles;
import problemeVilles.Ville;

public class MoteurCartes {

	private PopulationVilles popVilles;
	private Vector<Carte> cartes; // à remplacer par collection Java
	private Random rand = new Random();
	private MoteurDistanceMoyenne moteurDistanceMoyenne;
	
	/**
	 * Constructeur par paramètre
	 * 
	 * @param popVilles, la population de villes
	 */
	public MoteurCartes(PopulationVilles popVilles){
		
		// population de cartes
		cartes = new Vector<Carte>(CONFIGURATION.NB_CARTES_BASES);
		
		// instancie un moteur de distance moyenne
		moteurDistanceMoyenne = new MoteurDistanceMoyenne(popVilles);
		this.popVilles = popVilles;
		
		// crée la population de cartes initiales
		for(int i=0;i<CONFIGURATION.NB_CARTES_BASES;i++){
			
			// crée une nouvelle carte
			Carte temp = new Carte(moteurDistanceMoyenne);

			// selectionne 2 villes différentes au hasard
			for(int j=0;j<popVilles.getNbVille()/2;j++){
				Ville villeA = popVilles.getVille(rand.nextInt(popVilles.getNbVille()));
				Ville villeB = popVilles.getVille(rand.nextInt(popVilles.getNbVille()));
				
				while(villeA == villeB){
					villeB = popVilles.getVille(rand.nextInt(popVilles.getNbVille()));
				}
				
				temp.ajouterLien(new Lien(villeA,villeB));
			}
			
			// ajoute la carte à la liste
			cartes.addElement(temp);
		}
	}
	

	/**
	 * reduit la population de carte en ne gardant que les CONFIGURATION.NB_CARTES_BASES
	 * ayant le plus bas score (minimization). Cette méthode opère sur les champs de la classe
	 */
	public void reduitLaPopulation(){
	
		// crée un nouveau vecteur qui ne contiendra que les meilleurs
		Vector<Carte> meilleurCartes = new Vector<Carte>();
		// ajoute la première carte, puisque seule candidat
		meilleurCartes.add(cartes.remove(0));
		
		// continue tant qu'il reste des cartes à traiter
		while(cartes.size()>0){
			
			// obtient la prochaine carte
			Carte cetteCarte = cartes.remove(0);
			
			// vérifie si à placer au début
			if(cetteCarte.getScore() < meilleurCartes.firstElement().getScore()){
				meilleurCartes.add(0, cetteCarte);

			// vérifie si à placer à la fin
			}else if(cetteCarte.getScore() > meilleurCartes.lastElement().getScore()){
				meilleurCartes.add(cetteCarte);
				
			// sinon trouve sa place
			}else if(cetteCarte.getScore() < meilleurCartes.lastElement().getScore()){
				
				// par de la fin et remonte vers le début, jusqu'à avoir trouvé la place
				int index = (meilleurCartes.size()-1);
				while(cetteCarte.getScore() < meilleurCartes.get(index).getScore()){
					index-=1;
				}

				meilleurCartes.add(index+1, cetteCarte);
			}
			
			// vérifie si on a trop de carte, si c'est le cas, enlève la dernière
			if(meilleurCartes.size() > CONFIGURATION.NB_CARTES_BASES){
				meilleurCartes.remove((meilleurCartes.size()-1));
			}
			
		}

		// remplace la population de cartes par la nouvelle population réduite
		cartes = meilleurCartes;
	
	}

	/**
	 * elargie la population de carte en générant de nouvelles cartes qui sont des mix de cartes
	 * existantes. Cette méthode opère sur les champs de la classe.
	 */
	public void elargieLaPopulation(){

		// elargie la population en générant de nouveaux individus, qui combinent les gênes
		// des parents.
		
		// calcul la somme des scores de tous les parents
		double sommeScore = 0.0;
		
		for(int i=0;i<cartes.size();i++){
			sommeScore += cartes.get(i).getScore();
		}
		
		// pour tous les individues à générer
		for(int i=0;i<(CONFIGURATION.NB_CARTES_MAX-CONFIGURATION.NB_CARTES_BASES);i++){
			
			// selectionne 2 coupes de parents aléatoirement
			ListeDChainee section1 = obtientUneCoupe(sommeScore);
			ListeDChainee section2 = obtientUneCoupe(sommeScore);
			
			// assemble et ajoute le nouvel individu
			cartes.add(new Carte(moteurDistanceMoyenne, section1, section2));
		}
	
	}

	/**
	 * Obtient une section de carte selectionné au hasard parmis la liste de cartes
	 * en proportion de leur score.
	 * 
	 * NOTE: la technique employé est de donner plus de poids aux cartes de bases ayant les plus mauvais
	 * 		 score. Cette approche augmente le mélange des éléments et aide à sortir des minimum locaux
	 * 
	 * NOTE: la technique tend également à favoriser les solutions courtes en applicant un retrait de liens
	 * 
	 * @param sommeScore somme des scores de la population de cartes
	 * @return une section de carte (ListeDChainee)
	 */
	private ListeDChainee obtientUneCoupe(double sommeScore){
		
		int i=0;
		double nbAlea = rand.nextDouble()*sommeScore;
		double accumulationScore = 0;
		Carte courante = null;
		
		// selectionne l'individu en proportion du score
		while(i<CONFIGURATION.NB_CARTES_BASES && accumulationScore<=nbAlea){
			
			courante = cartes.get(i);
			accumulationScore += courante.getScore();
			
			i++;
		}
		
		// obtient une fraction de l'individu
		ListeDChainee section = courante.obtientFraction(rand.nextBoolean(), rand.nextInt(courante.getNbLiens()));

		// applique une mutation si nécessaire
		if(rand.nextDouble() < CONFIGURATION.POURCENTAGE_MUTATION){
			int indexLien = rand.nextInt(section.getNbElements());
			section.deplacerAIndex(indexLien);
			((Lien)section.getElement()).mute(popVilles.getVille(rand.nextInt(popVilles.getNbVille())), rand.nextInt(2));
		}

		// retire un lien pour favoriser solution courtes
		if(rand.nextDouble() < CONFIGURATION.POURCENTAGE_RETRAIT){
			int indexLien = rand.nextInt(section.getNbElements());
			section.enleverIndex(indexLien);
		}
		
		return section;
		
	}
	
	/**
	 * Calcul les scores des cartes
	 */
	public void evalueLesScores(){

		// evalue les scores pour chaque individus
    	for(int i=0;i<cartes.size();i++){
        	//System.out.println("Carte: " + i + "------------------------------------");
    		cartes.get(i).evalueScore(false);
    	}
	}

	/**
	 * Affiche la meilleur solution
	 */
	public void afficheMeilleurSolution(){
		System.out.println("Meilleur score: ");
		System.out.println(cartes.get(0).toString());
		cartes.get(0).evalueScore(true);
	}

	/**
	 * Affiche le meilleur score
	 */
	public void afficheMeilleurScore(){
		System.out.println("Meilleur score: " + cartes.get(0).getScore());
		
	}
	
	/**
	 * Retourne une représentation chaine de caractère de l'objet
	 * 
	 *  @return Chaine de caractère représentant l'objet
	 */
	public String toString(){
    	
		String str = new String();
    	str += "Liste des Cartes\n";
    	
    	// affiche toutes les cartes
    	for(int i=0;i<cartes.size();i++){
    		str += "Carte: " + i + "------------------------------------";
    		str += cartes.get(i).toString();
    		str += "\n";
    	}
    	return str;
	}
	
}
