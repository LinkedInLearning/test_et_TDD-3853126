package com.syllab.boutique.metier.reducs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.syllab.boutique.NommageRoyOsherove;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class HappyHourTest {

  private void fixerHorloge(int heure) {

  }

  @Test
  void getMontantPanier_horsHappyHour() {
    HappyHour test = new HappyHour(0.10, 17, 19);

    fixerHorloge(12);
    assertEquals(0, test.getMontantPanier(100));
  }

  @Test
  void getMontantPanier_pendantHappyHour() {
    HappyHour test = new HappyHour(0.10, 17, 19);
    
    fixerHorloge(18);
    assertEquals(10, test.getMontantPanier(100));
  }
}
