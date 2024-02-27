package com.syllab.boutique.metier.reducs;

/**
 * Définit les fonctionnalités clés d'une réduction.
 */
public interface Reduc {
  /**
   * Calcule le montant de la réduction pour l'ensemble du panier si il est
   * éligible à la réduction.
   * 
   * @param panier Panier concerné par la réduction
   * @return Montant de la réduction, 0 si le panier ne remplit pas les
   *         conditions.
   */
  double getMontantPanier(double prixTotal);

  /**
   * Calcule le montant de la réduction pour la ligne du panier si elle est
   * éligible à la réduction.
   * 
   * @param panier Ligne de panier concernée par la réduction
   * @return Montant de la réduction, 0 si la ligne du panier ne remplit pas
   *         les conditions.
   */
  double getMontantLigne(String ref, int qte, double prix);
}
