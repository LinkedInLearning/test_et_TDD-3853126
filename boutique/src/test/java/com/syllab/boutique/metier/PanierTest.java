package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class PanierTest {
  // Initialisation
  // - Usuel
  @Test
  void initialisation_panierVide() {
    var p = new Panier();

    assertTrue(p.estVide());
    assertEquals(0, p.getPrixTotal(), 0.0001);
    assertFalse(p.getLignes().iterator().hasNext());
  }
  // - Extrême (aucun)
  // - Erreur (aucun)

  // Ajouter
  // - Usuel
  @Test
  void ajouter_1Produit() {
    // Arrange
    var p = new Produit("AT12", "Ciment", 2);
    var panier = new Panier();

    // Act
    panier.ajouter(p, 3);

    // Assertion
    assertEquals(6, panier.getPrixTotal(), 0.0001);
    assertFalse(panier.estVide());
  }

  @Test
  void ajouter_2ProduitsDifferents() {
    var p1 = new Produit("P1", "L1", 2);
    var p2 = new Produit("P2", "L2", 5);
    var panier = new Panier();

    panier.ajouter(p1, 3);
    panier.ajouter(p2, 1);

    assertEquals(11, panier.getPrixTotal(), 0.0001);
    assertFalse(panier.estVide());
  }

  @Test
  void ajouter_1Produit2fois_AdditionneLesQuantites() {
    var p1 = new Produit("P1", "L1", 2);
    var panier = new Panier();

    panier.ajouter(p1, 3);
    panier.ajouter(p1, 1);

    assertEquals(8, panier.getPrixTotal(), 0.0001);
    assertFalse(panier.estVide());
  }

  // - Extreme (aucun)
  // - Erreur
  @Test
  void ajouter_quantite0_leveIllegalArgumentException() {
    var p1 = new Produit("P1", "L1", 2);
    var panier = new Panier();

    Executable act = () -> panier.ajouter(p1, 0);

    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void ajouter_quantiteNegative_leveIllegalArgumentException() {
    var p1 = new Produit("P1", "L1", 2);
    var panier = new Panier();

    Executable act = () -> panier.ajouter(p1, -3);

    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void ajouter_produitNull_leveNullPointerException() {
    var panier = new Panier();

    Executable act = () -> panier.ajouter(null, 2);

    assertThrows(NullPointerException.class, act);
  }

  // Diminuer
  // - Usuel
  @Test
  void diminuer_produitEnQuantite2OuPlus() {
    var p = new Produit("P1", "L1", 2);
    var panier = new Panier();

    panier.ajouter(p, 3);

    panier.diminuer(p);

    assertEquals(4, panier.getPrixTotal(), 0.0001);
    assertFalse(panier.estVide());
  }

  // - Extreme
  @Test
  void diminuer_dernierProduitEnQuantite1_panierVide() {
    var p = new Produit("P1", "L1", 2);
    var panier = new Panier();

    panier.ajouter(p, 1);

    panier.diminuer(p);

    assertEquals(0, panier.getPrixTotal(), 0.0001);
    assertTrue(panier.estVide());
  }

  @Test
  void diminuer_avantDernierProduitEnQuantite1_retireLeProduit() {
    var p1 = new Produit("P1", "L1", 2);
    var p2 = new Produit("P2", "L2", 5);
    var panier = new Panier();

    panier.ajouter(p1, 3);
    panier.ajouter(p2, 1);

    panier.diminuer(p2);

    assertEquals(6, panier.getPrixTotal(), 0.0001);
    assertFalse(panier.estVide());
  }

  // - Erreur
  @Test
  void diminuer_produitAbsentDuPanier_leveIllegalArgumentException() {
    var p1 = new Produit("P1", "L1", 2);
    var p2 = new Produit("P2", "L2", 5);
    var panier = new Panier();

    panier.ajouter(p1, 3);

    Executable act = () -> panier.diminuer(p2);

    assertThrows(IllegalArgumentException.class, act);
  }
}