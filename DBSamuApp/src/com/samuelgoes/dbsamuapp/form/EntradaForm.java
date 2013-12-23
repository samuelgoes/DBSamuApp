package com.samuelgoes.dbsamuapp.form;

public class EntradaForm {

	private int idImagen;
	private String titulo;
	private String autor;
	
	public EntradaForm(int idImagen, String titulo, String autor){
		this.idImagen = idImagen;
		this.titulo = titulo;
		this.autor = autor;
	}

	public int getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
}
