package com.syllab.boutique.metier.reducs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.syllab.boutique.NommageRoyOsherove;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class GestionnaireCouponsTest {

  private GestionnaireCoupons test;

  @BeforeEach
  void initTest() {
    test = new GestionnaireCoupons();
  }

  @Test
  void initialisation_gestionnaireVide() {
    assertNotNull(test);
    assertNull(test.getReduc("RZ"));
  }

  @Test
  void referencer_1Coupon() {
    var rz = new ReducZero();

    test.referencer("RZ", rz);

    assertSame(rz, test.getReduc("RZ"));
  }

  @Test
  void referencer_2Coupons() {
    var rz = new ReducZero();

    test.referencer("R1", rz);
    test.referencer("R2", new ReducZero());

    assertSame(rz, test.getReduc("R1"));
  }

  @Test
  void referencer_couponVide_leveIllegalArgumentException() {
    Executable act = () -> test.referencer("", new ReducZero());

    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void referencer_couponNull_leveNullPointerException() {
    Executable act = () -> test.referencer(null, new ReducZero());

    assertThrows(NullPointerException.class, act);
  }

  @Test
  void referencer_reducNull_leveNullPointerException() {
    Executable act = () -> test.referencer("RZ", null);

    assertThrows(NullPointerException.class, act);
  }

  private class ReducZero implements Reduc {

    @Override
    public double getMontantPanier(double prixTotal) {
      return 0;
    }

    @Override
    public double getMontantLigne(String ref, int qte, double prix) {
      return 0;
    }
  }
}
