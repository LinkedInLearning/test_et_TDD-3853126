package com.syllab.petstore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.syllab.petstore.Pet.Status;

public class PetTest {
  private final static String PETSTORE_API = "https://petstore3.swagger.io/api/v3";
  private HttpClient http;

  @BeforeEach
  void init() {
    http = HttpClient.newHttpClient();
  }
  
  @Test
  void consulter_192837465_introuvable() throws IOException, InterruptedException {
    var requete = HttpRequest
        .newBuilder(construire("/pet/192837465"))
        .GET()
        .build();
    var reponse = http.send(requete, BodyHandlers.ofString());
  
    assertEquals(404, reponse.statusCode());
  }

  @Test
  void creer_rex918273645() throws IOException, InterruptedException {
    var rex = new Pet(918273645, "Rex", Status.pending);
    var corps = ""; // à faire : sérialiser rex en json
    var requete = HttpRequest
        .newBuilder(construire("/pet"))
        .POST(BodyPublishers.ofString(corps))
        .build();

    var reponse = http.send(requete, BodyHandlers.ofString());
    Pet reel = null; // à faire : dé-sérialiser l'objet renvoyé

    assertEquals(200, reponse.statusCode());
    assertEquals(rex, reel);
  }

  private URI construire(String ressource) {
    return URI.create(
      String.format("%s%s", PETSTORE_API, ressource)
    );
  }
}
