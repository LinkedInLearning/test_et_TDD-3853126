package com.syllab.boutique.metier.reducs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.syllab.boutique.NommageRoyOsherove;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class ProduitOffertTest {
  @Test
  void initialisation_usuelle() {
    var reduc = new ProduitOffert("PX", 3);

    assertNotNull(reduc);
  }

  @Test
  void initialisation_refNull_leveNullPointerException() {
    Executable act = () -> new ProduitOffert(null, 3);

    assertThrows(NullPointerException.class, act);
  }

  @ParameterizedTest
  @CsvSource({ "'', 2", "PX, 0", "PX, -2" })
  void initialisation_parametreNonValides_leveIllegalArgumentException(String ref, int qte) {
    Executable act = () -> new ProduitOffert(ref, qte);

    assertThrows(IllegalArgumentException.class, act);
  }

  @ParameterizedTest
  @ValueSource(doubles = { 0, 1000 })
  void getMontantPanier_touteQuantite_retourne0(double prix) {
    var reduc = new ProduitOffert("PX", 3);

    assertEquals(0, reduc.getMontantPanier(prix));
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 2, 3, 4, 6 })
  void getMontantLigne_autreRef_retourne0(int qte) {
    var reduc = new ProduitOffert("PX", 3);

    assertEquals(0, reduc.getMontantLigne("PY", qte, 5));
  }

  @ParameterizedTest
  @CsvSource({ "0, 0", "2, 0", "3, 5", "4, 5", "6, 10", "8, 10", "9, 15" })
  void getMontantLigne_autreRef_retourne0(int qte, double reducAttendue) {
    var reduc = new ProduitOffert("PX", 3);

    assertEquals(reducAttendue, reduc.getMontantLigne("PX", qte, 5), .0001);
  }

}