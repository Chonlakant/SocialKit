package co.aquario.folkrice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import co.aquario.folkrice.R;
import co.aquario.folkrice.model.Controller;

public class SecondScreen extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondscreen);
    
		TextView showCartContent    = (TextView) findViewById(R.id.showCart);
		final Button thirdBtn 		= (Button) findViewById(R.id.third);
		
		//Get Global Controller Class object (see application tag in AndroidManifest.xml)
		final Controller aController = (Controller) getApplicationContext();
		
		// Get Cart Size
		final int cartSize = aController.getCart().getCartSize();
		
		String showString = "";
		
/******** Show Cart Products on screen - Start ********/
		
		if(cartSize >0)
		{	
			
			for(int i=0;i<cartSize;i++)
			{
				//Get product details
				String pName 	= aController.getCart().getProducts(i).getName();
				Double pPrice   	= aController.getCart().getProducts(i).getPrice();
				String pDisc   	= aController.getCart().getProducts(i).getDesc();
				
				showString += "\n\nProduct Name : "+pName+"\n"+
	                               "Price : "+pPrice+"\n"+
	                               "Discription : "+pDisc+""+
	                               "\n -----------------------------------";
			}
		}
		else
			showString = "\n\nShopping cart is empty.\n\n";
		
		showCartContent.setText(showString);
		
/******** Show Cart Products on screen - End ********/
		
//		thirdBtn.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				if(cartSize >0)
//				{
//					Intent i = new Intent(getBaseContext(), ThirdScreen.class);
//					startActivity(i);
//				}
//				else
//					Toast.makeText(getApplicationContext(),
//							"Shopping cart is empty.",
//							Toast.LENGTH_LONG).show();
//			}
//		});
		
	} 
	
	@Override
    protected void onDestroy() {
		
        super.onDestroy();
        
    }
}
