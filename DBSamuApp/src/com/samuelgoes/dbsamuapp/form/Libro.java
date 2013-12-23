package com.samuelgoes.dbsamuapp.form;

import nl.siegmann.epublib.domain.Resource;
import android.os.Parcel;
import android.os.Parcelable;

public class Libro implements Parcelable {

	private String titulo;
	private String autor;
	private String urlImg;
	
	public static final Parcelable.Creator<Libro> CREATOR = new Parcelable.Creator<Libro>(){

	    @Override
	    public Libro createFromParcel(Parcel source) {
	    	return new Libro(source);
	    }

	    @Override
	    public Libro[] newArray(int size) {
	    	return new Libro[size];
	    }
	};
	
	public Libro(){}
	
	public Libro(Parcel source){
		readFromParcel(source);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(titulo);
		dest.writeString(autor);
		dest.writeString(urlImg);
	}
	
	public void readFromParcel(Parcel source){
		titulo = source.readString();
		autor = source.readString();
		urlImg = source.readString();
	}
	
	

	//*********************
	//*  GETTER & SETTER  *
	//*********************
	
	
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

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

}
