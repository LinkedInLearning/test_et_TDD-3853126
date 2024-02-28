package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.syllab.boutique.NommageRoyOsherove;
import com.syllab.boutique.metier.reducs.ProduitOffert;
import com.syllab.boutique.metier.reducs.ReducSeuil;

@DisplayNameGeneration(NommageRoyOsherove.class)
public class PanierTest {

  public final static String REDUC_CODE = "5POUR50";
  public final static double REDUC_MONTANT = 5;
  public final static double REDUC_SEUIL = 50;

  public final static String OFFERT_CODE = "PX3+1";
  public final static String OFFERT_REF = "PX";
  public final static int OFFERT_QTE = 4;

  @BeforeAll
  static void referencerLesCoupons() {
    Panier.referencerCoupon(OFFERT_CODE, new ProduitOffert(OFFERT_REF, OFFERT_QTE));
    Panier.referencerCoupon(REDUC_CODE , new ReducSeuil(REDUC_MONTANT, REDUC_SEUIL));
  }

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

  @Test
  void appliquerReduction_Total60Coupon5Pour50_Total55() {
    ajouterProduit("P1", 30, 2);

    panier.appliquerReduction(REDUC_CODE);

    assertPrixTotalPanier(55);
  }

  @Test
  void appliquerReduction_Total60CouponNonValide_Total60() {
    ajouterProduit("P1", 30, 2);

    panier.appliquerReduction("INVALIDE");

    assertPrixTotalPanier(60);
  }

  @Test
  void appliquerReduction_Total30Coupon5Pour50_Total30() {

    ajouterProduit("P1", 30, 1);

    panier.appliquerReduction(REDUC_CODE);

    assertPrixTotalPanier(30);
  }

  @Test
  void appliquerReduction_PX3Plus1Avec4PX_1PXOffert() {
    var ligne = ajouterProduit(OFFERT_REF, 20, 4);

    ajouterProduit("P2", 1, 4);

    panier.appliquerReduction(OFFERT_CODE);

    assertLignePanier(60, 4, ligne);
    assertPrixTotalPanier(60 + 4);
  }

  @Test
  void appliquerReduction_PX3Plus1Et5Pour50Total60_PXOffertEtTotal55() {
    var ligne = ajouterProduit(OFFERT_REF, 20, 4);

    panier.appliquerReduction(REDUC_CODE);
    panier.appliquerReduction(OFFERT_CODE);

    assertLignePanier(60, 4, ligne);
    assertPrixTotalPanier(55);
  }

  @Test
  void appliquerReduction_PX3Plus1Avec9PX_2PXOfferts() {
    ajouterProduit("P2", 1, 4);
    panier.appliquerReduction(OFFERT_CODE);

    var ligne = ajouterProduit(OFFERT_REF, 20, 9);

    assertLignePanier(140, 9, ligne);
    assertPrixTotalPanier(140 + 4);
  }

  @Test
  void appliquerReduction_PX3Plus1Avec3PX_PasDePXOffert() {
    var ligne = ajouterProduit(OFFERT_REF, 20, 3);

    ajouterProduit("P2", 1, 4);

    panier.appliquerReduction(OFFERT_CODE);

    assertLignePanier(60, 3, ligne);
    assertPrixTotalPanier(60 + 4);
  }
}