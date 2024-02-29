package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
  private CodeCoupons coupons;

  @BeforeEach
  void creerPanier() {
    panier = new Panier();
    coupons = mock(CodeCoupons.class);
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
   * Reduc, CodeCoupons
   * - Classe de bibliothèque
   * - Déterministe -> ok
   * java.util
   * - Non-déterministe -> interface
   * Random, Date, ...
   * - Classe projet
   * - Sans dépendance -> ok
   * Produit
   * - Avec dépendances -> interface
   * GestionnaireCoupons -> CodeCoupons
   * - Statique -> Classe/Interface en paramètre/membre
   */
  private Reduc newReduc(String nom) {
    var r = mock(Reduc.class);

    when(coupons.getReduc(nom)).thenReturn(r);
    return r;
  }

  private void retour_getMontantPanier(Reduc r, double montant) {
    when(r.getMontantPanier(anyDouble())).thenReturn(montant);
  }

  private void retour_getMontantLigne(Reduc r, String ref, double montant) {
    when(r.getMontantLigne(ref, anyInt(), anyDouble())).thenReturn(montant);
  }

  @Test
  void appliquerReduction_couponInexistant() {
    ajouterProduit("P1", 10, 2);
    panier.appliquerReduction(coupons, "CP");

    assertPrixTotalPanier(20);
  }

  @Test
  void appliquerReduction_reducPanier() {
    var reduc = newReduc("CP");

    retour_getMontantLigne(reduc, anyString(), 0.0);
    retour_getMontantPanier(reduc, 5);
    ajouterProduit("P1", 10, 2);

    panier.appliquerReduction(coupons, "CP");

    assertPrixTotalPanier(15);
  }

  @Test
  void appliquerReduction_reducPanierLigne() {
    var cp = newReduc("CP");

    retour_getMontantLigne(cp, eq("P1"), 3);
    retour_getMontantLigne(cp, eq("P2"), 0);
    retour_getMontantPanier(cp, 5);

    ajouterProduit("P1", 10, 2);
    ajouterProduit("P2", 100, 1);

    panier.appliquerReduction(coupons, "CP");

    assertPrixTotalPanier((20 - 3) + 100 - 5);
  }

  @Test
  void appliquerReduction_reducPanierSuperieurAuPanier_leveIllegalStateException() {
    var cp = newReduc("CP");

    retour_getMontantLigne(cp, anyString(), 0.0);
    retour_getMontantPanier(cp, 25);
    ajouterProduit("P1", 10, 2);
    panier.appliquerReduction(coupons, "CP");

    Executable act = () -> panier.getPrixTotal();

    assertThrows(IllegalStateException.class, act);
  }

  @Test
  void appliquerReduction_reducLigneSuperieureALaLigne_leveIllegalStateException() {
    var cp = newReduc("CP");

    retour_getMontantLigne(cp, anyString(), 25);
    retour_getMontantPanier(cp, 5);
    ajouterProduit("P1", 10, 2);
    panier.appliquerReduction(coupons, "CP");

    Executable act = () -> panier.getPrixTotal();

    assertThrows(IllegalStateException.class, act);
  }

  @Test
  void appliquerReduction_reducLeveUneExceptionSurPanier_reductionIgnoree() {
    Reduc c1 = newReduc("C1");
    Reduc c2 = newReduc("C2");

    retour_getMontantLigne(c1, anyString(), 0);
    retour_getMontantLigne(c2, anyString(), 0);

    when(c1.getMontantPanier(anyDouble())).thenThrow(new IllegalArgumentException("oups"));
    retour_getMontantPanier(c2, 5);

    panier.appliquerReduction(coupons, "C1");
    panier.appliquerReduction(coupons, "C2");

    ajouterProduit("P1", 10, 2);

    assertPrixTotalPanier(15);
  }

  @Test
  void appliquerReduction_reducLeveUneExceptionSurLaPremiereLigne_reductionIgnoree() {
    Reduc c1 = newReduc("C1");
    Reduc c2 = newReduc("C2");

    when(c1.getMontantLigne(eq("P1"), anyInt(), anyDouble()))
      .thenThrow(new IllegalArgumentException("oups"));
    retour_getMontantLigne(c2, eq("P1"), 6);
    retour_getMontantLigne(c1, eq("P2"), 1);
    retour_getMontantLigne(c2, eq("P2"), 0);

    retour_getMontantPanier(c1, 3);
    retour_getMontantPanier(c2, 7);

    panier.appliquerReduction(coupons, "C1");
    panier.appliquerReduction(coupons, "C2");

    ajouterProduit("P1", 10, 2);
    ajouterProduit("P2", 100, 1);

    assertPrixTotalPanier(20 - 6 + 100 - 1 - 3 - 7);
  }
}