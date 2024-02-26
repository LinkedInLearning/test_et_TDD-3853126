package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProduitTest {
  @Test
  void initialisation_produitUsuel() {
    var test = new Produit("REF", "LIB", 2.30);

    assertEquals("REF", test.getReference());
    assertEquals("LIB", test.getLibelle());
    assertEquals(2.30, test.getPrix());
  }

  @Test
  void initialisation_prix0_leveIllegalArgumentException() {
    new Produit("REF", "LIB", 0);
  }

  @Test
  void initialisation_prixNegatif_leveIllegalArgumentException() {
    new Produit("REF", "LIB", -2.30);
  }

  @Test
  void initialisation_referenceVide_leveIllegalArgumentException() {
    new Produit("", "LIB", 2.30);
  }

  @Test
  void initialisation_referenceNull_leveNullPointerException() {
    new Produit(null, "LIB", 2.30);
  }

  @Test
  void initialisation_libelleVide_leveIllegalArgumentException() {
    new Produit("REF", "", 2.30);
  }

  @Test
  void initialisation_libelleNull_leveNullPointerException() {
    new Produit("REF", null, 2.30);
  }

  @Test
  void clone_produitUsuel() throws CloneNotSupportedException {
    var p = new Produit("REF", "LIB", 2.30);

    var test = p.clone();

    assertEquals(p, test);
  }
}
