package com.syllab.boutique.metier.reducs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.syllab.boutique.NommageRoyOsherove;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class ReducSeuilTest {
  @Test
  void initialisation_usuelle() {
    var reduc = new ReducSeuil(5, 50);

    assertEquals(0, reduc.getMontantLigne("PX", 1000, 3));
    assertEquals(0, reduc.getMontantPanier(49));
    assertEquals(5, reduc.getMontantPanier(50), .0001);
    assertEquals(5, reduc.getMontantPanier(110), .0001);
  }

  @ParameterizedTest
  @CsvSource({ 
    "0, 50,Réduction à zéro",
    "5, 5, Réduction identique au seuil",
    "50, 5, Réduction supérieure au seuil",
    "-1, 50, Réduction négative"
  })
  void initialisation_parametreNonValides_leveIllegalArgumentException(double montant, double seuil, String message) {
    Executable act = () -> new ReducSeuil(montant, seuil);

    assertThrows(IllegalArgumentException.class, act, message);
  }
}
