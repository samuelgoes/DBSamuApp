package com.samuelgoes.dbsamuapp.almacen;

import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;

public class AlmacenEbook {

	private static AlmacenEbook _instancia;
	private ArrayList<Book> _libros;
	
	private AlmacenEbook(){
		_libros = new ArrayList<Book>();
	}
	
	public static AlmacenEbook getInstancia(){
		if(_instancia == null)
			_instancia = new AlmacenEbook();
		
		return _instancia;
	}

	public ArrayList<Book> getLibros() {
		return _libros;
	}

	public void setLibros(ArrayList<Book> libros) {
		this._libros = libros;
	}
	
}
