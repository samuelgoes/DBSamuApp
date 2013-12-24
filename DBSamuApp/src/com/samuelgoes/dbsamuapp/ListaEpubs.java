package com.samuelgoes.dbsamuapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import nl.siegmann.epublib.domain.Book;
import android.annotation.TargetApi;
import android.app.ActionBar;
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
import android.widget.Spinner;

import com.samuelgoes.dbsamuapp.almacen.AlmacenEbook;
import com.samuelgoes.dbsamuapp.form.LibrosAdapter;

public class ListaEpubs extends Activity implements OnItemClickListener{

	private final String TAG = "ListaEpubs";
	private final int ORDENACION_NOMBRE = 0;
	private final int ORDENACION_FECHA = 1;
	
	final String DATE_FORMAT = "yyyy-MM-dd";
	
	private ArrayList<Book> libros;
	private ListView lvLibros;
	private ArrayAdapter<Book> adapter;
	private Spinner menu_desplegable;
	private int ordenacion;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ArrayAdapter<CharSequence> adaptador ;
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_lista_epubs);
		setupActionBar();
		
		adaptador = ArrayAdapter.createFromResource(this, R.array.valores_spinner, android.R.layout.simple_spinner_item);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		menu_desplegable = (Spinner) findViewById(R.id.spinner_orden);
		menu_desplegable.setOnItemSelectedListener(
		        new AdapterView.OnItemSelectedListener() {
			        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
			            if(position == ORDENACION_NOMBRE){
			            	ordenacion = ORDENACION_NOMBRE;
			            }else if(position == ORDENACION_FECHA){
			            	ordenacion = ORDENACION_FECHA;
			            }
			            
			            refrescar();
			        }
			 
			        public void onNothingSelected(AdapterView<?> parent) {}
		});
		menu_desplegable.setAdapter(adaptador);
		
		//Obtenemos los datos de los libros
		//libros = this.obtenerLibros();
		libros = new ArrayList<Book>();

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
	
	
//	@Override
//	protected void onResume() {
//		iniciado = true;
//		super.onResume();
//	}

//	@Override
//	protected void onRestart() {
//		Intent i;
//		
//	    super.onRestart();
//	    i = new Intent(ListaEpubs.this, ListaEpubs.class);
//	    i.putExtra("ordenacion", ordenacion);
//	    startActivity(i);
//	    finish();
//	}
	
	
	
	//********************
	//*	METODOS PRIVADOS *
	//********************

	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar;
			actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
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
		//Intent intent;
		//int orden;
		
		//intent = getIntent();
		//orden = intent.getIntExtra("ordenacion", ORDENACION_NOMBRE);
		lista = AlmacenEbook.getInstancia().getLibros();
		
		try{
			lista = ordenarLista(lista, ordenacion);
		}catch(ParseException pe){
			Log.e(TAG, "Error de parseo con la fecha");
		}
		
		return lista;
	}
	
	
	private ArrayList<Book> ordenarLista(ArrayList<Book> lista, int ordenacion) throws ParseException{
		ArrayList<Book> listaOrdenada;
		SimpleDateFormat sdf;
		String t1, t2;
		Date d1, d2;
		int i;
		boolean insertado;
		
		listaOrdenada = new ArrayList<Book>();
		sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		insertado = false;
		
		for(Book b : lista){
			i = 0;
			insertado = false;
			for(Book n : listaOrdenada){
				if(ordenacion == ORDENACION_NOMBRE){
					t1 = b.getTitle();
					t2 = n.getTitle();
					
					if(t1.compareTo(t2) < 0){
						listaOrdenada.add(i, b);
						insertado = true;
					}
				}else if(ordenacion == ORDENACION_FECHA){
					d1 = sdf.parse(b.getMetadata().getDates().get(0).getValue());
					d2 = sdf.parse(n.getMetadata().getDates().get(0).getValue());
					
					if(d1.compareTo(d2) < 0){
						listaOrdenada.add(i, b);
						insertado = true;
					}
				}
				
				i++;
			}
			
			if(!insertado)
				listaOrdenada.add(b);
		}
		
		return listaOrdenada;
	}
	
	
	
	private void refrescar(){
		ArrayList<Book> newItems = this.obtenerLibros();
        adapter.clear();
        adapter.addAll(newItems);
        adapter.notifyDataSetChanged();
	}
	
}
