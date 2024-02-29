package com.syllab.boutique.metier.reducs;

/**
 * Représente un fournisseur de réductions par code coupon.
 */
public interface CodeCoupons {
  /**
   * Obtient une réduction d'après le code fourni.
   * 
   * @param coupon Code du coupon.
   * @return Réduction correspondant au coupon, null
   *         si le coupon est inconnu.
   */
  Reduc getReduc(String coupon);
}
