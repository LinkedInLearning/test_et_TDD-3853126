package com.syllab.boutique.metier.reducs;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import com.syllab.boutique.NommageRoyOsherove;

@DisplayNameGeneration(NommageRoyOsherove.class)
@ExtendWith(MockitoExtension.class)
public class HappyHourTest {

  @InjectMocks
  HappyHour test = new HappyHour(0.10, 17, 19);

  @Mock
  Clock horloge;

  private void fixerHorloge(int heure) {
    when(horloge.instant())
      .thenReturn(Instant.parse(
        String.format("2024-02-29T%d:00:00.00Z", heure)
      ));
    when(horloge.getZone()).thenReturn(ZoneId.systemDefault());
  }

  @Test
  void getMontantPanier_horsHappyHour() {
    fixerHorloge(12);
    assertEquals(0, test.getMontantPanier(100));
  }

  @Test
  void getMontantPanier_pendantHappyHour() {
    fixerHorloge(18);
    assertEquals(10, test.getMontantPanier(100));
  }
}
