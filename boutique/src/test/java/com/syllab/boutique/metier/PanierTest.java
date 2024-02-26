package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PanierTest {
  // Initialisation
  // - Usuel
  // initialisation_panierVide
  // - Extrême (aucun)
  // - Erreur (aucun)

  // Ajouter
  // - Usuel
  @Test
  void ajouter_1Produit() {
    // Arrange
    Produit p = new Produit("AT12", "Ciment", 2);
    Panier panier = new Panier();

    // Act
    panier.ajouter(p, 3);
    
    // Assertion
    assertEquals(6, panier.getPrixTotal());
  }
  // ajouter_2ProduitsDifferents
  // ajouter_1Produit2fois_AdditionneLesQuantites
  // - Extreme (aucun)
  // - Erreur
  // ajouter_quantite0_leveIllegalArgumentException
  // ajouter_quantiteNegative_leveIllegalArgumentException
  // ajouter_produitNull_leveNullPointerException

  // Diminuer
  // - Usuel
  // diminuer_produitEnQuantite2OuPlus
  // - Extreme
  // diminuer_dernierProduitEnQuantite1_panierVide
  // diminuer_avantDernierProduitEnQuantite1_retireLeProduit
  // - Erreur
  // diminuer_produitAbsentDuPanier_leveIllegalArgumentException
}