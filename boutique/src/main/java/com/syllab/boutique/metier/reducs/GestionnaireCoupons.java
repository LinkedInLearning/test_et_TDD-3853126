package com.syllab.boutique.metier.reducs;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente un gestionnaire de coupons de réductions.
 */
public class GestionnaireCoupons implements CodeCoupons {
  /**
   * Référence un coupon de réduction.
   * 
   * @param coupon Code de coupon
   * @param reduc  Réduction associée au coupon.
   * @exception IllegalArgumentException Code de coupon vide.
   * @exception NullPointerException     Coupon ou réduction nulle.
   */
  public void referencer(String coupon, Reduc reduc) {
    if (coupon.length() == 0) {
      throw new IllegalArgumentException("Code de coupon vide");
    }
    if (reduc == null) {
      throw new NullPointerException("Réduction nulle");
    }
    this.coupons.put(coupon, reduc);
  }

  /**
   * Obtient la réduction associée à un coupon précédemment référencé.
   * 
   * @param coupon Code du coupon recherché.
   * @return Réduction associée au coupon, null si le coupon est inconnu.
   */
  public Reduc getReduc(String coupon) {
    return this.coupons.get(coupon);
  }

  private Map<String, Reduc> coupons = new HashMap<>();
}
