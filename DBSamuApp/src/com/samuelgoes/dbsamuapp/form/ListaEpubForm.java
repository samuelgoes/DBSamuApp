package com.samuelgoes.dbsamuapp.form;

import com.dropbox.client2.DropboxAPI;

public class ListaEpubForm {

	private DropboxAPI<?> api;
	private static ListaEpubForm _instancia;
	
	private ListaEpubForm(){}
	
	public static ListaEpubForm getInstancia(){
		if(_instancia == null){
			_instancia = new ListaEpubForm();
		}
		
		return _instancia;
	}
	

	public DropboxAPI<?> getApi() {
		return api;
	}

	public void setApi(DropboxAPI<?> api) {
		this.api = api;
	}
	
}
