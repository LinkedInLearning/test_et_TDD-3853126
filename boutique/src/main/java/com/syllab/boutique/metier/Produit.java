package com.syllab.boutique.metier;

/**
 * Représente un produit avec ses informations.
 */
public class Produit implements Cloneable {
  /**
   * Initialise un produit avec les informations fournies.
   * 
   * @param reference Référence unique du produit.
   * @param libelle   Libellé du produit
   * @param prix      Prix de vente du produit
   * @exception IllegalArgumentException Paramètre non valide (chaîne vide ou prix
   *                                     négatif)
   * @exception NullPointerException     Référence ou libellé nul.
   */
  public Produit(
      String reference,
      String libelle,
      double prix) {
    if (reference.length() == 0 || libelle.length() == 0) {
      throw new IllegalArgumentException(
          "La référence et le libellés doivent être non vides.");
    }
    if (prix <= .0) {
      throw new IllegalArgumentException(
          "Le prix doit être positif.");
    }
    this.reference = reference;
    this.libelle = libelle;
    this.prix = prix;
  }

  /**
   * Obtient la référence unique du produit.
   * 
   * @return Référence unique du produit.
   */
  public String getReference() {
    return this.reference;
  }

  /**
   * Obtient le libellé du produit.
   * 
   * @return Libellé du produit.
   */
  public String getLibelle() {
    return this.libelle;
  }

  /**
   * Obtient le prix de vente du produit.
   * 
   * @return Prix du produit.
   */
  public double getPrix() {
    return this.prix;
  }

  /**
   * Détermine une valeur unique pour un produit de référence donnée.
   * 
   * @return Hachage de la référence du produit.
   */
  @Override
  public int hashCode() {
    return this.reference.hashCode();
  }

  /**
   * Vérifie l'égalité avec une autre instance de produit d'après sa référence.
   * 
   * @return true si l'autre instance possède la même référence, false sinon.
   */
  @Override
  public boolean equals(Object o) {
    return o instanceof Produit
        && this.reference == ((Produit) o).reference;
  }

  /**
   * Formatte une représentation du produit sous forme de chaîne de caractères.
   * 
   * @return Chaîne caractérisant le produit.
   */
  @Override
  public String toString() {
    return String.format(
        "[%s] %s",
        this.reference,
        this.libelle);
  }

  /**
   * Crée un produit possédant les même caractéristiques que l'instance courante.
   * 
   * @return Instance de Produit identique à l'instance courante.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return new Produit(this.reference, this.libelle, this.prix);
  }

  private String reference, libelle;
  private double prix;
}
