package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.syllab.boutique.NommageRoyOsherove;
import com.syllab.boutique.metier.reducs.GestionnaireCoupons;
import com.syllab.boutique.metier.reducs.HappyHour;
import com.syllab.boutique.metier.reducs.ProduitOffert;
import com.syllab.boutique.metier.reducs.ReducSeuil;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class IntegrationTest {
  @Test
  void appliquerReduction_ProduitOfferEtReducSeuilSur2Produits() {
    var coupons = new GestionnaireCoupons();
    var panier = new Panier();
    var px = new Produit("PX", "Produit 1", 20);
    var py = new Produit("PY", "Produit 2", 1);

    coupons.referencer("5POUR50", new ReducSeuil(5, 50));
    coupons.referencer("PX3+1", new ProduitOffert("PX", 4));
    coupons.referencer("HAPPYH", new HappyHour(0.10, 17, 19));

    panier.ajouter(px, 9);
    panier.ajouter(py, 4);

    panier.appliquerReduction(coupons, "PX3+1");
    panier.appliquerReduction(coupons, "5POUR50");

    var lignesAttendues = Arrays.asList(
        panier.new Ligne(px, 9),
        panier.new Ligne(py, 4));
    assertFalse(panier.estVide());
    assertIterableEquals(lignesAttendues, panier.getLignes());
    assertEquals(140 + 4 - 5, panier.getPrixTotal(), .0001);
  }
}
