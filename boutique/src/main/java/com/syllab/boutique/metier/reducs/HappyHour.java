package com.syllab.boutique.metier.reducs;

import java.time.Clock;
import java.time.LocalDateTime;
import javax.inject.Inject;

/**
 * Représente une réduction effective sur le panier sur une tranche horaire
 * précise.
 */
public class HappyHour implements Reduc {
  @Inject
  private Clock clock;

  /**
   * Initialise une réduction Happy Hour d'après un pourcentage de réduction
   * du panier, une heure de début et de fin de l'Happy Hour.
   * 
   * @param taux       Pourcentage de réduction sur le montant du panier
   * @param heureDebut Heure de début
   * @param heureFin   Heure de fin
   */
  public HappyHour(double taux, int heureDebut, int heureFin) {
    this.taux = taux;
    this.heureDebut = heureDebut;
    this.heureFin = heureFin;
  }

  @Override
  public double getMontantPanier(double prixTotal) {
    var maintenant = LocalDateTime.now(this.clock);

    return this.heureDebut <= maintenant.getHour()
        && maintenant.getHour() < this.heureFin
            ? prixTotal * this.taux
            : 0.0;
  }

  @Override
  public double getMontantLigne(String ref, int qte, double prix) {
    return 0;
  }

  private double taux;
  private int heureDebut, heureFin;
}
