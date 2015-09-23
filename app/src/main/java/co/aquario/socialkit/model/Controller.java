package co.aquario.socialkit.model;

import android.app.Application;

import java.util.ArrayList;

import co.aquario.socialkit.PrefManager;

public class Controller extends Application {
	
	private ArrayList<ProductAquery> myProducts = new ArrayList<ProductAquery>();
	private  ModelCart myCart = new ModelCart();
	private static PrefManager prefManager;

	public ProductAquery getProducts(int pPosition) {
		
		return myProducts.get(pPosition);
	}
	
	public void setProducts(ProductAquery Products) {
	   
		myProducts.add(Products);
		
	}	
	
	public ModelCart getCart() {
		   
		return myCart;
		
	}
 
   public int getProductsArraylistSize() {
		
		return myProducts.size();
	}
   
}
