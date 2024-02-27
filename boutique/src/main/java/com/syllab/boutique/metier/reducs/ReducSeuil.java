package com.syllab.boutique.metier.reducs;

/**
 * Représente une réduction de type N € de réduction pour minimum d'achat.
 */
public class ReducSeuil implements Reduc {

  /**
   * Initialise une réduction d'un certain montant pour un seuil minimum
   * d'achat.
   * 
   * @param montant Montant de la réduction accordée
   * @param seuil   Seuil d'achat à partir duquel la réduction est appliquée.
   */
  public ReducSeuil(double montant, double seuil) {
    this.montant = montant;
    this.seuil = seuil;
  }

  @Override
  public double getMontantPanier(double prixTotal) {
    return prixTotal >= this.seuil ? this.montant : 0;
  }

  @Override
  public double getMontantLigne(String ref, int qte, double prix) {
    return 0;
  }

  private double montant, seuil;
}
