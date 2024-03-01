package com.syllab.boutique.metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import com.syllab.boutique.metier.reducs.GestionnaireCoupons;
import com.syllab.boutique.metier.reducs.HappyHour;
import com.syllab.boutique.metier.reducs.ProduitOffert;
import com.syllab.boutique.metier.reducs.ReducSeuil;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BddStepDef {
  private Panier panier;
  private GestionnaireCoupons coupons = new GestionnaireCoupons();
  private Map<String, Produit> produits = new HashMap<>();

  @Given("un panier")
  public void creerPanier() {
    panier = new Panier();
  }

  @Given("un produit/article {word} nommé {string} à {double} euro(s)")
  public void creerProduit(String ref, String libelle, double prix) {
    produits.put(ref, new Produit(ref, libelle, prix));
  }

  @Given("un coupon {word} offrant -{int}% de {int}h à {int}h")
  public void creerHappyHour(String code, int pourcentage, int debut, int fin) {
    coupons.referencer(code, new HappyHour(
        ((double) pourcentage) / 100,
        debut,
        fin));
  }

  @Given("un coupon {word} offrant {double} euro(s) de réduction pour {double} euro(s) d'achat")
  public void creerReducSeuil(String code, double montant, double seuil) {
    coupons.referencer(code, new ReducSeuil(montant, seuil));
  }

  @Given("un coupon {word} offrant un/1 {word} offert pour {int} acheté(s)")
  public void creerProduitOffert(String code, String ref, int qte) {
    coupons.referencer(code, new ProduitOffert(ref, qte + 1));
  }

  @When("on applique (la réduction ){word}( au panier)")
  public void appliquerReduction(String codeCoupon) {
    panier.appliquerReduction(coupons, codeCoupon);
  }

  @When("on ajoute {int} {word}( au panier)")
  public void ajouterProduit(int qte, String ref) {
    panier.ajouter(produits.get(ref), qte);
  }

  @Then("le panier n'est pas vide")
  public void assertPanierNonVide() {
    assertFalse(panier.estVide());
  }

  @Then("le total du panier est de {double} euro(s)")
  public void assertPrixTotalPanier(double attendu) {
    assertEquals(attendu, panier.getPrixTotal(), .0001);
  }

}
