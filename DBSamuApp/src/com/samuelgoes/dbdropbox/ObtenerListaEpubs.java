package com.samuelgoes.dbdropbox;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

public class ObtenerListaEpubs extends AsyncTask<Void, Void, ArrayList<Book>> {

	private final static String TAG = "ObtenerListaEpubs";
	
	private DropboxAPI<?> _api;
	private ArrayList<Book> _lista;
	
	public ObtenerListaEpubs(DropboxAPI<?> api){
		_api = api;
	}
	
	
	@Override
	protected ArrayList<Book> doInBackground(Void... params) {
		
		ArrayList<Book> lista;
		Entry entry;
		Book libro;
		InputStream dip;
		
		entry = null;
		libro = null;
		lista = new ArrayList<Book>();
		
		try{
			entry = _api.metadata("/EPUB", 100, null, true, null);
		
	        for (Entry ent: entry.contents){
	        	if(ent.mimeType.equals("application/epub+zip")){
	        		Log.i(TAG, "Fichero: " + ent.fileName());
	        		
	        		dip = _api.getFileStream("/EPUB/" + ent.fileName(), null);
	        		libro = (new EpubReader()).readEpub(dip);
	        		lista.add(libro);
	        	}
	        }
        
		}catch(DropboxException de){
			Log.e(TAG, "Error al obtener ficheros");
		}catch(IOException ioe){
			Log.e(TAG, "Error al leer el EPUB");
		}

        return lista;
	}
	
	
	
    @Override
    protected void onPostExecute(ArrayList<Book> result) {
    	_lista = result;
    }
	
}
