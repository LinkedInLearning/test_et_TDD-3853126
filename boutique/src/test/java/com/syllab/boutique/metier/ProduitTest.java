package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.syllab.boutique.NommageRoyOsherove;

@DisplayNameGeneration(NommageRoyOsherove.class)
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
    Executable act = () -> new Produit("REF", "LIB", 0);
    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void initialisation_prixNegatif_leveIllegalArgumentException() {
    Executable act = () -> new Produit("REF", "LIB", -2.30);
    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void initialisation_referenceVide_leveIllegalArgumentException() {
    Executable act = () -> new Produit("", "LIB", 2.30);
    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void initialisation_referenceNull_leveNullPointerException() {
    Executable act = () -> new Produit(null, "LIB", 2.30);
    assertThrows(NullPointerException.class, act);
  }

  @Test
  void initialisation_libelleVide_leveIllegalArgumentException() {
    Executable act = () -> new Produit("REF", "", 2.30);
    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void initialisation_libelleNull_leveNullPointerException() {
    Executable act = () -> new Produit("REF", null, 2.30);
    assertThrows(NullPointerException.class, act);
  }

  @Test
  void clone_produitUsuel() throws CloneNotSupportedException {
    var p = new Produit("REF", "LIB", 2.30);

    var test = p.clone();

    assertNotNull(test);
    assertEquals(p, test);
    assertNotSame(p, test);
    assertInstanceOf(Produit.class, test);
  }
}
