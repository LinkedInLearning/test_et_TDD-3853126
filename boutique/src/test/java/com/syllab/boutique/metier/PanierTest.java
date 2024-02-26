package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PanierTest {
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
}