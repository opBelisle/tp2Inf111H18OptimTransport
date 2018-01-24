package enginCartes;

/**
 * Cette classe implémente une proposition de carte de liens liant 
 * les villes du problème. 
 * 
 * 
 * Liste des méthodes publiques: 
 *     - Carte, constructeur avec liste vide
 *     - Carte, constructeur à partir de sections de listes
 *     - ajouterLien, méthode permettant d'ajouter un lien
 *     - enleverLien, méthode permettant d'enlever un lien à l'index
 *     - obtientFraction, obtient une fraction de la liste (deep copy)
 *     - getNbLiens, méthode pour obtenir le nombre de liens
 *     - getScore, retourne le score
 *     - evalueScore, calcul le score de la carte
 *     - calculSommeLongueurs, calcul la somme des longeurs
 *     - toString, obtient une représentation chaînes de caractères de l'objet
 *
 * @author Fred Simard | ETS, 
 * @version Hiver 2018
 */

import listeChainee.ListeDChainee;

public class Carte{
    
    // liste chainee
	ListeDChainee listeLiens = new ListeDChainee();
	double score = Double.POSITIVE_INFINITY;
	MoteurDistanceMoyenne moteurDistanceMoyenne;

	/**
	 * Constructeur qui reçoit le moteur de distance et initialise avec une
	 * liste de liens vide
	 * 
	 * @param moteurDistanceMoyenne, reference au moteur de calcul de la distance moyenne
	 */
	public Carte(MoteurDistanceMoyenne moteurDistanceMoyenne){
		this.moteurDistanceMoyenne = moteurDistanceMoyenne;
	}

	/**
	 * Constructeur qui reçoit le moteur de distance et des segments de listes 
	 * pour initialiser la liste
	 * 
	 * @param moteurDistanceMoyenne, reference au moteur de calcul de distance moyenne
	 * @param section1, premiere section de liste
	 * @param section2, deuxieme section de liste
	 */
	public Carte(MoteurDistanceMoyenne moteurDistanceMoyenne, 
			     ListeDChainee section1, 
			     ListeDChainee section2){
		
		// copie la référence au moteur de calcul distance
		this.moteurDistanceMoyenne = moteurDistanceMoyenne;
		// démarre à partir de la premiere section
		listeLiens = section1;
		
		section2.deplacerDebut();
		
		for(int i=0;i<section2.getNbElements();i++){
			listeLiens.ajouterFin(section2.getElement());
			section2.deplacerSuivant();
		}
	}

	/**
	 * Méthode permettant d'ajouter un lien à la fin
	 * 
	 * @param ceLien, lien à ajouter
	 */
	public void ajouterLien(Lien ceLien){
		listeLiens.ajouterFin(ceLien);
	}

	/**
	 * Méthode permettant d'enlever un lien à l'index
	 * 
	 * @param index, index où enlever le lien
	 */
	public void enleverLien(int index){
		listeLiens.enleverIndex(index);
	}
	

	/**
	 * Méthode permettant d'obtenir une fraction de la liste. La fraction est construite 
	 * par deep copy.
	 * 
	 * @param duDebut, boolean indiquant si la section du début ou de la fin doit être retourné
	 * @param indexCoupe, index de coupe
	 * @return Segment de liste selectionné
	 */
    public ListeDChainee obtientFraction(boolean duDebut, int indexCoupe){
        
        // crée une nouvelle liste
    	ListeDChainee listeDuplicat = new ListeDChainee();

        // si du debut
        if(duDebut){
            
            // copie de 0 à l'index
            listeLiens.deplacerDebut();
            listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());
            
            for(int i=0;i<indexCoupe;i++){
                listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());
            }
            
        }else{
            
            // se place à l'index et copie jusqu'à la fin            
            listeLiens.deplacerAIndex(indexCoupe);
            listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());

            while(listeLiens.deplacerSuivant()){
                listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());
            }
            
        }
        
        return listeDuplicat;
    }
    

	/**
	 * Méthode permettant d'obtenir le nombre de liens dans la carte
	 * 
	 * @return nombre de liens dans la carte
	 */
    public int getNbLiens(){
        return listeLiens.getNbElements();
    }    

	/**
	 * Méthode permettant d'obtenir le score de la carte
	 * 
	 * @return le score
	 */
    public double getScore(){
        return score;
    }    


    /**
     * evalue le score de la carte
     * 
     * @param afficher, true si on veut afficher le score en plus de le calculer
     */
    public void evalueScore(boolean afficher){
    	
    	
    	// calcul la distance moyenne entre 2 noeuds
		moteurDistanceMoyenne.calculDistanceMoyenne(listeLiens,afficher);

		double distanceMoyenne = moteurDistanceMoyenne.getDistanceMoyenne();
		
    	// calcul la longeur totales des liens
    	double longueur = calculSommeLongueurs();
    	
    	// calcul le score
    	score = CONFIGURATION.PENALITE_DISTANCE*distanceMoyenne + 
    			CONFIGURATION.PENALITE_LONGUEUR*longueur + 
    			CONFIGURATION.PENALITE_DECONNECTE*moteurDistanceMoyenne.getNbNonConnecte();
    	
    }    
    
    /**
     * Fait le calcul de la somme des longeurs des liens de la carte
     * 
     * @return la longeur totales des liens 
     */
    private double calculSommeLongueurs(){

    	// se déplace au début de la liste de liens
    	double sommeLongueur = 0.0;
    	listeLiens.deplacerDebut();
    	
    	// se déplace vers la fin en additionnant
    	// les longeurs
    	while(listeLiens.deplacerSuivant()){
    		Lien lien = (Lien) listeLiens.getElement();
    		sommeLongueur += lien.getLongueur();
    	}
    	
    	return sommeLongueur;
    }

    /**
     * surdéfinition de la méthode permettant d'obtenir un représentation String de l'objet
     * 
     * return représentation de l'objet sur chaîne de caractères
     */
    public String toString(){
    	String str = new String();
    	str += "Liste des liens\n";
    	listeLiens.deplacerDebut();
    	while(listeLiens.deplacerSuivant()){
    		str += listeLiens.getElement().toString();
    		str += "\n";
    	}
    	str += "Score: " + score + "\n";
    	return str;
    }
    
}
