package com.samuelgoes.dbsamuapp;

import java.io.IOException;

import com.samuelgoes.dbsamuapp.almacen.AlmacenEbook;

import nl.siegmann.epublib.domain.Book;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MostrarCover extends Activity {

	private final String TAG = "MostrarCover";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Bitmap portada;
		ImageView view;
		Intent intent;
		Book book;
		int posicion;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_cover);
		setupActionBar();
		
		intent = getIntent();
//		book = (Libro) intent.getParcelableExtra("libro");
		posicion = (int) intent.getIntExtra("posicion", -1);
		book = AlmacenEbook.getInstancia().getLibros().get(posicion);
		
		portada = obtenerBitmap(book);
		
		// Assign the bitmap to an ImageView in this layout
		view = (ImageView) findViewById(R.id.portada);
		view.setImageBitmap(portada);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_cover, menu);
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

	
	
    //*******************************
    //*		METODOS PRIVADOS		*
    //*******************************
	
	
//	private Bitmap obtenerBitmap(Libro book){
//		Bitmap portada;
//		
//		portada = null;
//		
//		try{
//			portada = BitmapFactory.decodeStream(new Resource(book.getUrlImg()).getInputStream());
//		}catch(IOException ioe){
//			Log.e(TAG, "Error al obtener la imagen de portada");
//		}
//		
//		return portada;
//	}
	
	
	private Bitmap obtenerBitmap(Book book){
		Bitmap portada;
		
		portada = null;
		
		try{
			portada = BitmapFactory.decodeStream(book.getCoverImage().getInputStream());
		}catch(IOException ioe){
			Log.e(TAG, "Error al obtener la imagen de portada");
		}
		
		return portada;
	}
	
}
