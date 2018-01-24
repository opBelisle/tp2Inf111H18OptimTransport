package listeChainee;

/**
 * Cette classe implémente une liste doublement chainée. 
 * 
 * Le type de l'élément est abstrait en utilisant la classe Object
 * 
 * Cette liste offre plusieurs méthodes d'accès, tel que ajouter début/fin/à l'index, elle 
 * propose également un itérateur qui permet d'itérer la liste.
 * 
 * Cette classe dépend sur la classe Noeud qui est définit comme une classe privée
 * 
 * Liste des méthodes publiques:
 *     - deplacerDebut, deplace l'itérateur au début
 *     - deplacerFin, deplace l'itérateur à la fin
 *     - deplacerAIndex, deplace l'itérateur à l'index
 *     - deplacerSuivant, deplace l'itérateur au suivant
 *     - deplacerPrecedent, deplace l'itérateur au précédent
 *     - getElement, obtient l'élément à l'itérateur
 *     - ajouterDebut, ajoute un élément au début
 *     - enleverDebut, enleve un élément au début
 *     - ajouterFin, ajoute un élément à la fin
 *     - enleverFin, enleve un élément à la fin
 *     - ajouterIndex, ajoute un élément à l'index
 *     - enleverIndex, enleve un élément à l'index
 *     - getNbElements, obtient le nombre d'élément
 *
 * @author Fred Simard | ETS, 
 * @version Hiver 2018
 */


/**
 * Cette classe privée implémente le noeud d'une liste doublement chainée. 
 * 
 * Le type de l'élément est abstrait en utilisant la classe Object
 * 
 * Liste des méthodes publiques: 
 *     - Noeud, constructeur qui prend un élément
 *     - Noeud, constructeur qui prend un élément, un noeud suivant et un noeud précédent
 *     - setSuivant, definit le noeud suivant
 *     - setPrecedent, definit le noeud precedent
 *     - getSuivant, obtient le noeud suivant
 *     - getPrecedent, obtient le noeud precedent
 *     - getElement, obtient l'élément
 */
class Noeud {
	Object element;
	
	Noeud suivant;
	Noeud precedent;
	
	public Noeud(){}

	/**
	 * Constructeur qui reçoit un élément
	 * 
	 * @param element, élément à attacher au noeud
	 */
	public Noeud(Object element){
		this.element = element;
	}

	/**
	 * Constructeur qui reçoit un élément, un noeud suivant et un noeud précédent
	 * 
	 * @param element, élément à attacher au noeud
	 * @param suivant, le noeud suivant
	 * @param precedent, le noeud précédent
	 */
	public Noeud(Object element, Noeud suivant, Noeud precedent){
		this.element = element;
		this.suivant = suivant;
		this.precedent = precedent;
	}

	/**
	 * Mutateur pour le noeud suivant
	 * 
	 * @param suivant, le nouveau noeud suivant
	 */
	public void setSuivant(Noeud suivant){
		this.suivant = suivant;
	}

	/**
	 * Mutateur pour le noeud precedent
	 * 
	 * @param precedent, le nouveau noeud precedent
	 */
	public void setPrecedent(Noeud precedent){
		this.precedent = precedent;
	}

	/**
	 * Informateur pour le noeud suivant
	 * 
	 * @return le noeud suivant
	 */
	public Noeud getSuivant(){
		return this.suivant;
	}

	/**
	 * Informateur pour le noeud precedent
	 * 
	 * @return le noeud precedent
	 */
	public Noeud getPrecedent(){
		return this.precedent;
	}

	/**
	 * Informateur pour l'élément
	 * 
	 * @return l'élément
	 */
	public Object getElement(){
		return this.element;
	}
	
}

public class ListeDChainee {
	
	Noeud tete = null;
	Noeud queue = null;
	int nbElement = 0;
    
	Noeud courant = null;
	
	/**
	 * Constructeur par défaut
	 */
	public ListeDChainee(){}
	

	/**
	 * deplacerDebut, cette méthode déplace l'itérateur au début
	 */
	public void deplacerDebut(){
		courant = tete;
	}
    

