package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.syllab.boutique.NommageRoyOsherove;
import com.syllab.boutique.metier.reducs.CodeCoupons;
import com.syllab.boutique.metier.reducs.Reduc;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class PanierTest {

  private Panier panier;

  @BeforeEach
  void creerPanier() {
    panier = new Panier();
  }

  Panier.Ligne ajouterProduit(String ref, double prix, int qte) {
    return panier.ajouter(new Produit(ref, "L" + ref, prix), qte);
  }

  void assertPanierVide() {
    assertTrue(panier.estVide());
  }

  void assertPanierNonVide() {
    assertFalse(panier.estVide());
  }

  void assertPrixTotalPanier(double attendu) {
    assertEquals(attendu, panier.getPrixTotal(), .0001);
  }

  void assertLignePanier(double attendu_prix, int attendue_qte, Panier.Ligne reelle) {
    assertEquals(attendue_qte, reelle.getQuantite());
    assertEquals(attendu_prix, reelle.getPrixTotal(), .0001);
  }

  // Initialisation
  // - Usuel
  @Test
  void initialisation_panierVide() {
    assertPanierVide();
    assertPrixTotalPanier(0);
    assertFalse(panier.getLignes().iterator().hasNext(), "Présence d'une ligne sur un nouveau panier");
  }
  // - Extrême (aucun)
  // - Erreur (aucun)

  // Ajouter
  // - Usuel
  @Test
  void ajouter_1Produit() {
    ajouterProduit("AT12", 2, 3);

    assertPrixTotalPanier(6);
    assertPanierNonVide();
  }

  @Test
  void ajouter_2ProduitsDifferents() {
    var l1 = ajouterProduit("P1", 2, 3);
    var l2 = ajouterProduit("P2", 5, 1);

    assertIterableEquals(Arrays.asList(l1, l2), panier.getLignes());
    assertPrixTotalPanier(11);
    assertPanierNonVide();
  }

  @Test
  @DisplayName("(ajouter) 1 produit 2 fois -> additionne les quantités")
  void ajouter_1Produit2Fois_AdditionneLesQuantites() {
    var p1 = ajouterProduit("P1", 2, 3).getProduit();
    var attendue = panier.new Ligne(p1, 4);

    panier.ajouter(p1, 1);

    assertIterableEquals(Arrays.asList(attendue), panier.getLignes());
    assertPrixTotalPanier(8);
    assertPanierNonVide();
  }

  // - Extreme (aucun)
  // - Erreur
  @Test
  void ajouter_quantite0_leveIllegalArgumentException() {
    Executable act = () -> ajouterProduit("P1", 2, 0);

    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void ajouter_quantiteNegative_leveIllegalArgumentException() {
    Executable act = () -> ajouterProduit("P1", 2, -3);

    assertThrows(IllegalArgumentException.class, act);
  }

  @Test
  void ajouter_produitNull_leveNullPointerException() {
    Executable act = () -> panier.ajouter(null, 2);

    assertThrows(NullPointerException.class, act);
  }

  // Diminuer
  // - Usuel
  @Test
  void diminuer_produitEnQuantite2OuPlus() {
    var p = ajouterProduit("P1", 2, 3).getProduit();

    panier.diminuer(p);

    assertPrixTotalPanier(4);
    assertPanierNonVide();
  }

  // - Extreme
  @Test
  void diminuer_dernierProduitEnQuantite1_panierVide() {
    var p = ajouterProduit("P1", 2, 1).getProduit();

    panier.diminuer(p);

    assertPrixTotalPanier(0);
    assertPanierVide();
  }

  @Test
  void diminuer_avantDernierProduitEnQuantite1_retireLeProduit() {
    ajouterProduit("P1", 2, 3);

    var p2 = ajouterProduit("P2", 5, 1).getProduit();

    panier.diminuer(p2);

    assertPrixTotalPanier(6);
    assertPanierNonVide();
  }

  // - Erreur
  @Test
  void diminuer_produitAbsentDuPanier_leveIllegalArgumentException() {
    var p2 = new Produit("P2", "L2", 5);

    ajouterProduit("P1", 2, 5);

    Executable act = () -> panier.diminuer(p2);

    assertThrows(IllegalArgumentException.class, act);
  }

  /*
   * Dépendances :
   * - Interface -> simuler
   *   Reduc, CodeCoupons
   * - Classe de bibliothèque
   * - Déterministe -> ok
   *   java.util
   * - Non-déterministe -> interface
   *   Random, Date, ...
   * - Classe projet
   * - Sans dépendance -> ok
   *   Produit
   * - Avec dépendances -> interface
   *   GestionnaireCoupons -> CodeCoupons
   * - Statique -> Classe/Interface en paramètre/membre
   */
  @Test
  void appliquerReduction_couponInexistant() {
    CodeCoupons coupons = mock(CodeCoupons.class);

    when(coupons.getReduc("CP")).thenReturn(null);
    ajouterProduit("P1", 10, 2);
    panier.appliquerReduction(coupons, "CP");
    
    assertPrixTotalPanier(20);
  } 
  @Test
  void appliquerReduction_reducPanier() {
    CodeCoupons coupons = mock(CodeCoupons.class);
    Reduc reduc = mock(Reduc.class);

    when(coupons.getReduc("CP")).thenReturn(reduc);
    when(reduc.getMontantLigne(anyString(), anyInt(), anyDouble())).thenReturn(0.0);
    when(reduc.getMontantPanier(anyDouble())).thenReturn(5.0);
    ajouterProduit("P1", 10, 2);
    panier.appliquerReduction(coupons, "CP");

    assertPrixTotalPanier(15);
  }
  @Test
  void appliquerReduction_reducPanierLigne() {
    /* ... */
  }
  @Test
  void appliquerReduction_reducPanierSuperieurAuPanier_leveIllegalStateException() {
    /* ... */
  } 
  @Test
  void appliquerReduction_reducLigneSuperieureALaLigne_leveIllegalStateException() {
    /* ... */
  }
  
}