package com.samuelgoes.dbsamuapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.android.AuthActivity;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;
import com.samuelgoes.dbsamuapp.form.ListaEpubForm;

public class EpubActivity extends Activity {

	private static final String TAG = "EpubActivity";
	
	//Elementos de la pantalla
	private Button _conectar, _leer;
	private TextView _estado;
	//private TextView _etiqueta;
	
	//Elementos Dropbox
	private DropboxAPI<AndroidAuthSession> _api;
	private boolean isConnected;
	private final static AccessType ACCESS_TYPE = AccessType.DROPBOX;
	private final String APP_KEY = "1k2se2ge619e518";
	private final String APP_SECRET = "zbep3hhasokjmh9";
	
	//Elementos Constantes
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub);
        
        AndroidAuthSession sesion;
        
        sesion = buildSession();
        _api = new DropboxAPI<AndroidAuthSession>(sesion);
        
        checkAppKeySetup();
        
        _conectar = (Button)findViewById(R.id.btn_conectar);
        _conectar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isConnected){
					//Desconectar
				}else{
					_api.getSession().startAuthentication(EpubActivity.this);
				}
			}
		});
        
        
        //_etiqueta= (TextView) findViewById(R.id.textView1);
        _estado = (TextView) findViewById(R.id.textView2);
        
        _leer = (Button)findViewById(R.id.btn_leer);
//        _leer.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ObtenerListaEpubs ole = new ObtenerListaEpubs(_api);
//				ole.execute();
//			}
//		});
        
        this.checkAppKeySetup();
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.epub, menu);
        return true;
    }
    
    
    @Override
    protected void onResume() {
        super.onResume();
        
        TokenPair tokens;
        AndroidAuthSession sesion;
        
        sesion = _api.getSession();

        if (sesion.authenticationSuccessful()) {
            try {
            	sesion.finishAuthentication();

                tokens = sesion.getAccessTokenPair();
                storeKeys(tokens.key, tokens.secret);
                setLoggedIn(true);
            } catch (IllegalStateException e) {
                showToast("No se ha podido autenticar en Dropbox:" + e.getLocalizedMessage());
                Log.i(TAG, "Error Autenticaci—n", e);
            }
        }else{
        	setLoggedIn(false);
        }
    }
    
    
    public void mostrarEpubs(View view) {
    	Intent intent;
    	ListaEpubForm form;
    	
    	intent = new Intent(this, ListaEpubs.class);
    	form = ListaEpubForm.getInstancia();
    	form.setApi(_api);
        startActivity(intent);
    }
    
    
    //*******************************
    //*		METODOS PRIVADOS		*
    //*******************************
    
    
    
    private void checkAppKeySetup(){
    	Intent test;
    	String uri;
    	PackageManager pm;
    	
    	pm = getPackageManager();
    	
    	test = new Intent(Intent.ACTION_VIEW);
    	uri = "db-" + APP_KEY + "://" + AuthActivity.AUTH_VERSION + "/test";
    	test.setData(Uri.parse(uri));
    	
    	
    	if (0 == pm.queryIntentActivities(test, 0).size()) {
            showToast("Manifiesto incorrecto");
            finish();
        }    	
    }
    
    
    private void setLoggedIn(boolean loggedIn) {
    	isConnected = loggedIn;
    	if (isConnected) {
    		_conectar.setText("Desconectar");
    		_estado.setText("Conectado");
    	} else {
    		_conectar.setText("Conectar");
            _estado.setText("Desconectado");
    	}
    }
    
    
//    private void logOut() {
//        _api.getSession().unlink();
//        clearKeys();
//        setLoggedIn(false);
//    }
    
    
    private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }
    
    
    private String[] getKeys() {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(ACCESS_KEY_NAME, null);
        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
        
        if (key != null && secret != null) {
        	String[] ret = new String[2];
        	ret[0] = key;
        	ret[1] = secret;
        	return ret;
        } else {
        	return null;
        }
    }


    private void storeKeys(String key, String secret) {
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        Editor edit = prefs.edit();
        edit.putString(ACCESS_KEY_NAME, key);
        edit.putString(ACCESS_SECRET_NAME, secret);
        edit.commit();
    }

//    private void clearKeys() {
//        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
//        Editor edit = prefs.edit();
//        edit.clear();
//        edit.commit();
//    }

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session;

        String[] stored = getKeys();
        if (stored != null) {
            AccessTokenPair accessToken = new AccessTokenPair(stored[0], stored[1]);
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, accessToken);
        } else {
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
        }

        return session;
    }
    
}
