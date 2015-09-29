package co.aquario.folkrice.model;

import java.util.ArrayList;

public class ModelCart {
	
   private ArrayList<ProductAquery> cartProducts = new ArrayList<ProductAquery>();
	

   public ProductAquery getProducts(int pPosition) {
		
		return cartProducts.get(pPosition);
	}
	
	public void setProducts(ProductAquery Products) {
	   
		cartProducts.add(Products);
		
	}
	
	public int getCartSize() {
		   
		return cartProducts.size();
		
	}
 
	public boolean checkProductInCart(ProductAquery aProduct) {
		   
		return cartProducts.contains(aProduct);
		
	}

}