	/**
	 * deplacerDebut, cette méthode déplace l'itérateur à la fin
	 */
	public void deplacerFin(){
		courant = queue;
	}
    

	/**
	 * deplacerDebut, cette méthode déplace l'itérateur à l'index donné
	 * 
	 * @param index, index auquel on veut placer l'itérateur
	 */
	public void deplacerAIndex(int index){
		
		// part de la tête
		courant = tete;
        
		// se déplace du nombre d'index requis
        for(int i=0;i<index;i++){
            courant = courant.getSuivant();
        }
		
	}

	/**
	 * deplacerDebut, cette méthode déplace l'itérateur au suivant (vers la fin)
	 * 
	 * @return retourne true, si le suivant est valide, false sinon
	 */
	public boolean deplacerSuivant(){

		// vérifie si l'itérateur est valide
        if(courant==null){
            return false;
        }

		// obtient le suivant
		courant = courant.getSuivant();

		// vérifie si valide
        if(courant==null){
            return false;
        }
        return true;
		
	}

	/**
	 * deplacerPrecedent, cette méthode déplace l'itérateur sur le précédent (vers le début)
	 * 
	 * @return retourne true, si le précédent est valide, false sinon
	 */
	public boolean deplacerPrecedent(){

		// vérifie si l'itérateur est valide
		if(courant==null){
            return false;
        }
		
		// obtient le précédent
		courant = courant.getPrecedent();
		
		// vérifie si valide
        if(courant==null){
            return false;
        }
        return true;
	}
	
	/**
	 * getElement, cette méthode retourne l'élément attaché à l'itérateur
	 * 
	 * @return retourne true, si le précédent est valide, false sinon
	 */    
	public Object getElement(){

		// vérifie si l'itérateur est valide
		if(courant==null){
            return false;
        }
		
		return courant.getElement();
		
	}


	/**
	 * ajouterDebut, cette méthode ajoute un élément au début
	 * 
	 * @param obj, object à ajouter
	 */ 
	public void ajouterDebut(Object obj){
		
		// crée un Noeud
		Noeud noeudAjouter = new Noeud(obj);
		
		// vérifie si la tête est nulle (liste vide)
		if(tete == null){
			// nouveau noeud est à la fois tête et queue
			tete = noeudAjouter;
			queue = noeudAjouter;
		}else{

			// si tête et noeud pareil (1 seul élément)
			if(tete == queue){
				queue.setPrecedent(noeudAjouter);
			}else{
				tete.setPrecedent(noeudAjouter);
			}
			
			// place en tête
			noeudAjouter.setSuivant(tete);
			tete = noeudAjouter;
		}
		
		// ajoute au compte d'élément
		nbElement++;
		
	}


	/**
	 * enleverDebut, cette méthode enleve un élément au début
	 * 
	 * @return obj, object enlevé
	 */ 
	public Object enleverDebut(){
		
		// objet à retourner
		Object objRetourne = null;
		
		// si vide
		if(tete == null){
			// ne fait rien
		}else{
			
			// si un seul élément
			if(tete == queue){
				queue = null;
			}
			
			// obtient référence sur élément
			objRetourne = tete.getElement();

			// définit tête à suivant
			tete = tete.getSuivant();
			
			// si tête n'est pas null (reste au moins un élément)
			if(tete!=null){
				// précédent à tête est null
				tete.setPrecedent(null);
			}

			// ajuste le compte d'élément
			nbElement--;
		}
		
		// retourne la référence
		return objRetourne;
	}

	/**
	 * ajouterFin, ajoute l'élément à la fin
	 * 
	 * @param obj, l'objet à ajouter
	 */  
	public void ajouterFin(Object obj){
		
		// nouveau noeud
		Noeud noeudAjouter = new Noeud(obj);
		
		// si vide
		if(queue == null){
			// ajoute le seul élément
			queue = noeudAjouter;
			tete = noeudAjouter;
		}else{

			// si un seul élément
			if(tete == queue){
				// ajoute après la tête
				tete.setSuivant(noeudAjouter);
			}else{
				// ajoute après la queue
				queue.setSuivant(noeudAjouter);
			}
			
			// ajoute à la fin
			noeudAjouter.setPrecedent(queue);
			queue = noeudAjouter;
		}

		nbElement++;
	}

