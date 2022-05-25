package fr.insalyon.p2i2.application;

import javax.swing.*;

import fr.insalyon.p2i2.connexionBD.Produit;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.OpenFoodFactsWrapper.*;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;
import pl.coderion.model.Product;
import pl.coderion.model.ProductResponse;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

import java.awt.*;

public class ProduitCompo extends Compo {

    private static OpenFoodFactsWrapper foodWrapper = new OpenFoodFactsWrapperImpl();

    public ProduitCompo(Produit produit) {
        setMySize(400, 100);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ProductResponse productResponse =
        // foodWrapper.fetchProductByCode(String.valueOf(produit.codebarre));
        // Product product = productResponse.getProduct();

        JLabel nomProduit = new JLabel(produit.nom);
        nomProduit.setFont(boldFont);
        add(nomProduit);
        // JLabel marque = new JLabel(product.getBrands());
        // marque.setFont(mainFont);
        // add(marque);
        JLabel quantite = new JLabel("quantité : " + String.valueOf(produit.quantite));
        quantite.setFont(mainFont);
        add(quantite);

    }

    @Override
    public Insets getInsets() {
        return new Insets(inset, inset, inset, inset);
    }

}
