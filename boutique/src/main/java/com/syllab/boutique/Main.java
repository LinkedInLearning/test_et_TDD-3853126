package com.syllab.boutique;

import java.io.PrintStream;

import com.syllab.boutique.metier.Produit;
import com.syllab.boutique.metier.Panier;

public class Main {
  public static void main(String[] args) {
    Produit p1 = new Produit("AT23", "Parpaing", 0.50);
    Produit p2 = new Produit("AT12", "Ciment", 7.50);
    Panier panier = new Panier();

    panier.ajouter(p1, 5);
    panier.ajouter(p2, 8);
    panier.ajouter(p1, 2);
    panier.diminuer(p1);

    afficherPanier(System.out, panier);
  }

  private final static String FORMAT_LIGNE = "| %-30s | %5d | %8.2f | %8.2f |\n";
  private final static String SEPARATEUR = "+--------------------------------+-------+----------+----------+";
  private final static String FORMAT_TOTAL = "                                             TOTAL  | %8.2f |\n";
  private final static String PIED = "                                                    +----------+";

  private static void afficherPanier(PrintStream sortie, Panier panier) {
    sortie.println(SEPARATEUR);
    for (var l : panier.getLignes()) {
      var p = l.getProduit();
      sortie.printf(
          FORMAT_LIGNE,
          p,
          l.getQuantite(),
          p.getPrix(),
          l.getPrixTotal());
    }
    sortie.println(SEPARATEUR);
    sortie.printf(FORMAT_TOTAL, panier.getPrixTotal());
    sortie.println(PIED);
  }
}