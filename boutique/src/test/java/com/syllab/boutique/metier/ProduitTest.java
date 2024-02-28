package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

  @ParameterizedTest
  @ValueSource(doubles = { 0, -2.30 })
  void initialisation_prixNonValide_leveIllegalArgumentException(double prix) {
    Executable act = () -> new Produit("REF", "LIB", prix);
    assertThrows(IllegalArgumentException.class, act);
  }

  @ParameterizedTest
  @CsvSource({ "'', LIB", "REF, ''" })
  void initialisation_referenceOuLibelleVide_leveIllegalArgumentException(String ref, String libelle) {
    Executable act = () -> new Produit(ref, libelle, 2.30);
    assertThrows(IllegalArgumentException.class, act);
  }
  
  @ParameterizedTest
  @CsvSource({ ", LIB", "REF, " })
  void initialisation_referenceOuLibelleNull_leveNullPointerException(String ref, String libelle) {
    Executable act = () -> new Produit(ref, libelle, 2.30);
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
