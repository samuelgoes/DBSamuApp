package com.samuelgoes.dbsamuapp;

import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.samuelgoes.dbsamuapp.almacen.AlmacenEbook;
import com.samuelgoes.dbsamuapp.form.LibrosAdapter;

public class ListaEpubs extends Activity implements OnItemClickListener{

	private final String TAG = "ListaEpubs";
	
	private ArrayList<Book> libros;
	private ListView lvLibros;
	private ArrayAdapter<Book> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_epubs);
		setupActionBar();
		
		//Obtenemos los datos de los libros
		libros = this.obtenerLibros();

		// Creamos el adaptador. Almacena y maqueta los datos mostrados en el ListView
		adapter = new LibrosAdapter(this, libros);
		
		// Recuperamos el ListView
		lvLibros = (ListView) findViewById(R.id.lvItems);
		lvLibros.setAdapter(adapter);							// Asignamos el Adapter al ListView. El adapter hace de plantilla
		lvLibros.setOnItemClickListener(this);					// Asignamos el Listener al ListView para cuando pulsamos sobre uno de los items
	}

	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long ID) {
		Intent intent;
		
		Log.i(TAG, libros.get(position).getTitle());
    	
    	intent = new Intent(this, MostrarCover.class);
//    	intent.putExtra("libro", libros.get(position));
    	intent.putExtra("posicion", position);
    	
        startActivity(intent);
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
	
	
	
	//********************
	//*	METODOS PRIVADOS *
	//********************

	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	
	
//	@SuppressWarnings("unchecked")
//	private ArrayList<Libro> obtenerLibros(){
//		ArrayList<Libro> lista;
//		Intent intent;
//		
//		intent = getIntent();
//		lista = (ArrayList<Libro>) intent.getSerializableExtra("listaLibros");
//		
//		return lista;
//	}
	
	private ArrayList<Book> obtenerLibros(){
		ArrayList<Book> lista;
		
		lista = AlmacenEbook.getInstancia().getLibros();
		
		return lista;
	}
	
}
