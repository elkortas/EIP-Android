package com.epitech.neerbyy;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.epitech.neerbyy.Network.ACTION;
import com.epitech.neerbyy.Network.METHOD;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Menu2 extends MainMenu {

	Button btnMap;
	Button btnFlux;
	Button btnFavorite; 
	Button btnMessage; 
	Button btnInfo;
	Button btnCoDeco;
	Button btnOptions;
	
	ProgressDialog mProgressDialog;
	ResponseWS rep;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu2);

		btnMap = (Button)findViewById(R.id.btnMenuViewMap);
		btnFlux = (Button)findViewById(R.id.btnMenuFlux);
		btnFavorite = (Button)findViewById(R.id.btnMenuFavoris);
		btnMessage = (Button)findViewById(R.id.btnMenuMessage);
		btnInfo = (Button)findViewById(R.id.btnMenuMyInfo);
		btnCoDeco = (Button)findViewById(R.id.btnMenuCoDeco);
		btnOptions = (Button)findViewById(R.id.btnMenuOption);
		
		if (Network.USER == null) {
			btnFlux.setEnabled(false);
			btnFavorite.setEnabled(false);
			btnMessage.setEnabled(false);
			btnInfo.setEnabled(false);
			
			btnFlux.setVisibility(View.INVISIBLE);
			btnFavorite.setVisibility(View.INVISIBLE);
			btnMessage.setVisibility(View.INVISIBLE);
			btnInfo.setVisibility(View.INVISIBLE);
		}
		else
		{
			//btnCoDeco.setText("D�connexion");
			//btnCoDeco.setBackgroundColor(R.color.orangeNeerbyy);
			//btnCoDeco.se(R.color.orangeNeerbyy);
			
			/*btnFlux.setEnabled(true);
			btnFavorite.setEnabled(true);
			btnMessage.setEnabled(true);
			btnInfo.setEnabled(true);*/	
		}
	
		btnMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu2.this, MapView.class);
				startActivity(intent);
			}
		});
		btnFlux.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu2.this, ViewFeed.class);
				startActivity(intent);
			}
		});
		/*btnFavorite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu2.this, CreateAccount.class);
				startActivity(intent);
			}
		});*/
		btnMessage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu2.this, ViewConv.class);
				startActivity(intent);
			}
		});
		btnInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu2.this, EditInfoUser.class);
				startActivity(intent);
			}
		});
		btnCoDeco.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent;
				if (Network.USER == null) {
					intent = new Intent(Menu2.this, Login.class);
					startActivity(intent);
					return;
				}
				
				
				mProgressDialog = ProgressDialog.show(Menu2.this, "Please wait",
						"Long operation starts...", true);
				
				Thread thread1 = new Thread(){
			        public void run(){	        	      
					try {	
		            	Gson gson = new Gson();
		            	String url = Network.URL + Network.PORT + "/sessions/" + Network.USER.token + ".json";

		            	Message myMessage, msgPb;
		            	msgPb = myHandler.obtainMessage(0, (Object) "Please wait");	 
		                myHandler.sendMessage(msgPb);
		                
						Bundle messageBundle = new Bundle();
						messageBundle.putInt("action", ACTION.DECO.getValue());
				        myMessage = myHandler.obtainMessage();	
   		        
				        InputStream input = Network.retrieveStream(url, METHOD.DELETE, null);
						if (input == null)
							messageBundle.putInt("error", 1);
						else
						{	
							Reader readerResp = new InputStreamReader(input);
							String ret = Network.checkInputStream(readerResp);
							
							if (ret.charAt(0) != '{' && ret.charAt(0) != '[')
							{
								messageBundle.putInt("error", 3);
								messageBundle.putString("msgError", ret);
							}
							else
							{
								try {
									rep = gson.fromJson(ret, ResponseWS.class);
								}
								catch(JsonParseException e)
							    {
							        System.out.println("Exception n3 in check_exitrestrepWSResponse::"+e.toString());
							    }
								
								if (rep.responseCode == 1)
								{
									messageBundle.putInt("error", 2);
									messageBundle.putString("msgError", rep.responseMessage);
								}
								//else		  	                   
									//messageBundle.putSerializable("user", (Serializable) user);	
							}
						}						
						myMessage.setData(messageBundle);
	                    myHandler.sendMessage(myMessage);
	                    
	                    msgPb = myHandler.obtainMessage(1, (Object) "Success");
		                myHandler.sendMessage(msgPb);
	                }
					catch (Exception e) {
		                e.printStackTrace();}
					
				}};
			thread1.start();						
			}
		});
		/*btnOptions.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu2.this, CreateAccount.class);
				startActivity(intent);
			}
		});*/
	
	}
	
	
	
	Handler myHandler = new Handler()
	{
	    @Override 
	    public void handleMessage(Message msg)
	    {
	    	switch (msg.what) {
	        case 0:   //  begin
	            if (mProgressDialog.isShowing()) {
	                mProgressDialog.setMessage(((String) msg.obj));
	                //return;
	            }
	            break;
	        case 1:  //  finish
	        	if (mProgressDialog.isShowing()) {
	                mProgressDialog.dismiss();
	                //return;
	        	}
	        	break;
	        default: // should never happen
	            break;
	    	}
	    	
	    	Bundle pack = msg.getData();
	    	int Error = pack.getInt("error");
	    	switch (Network.ACTION.values()[pack.getInt("action")])
	    	{
		    	case DECO:
		    		
			    	if (Error == 1)
			    		Toast.makeText(getApplicationContext(), "Error: connection with WS fail", Toast.LENGTH_LONG);
			    	else if (Error == 2)
			    	{
			    		Toast.makeText(getApplicationContext(), "Login error :\n" + pack.getString("msgError"), Toast.LENGTH_LONG).show();
			    	}
			    	else if (Error == 3)
			    		Toast.makeText(getApplicationContext(), "Network error :\n" + pack.getString("msgError"), Toast.LENGTH_LONG).show();
			    	else
			    	{
			    		Toast.makeText(getApplicationContext(), "Logout success", Toast.LENGTH_LONG).show();
			    		Network.USER = null;
			    		Intent intent = new Intent(Menu2.this, Login.class);
						startActivity(intent);						
			    	}
			    	break;
	    	} 	
	    }
	};
	
	
}