package com.syllab.boutique.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syllab.boutique.metier.reducs.Reduc;

/**
 * Représente un panier de site marchand.
 */
public class Panier {

  /**
   * Applique un coupon de réduction à la commande.
   * 
   * @param coupon Code du coupon.
   */
  public void appliquerReduction(String coupon) {
    var reduc = coupons.get(coupon);

    if (reduc != null) {
      this.reducs.add(reduc);
    }
  }

  /**
   * Obtient les lignes du panier selon un ordre arbitraire.
   * 
   * @return Itérateur sur les lignes du panier.
   */
  public Iterable<Ligne> getLignes() {
    return this.lignes.values();
  }

  /**
   * Ajoute une quantité d'un produit au panier. Si le produit est déjà
   * présent, sa quantité est modifiée, sinon une ligne est ajoutée.
   * 
   * @param produit  Produit à ajouter au panier.
   * @param quantite Quantité de produit à ajouter au panier.
   * @return Ligne du panier ajoutée ou modifiée.
   * @exception IllegalArgumentException Quantité non valide (négative
   *                                     ou nulle)
   */
  public Ligne ajouter(Produit produit, int quantite) {
    if (produit == null) {
      throw new NullPointerException("Le produit doit être non nul.");
    }
    if (quantite <= 0) {
      throw new IllegalArgumentException("La quantité doit être positive.");
    }
    var ligne = this.lignes.get(produit);

    if (ligne == null) {
      ligne = new Ligne(produit, quantite);
      this.lignes.put(produit, ligne);
    } else {
      ligne.quantite += quantite;
    }
    return ligne;
  }

  /**
   * Diminue de 1 la quantité d'un produit dans le panier et le retire du
   * panier si c'est le dernier exemplaire.
   * 
   * @param produit Référence du produit à retirer du panier.
   * @exception IllegalArgumentException Référence absente du panier.
   */
  public void diminuer(Produit produit) {
    var ligne = this.lignes.get(produit);

    if (ligne == null) {
      throw new IllegalArgumentException("Référence absente du panier");
    }
    if (--ligne.quantite == 0) {
      this.lignes.remove(produit);
    }
  }

  /**
   * Calcule le montant total du panier.
   * 
   * @return Montant total du panier.
   */
  public double getPrixTotal() {
    var total = this.lignes.values().stream()
        .mapToDouble(l -> l.getPrixTotal())
        .sum();
    var totalReducs = this.reducs.stream()
        .mapToDouble(l -> l.getMontantPanier(total))
        .sum();
    return total - totalReducs;
  }

  /**
   * Vérifie si le panier est vide.
   * 
   * @return true si le panier est vide, false sinon.
   */
  public boolean estVide() {
    return this.lignes.size() == 0;
  }

  /**
   * Représente une ligne du panier.
   */
  public class Ligne {
    /**
     * Initialise un ligne du panier caractérisée par un produit et une
     * quantité.
     * 
     * @param produit  Produit de la ligne du panier.
     * @param quantite Quantité de produit sur cette ligne.
     */
    public Ligne(Produit produit, int quantite) {
      this.produit = produit;
      this.quantite = quantite;
    }

    /**
     * Obtient la quantité du produit actuellement présente dans le panier.
     * 
     * @return Quantité de produit dans le panier.
     */
    public int getQuantite() {
      return this.quantite;
    }

    /**
     * Obtient le produit présent dans la ligne du panier.
     * 
     * @return Produit de la ligne.
     */
    public Produit getProduit() {
      return this.produit;
    }

    /**
     * Calcule le montant total de la ligne.
     * 
     * @return Montant de la ligne en fonction du prix de vente et de la
     *         quantité.
     */
    public double getPrixTotal() {
      var totalReducs = Panier.this.reducs.stream()
          .mapToDouble(r -> r.getMontantLigne(
              this.produit.getReference(),
              this.quantite,
              this.produit.getPrix()))
          .sum();
      return this.produit.getPrix() * this.quantite - totalReducs;
    }

    /**
     * Vérifie si une ligne correspond bien à une autre.
     * 
     * @return true si la ligne contient le même produit en même quantité,
     *         false sinon.
     */
    @Override
    public boolean equals(Object o) {
      return o instanceof Ligne
          && this.hashCode() == o.hashCode();
    }

    /**
     * Détermine une valeur unique pour une ligne de panier donnée.
     * 
     * @return Hachage de la ligne.
     */
    @Override
    public int hashCode() {
      return String.format(
          "%s,%d",
          this.produit.getReference(),
          this.quantite).hashCode();
    }

    private Produit produit;
    private int quantite;
  }

  private Map<Produit, Ligne> lignes = new HashMap<>();
  private List<Reduc> reducs = new ArrayList<>();

  private static Map<String, Reduc> coupons = new HashMap<>();

  public static void referencerCoupon(String code, Reduc reduction) {
    coupons.put(code, reduction);
  }
}
