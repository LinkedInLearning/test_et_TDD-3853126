package com.syllab.boutique.metier.reducs;

/**
 * Représente une réduction de type 1 offert pour N achetés.
 */
public class ProduitOffert implements Reduc {
  /**
   * Initialise une réduction de type 1 offert pour N achetés.
   * 
   * @param ref Référence du produit concerné par la réduction.
   * @param qte Quantité de produits commandés (produit offert compris) 
   */
  public ProduitOffert(String ref, int qtePayante) {
    if (ref.length() == 0) {
      throw new IllegalArgumentException("La référence doit être non vide");
    }
    if (qtePayante <= 0) {
      throw new IllegalArgumentException("La quantité doit être strictement positive");
    }
    this.ref = ref;
    this.qte = qtePayante;
  }

  @Override
  public double getMontantPanier(double prixTotal) {
    return 0;
  }

  @Override
  public double getMontantLigne(String ref, int qte, double prix) {
    return ref.equals(this.ref) ? (qte / this.qte) * prix : 0;
  }

  private String ref;
  private int qte;
}
