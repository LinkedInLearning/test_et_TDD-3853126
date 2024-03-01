# language: fr

Fonctionnalité: Appliquer des réductions

    Appliquer une ou plusieurs réductions sur le panier
    d'une boutique en ligne.

    Scénario: Pas de réduction sur un nouveau panier
        Etant donné un panier
        Et un coupon 5POUR50 offrant 5 euros de réduction pour 50 euros d'achat
        Quand on applique 5POUR50 au panier
        Alors le total du panier est de 0 euro

    Scénario: Appliquer PX3+1 et 5POUR50 sur un panier de 2 produits
        Etant donné un panier
        Et un produit PX nommé "Produit 1" à 20 euros
        Et un produit PY nommé "Produit 2" à 1 euro
        Et un coupon HAPPYH offrant -10% de 17h à 19h
        Et un coupon PX3+1 offrant 1 PX offert pour 3 achetés
        Et un coupon 5POUR50 offrant 5 euros de réduction pour 50 euros d'achat
        Quand on ajoute 9 PX au panier
        Et on ajoute 4 PY au panier
        Et on applique la réduction PX3+1
        Et on applique la réduction 5POUR50
        Alors le panier n'est pas vide
        Et le total du panier est de 139 euros