	/**
	 * enleverFin, enleve l'élément à la fin
	 * 
	 * @return obj, l'objet enlevé
	 */ 
	public Object enleverFin(){
		
		Object objRetourne = null;
		
		// si vide
		if(tete == null){
			//ne fait rien
		}else{
			// si un seul élément
			if(tete == queue){
				// met tête à vide
				tete = null;
			}
			
			// retire la queue
			objRetourne = queue.getElement();
			queue = queue.getPrecedent();

			// si queue n'est pas vide
			if(queue != null){
				// ajuste le suivant à la queue
				queue.setSuivant(null);
			}
			
			// ajuste le nombre d'élément
			nbElement--;
		}

		return objRetourne;
	}

	/**
	 * ajouterIndex, ajoute l'élément à l'index donnée
	 * Si index est plus grand que le nombre d'élément, ajoute à la fin
	 * 
	 * @param obj, l'objet à ajouter
	 * @param index, l'index où enlever l'élément
	 */ 
	public void ajouterIndex(Object obj, int index){
		
		int i=1;
		Noeud courant;
		Noeud suivant;
		
		// si vide
		if(tete == null){
			// ajoute au début
			ajouterDebut(obj);
		// si un seul élément
		}else if(tete==queue){
			
			// index == 0 ajoute au début
			if(index == 0){
				ajouterDebut(obj);
			// sinon, ajoute à la fin
			}else{
				ajouterFin(obj);
			}
		}else{
			
			// si index est zéro
			if(index == 0){
				// ajoute au début
				ajouterDebut(obj);
			
			// si plus grand que le nombre d'élément
			}else if(index > nbElement){
				// ajoute à la fin
				ajouterFin(obj);
			}else{

				// sinon se déplace à l'index
				Noeud noeudAjoute = new Noeud(obj);
				
				courant = tete;
				suivant = courant.getSuivant();
				
				while(suivant != null && i<index){
					courant = suivant;
					suivant = courant.getSuivant();
					i++;
				}
	
				// raboute la liste
				noeudAjoute.setSuivant(courant.getSuivant());
				noeudAjoute.setPrecedent(courant);
				suivant.setPrecedent(noeudAjoute);
				courant.setSuivant(noeudAjoute);
				
				// ajuste le compte
				nbElement++;
			}
		}
		
	}

	/**
	 * enleverIndex, enleve l'élément à l'index donnée
	 * Si index est plus grand que le nombre d'élément, enleve à la fin
	 * 
	 * @param index, l'index où enlever l'Élément
	 * @return l'objet enlevé
	 */ 
	public Object enleverIndex(int index){
		
		int i=0;
		Noeud courant;
		Noeud suivant;
		Object objRetourne = null;
		
		// si vide
		if(tete == null){
			// ne fait rien
			
		// si un seul élément
		}else if(tete==queue){
			// enlever début
			objRetourne = enleverDebut();
		}else{
			
			// si index = 0
			if(index == 0){
				// enlever début
				objRetourne = enleverDebut();
			// si index >= que fin
			}else if(index >= nbElement-1){
				// enlever à la fin 
				objRetourne = enleverFin();
			}else{
			
				// sinon, se déplace à l'index et enlève
				courant = tete;
				suivant = courant.getSuivant();
				
				while(suivant != null && i<index){
					courant = suivant;
					suivant = courant.getSuivant();
					i++;
				}

				// prend l'élément
				objRetourne = courant.getElement();
				
				// raboute la liste
				courant.getPrecedent().setSuivant(courant.getSuivant());
				courant.getSuivant().setPrecedent(courant.getPrecedent());
				
				// ajute compte d'élément
				nbElement--;
			}
		}
		
		return objRetourne;
	}
    

	/**
	 * getNbElements, informatrice retourne le nombre d'élément dans la liste
	 * 
	 * @return int, le nombre d'élément
	 */ 
    public int getNbElements(){
        
        return nbElement;
    }
	
}
