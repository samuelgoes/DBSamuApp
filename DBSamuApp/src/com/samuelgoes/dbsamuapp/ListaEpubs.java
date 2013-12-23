package com.samuelgoes.dbsamuapp;

import jarroba.ramon.listado.Lista_adaptador;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nl.siegmann.epublib.domain.Book;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.samuelgoes.dbdropbox.ObtenerListaEpubs;
import com.samuelgoes.dbsamuapp.form.EntradaForm;
import com.samuelgoes.dbsamuapp.form.ListaEpubForm;

public class ListaEpubs extends Activity {

	private final String TAG = "ListaEpubs";
	
	private DropboxAPI<?> _api;
	private ListView lista; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_epubs);
		setupActionBar();
		
		ArrayList<EntradaForm> datos = new ArrayList<EntradaForm>();
		
		ArrayList<Book> libros = obtenerLibros();
		for(Book libro : libros){
			datos.add(new EntradaForm(R.drawable.book, libro.getTitle(), "Samu"));
		}
		
		
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
		        if (entrada != null) {
		            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior); 
		            if (texto_superior_entrada != null) 
		            	texto_superior_entrada.setText(((EntradaForm) entrada).getTitulo()); 

		            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior); 
		            if (texto_inferior_entrada != null)
		            	texto_inferior_entrada.setText(((EntradaForm) entrada).getAutor()); 

		            ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen); 
		            if (imagen_entrada != null)
		            	imagen_entrada.setImageResource(((EntradaForm) entrada).getIdImagen());
		        }
			}
		});

        lista.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				EntradaForm elegido = (EntradaForm) pariente.getItemAtPosition(posicion); 

                CharSequence texto = "Seleccionado: " + elegido.getTitulo();
                Toast toast = Toast.makeText(ListaEpubs.this, texto, Toast.LENGTH_LONG);
                toast.show();
			}
        });
		
		
		
//		LinearLayout rll = (LinearLayout) findViewById(R.id.rll);
//		
//		ArrayList<Book> lista = obtenerLibros();
//		
//		for(Book libro : lista){
//			Button tv = new Button(this);
//			tv.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					
//				}
//			});
//			RelativeLayout rl = new RelativeLayout(this);
//			
//	        tv.setText(libro.getTitle());
//	        tv.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//	        
//	        rl.addView(tv);
//	        rll.addView(rl);
//		}
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_epubs, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	
	public void mostrarCover(){
		Log.i(TAG, "MostrarCover");
	}
	
	
	
	//********************
	//*	METODOS PRIVADOS *
	//********************
	
//    private void addChild(Book libro){
//        LayoutInflater inflater = LayoutInflater.from(this);
//        int id = R.layout.activity_lista_epubs;
//         
//        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(id, null, false);
// 
//        TextView textView = (TextView) relativeLayout.findViewById(R.id.buttonsLayout);
//        textView.setText(libro.getTitle());
//         
//        layout.addView(relativeLayout);     
//         
//    }
	
    
    
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	
	
	private ArrayList<Book> obtenerLibros(){
		ArrayList<Book> lista;
		
		lista = null;
		//_api = ((ListaEpubForm)savedInstanceState.getSerializable("DropboxAPI")).getApi();
		_api = ListaEpubForm.getInstancia().getApi();
		
		try{
			ObtenerListaEpubs ole = new ObtenerListaEpubs(_api);
			ole.execute();
			lista = ole.get();
		}catch(InterruptedException ie){
		}catch(ExecutionException ee){}
		
//		for(Book libro : lista){
//			this.addChild(libro);
//		}
		
		return lista;
	}
	
}
