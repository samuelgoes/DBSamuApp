package com.samuelgoes.dbepub;

import java.io.IOException;

import nl.siegmann.epublib.domain.Book;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class MostrarCover extends AsyncTask<Book, Void, Bitmap> {

	private final static String TAG = "MostrarCover";
	private ImageView _coverView;
	
	
	@Override
	protected Bitmap doInBackground(Book... libros) {
		
		Book libro;
		Bitmap portada;
		
		libro = libros[0];
		portada = null;
		
		try{
			portada = BitmapFactory.decodeStream(libro.getCoverImage().getInputStream());
		}catch(IOException ioe){
			Log.e(TAG, "Error al mostrar la imagen");
		}
		
		return portada;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		_coverView.setImageBitmap(result);
	}
	
}
