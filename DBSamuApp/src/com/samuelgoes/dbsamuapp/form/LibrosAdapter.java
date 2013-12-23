package com.samuelgoes.dbsamuapp.form;

import java.util.ArrayList;

import nl.siegmann.epublib.domain.Book;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samuelgoes.dbsamuapp.R;

public class LibrosAdapter extends ArrayAdapter<Book> {

	private Context context;
	private ArrayList<Book> datos;

	public LibrosAdapter(Context context, ArrayList<Book> datos) {
		super(context, R.layout.listview_item, datos);
		
		this.context = context;
		this.datos = datos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View item;
		ImageView imagen;
		TextView titulo, autor;
		
		// En primer lugar "inflamos" una nueva vista, que sera la que se mostrara en la celda del ListView.
		item = LayoutInflater.from(context).inflate(R.layout.listview_item, null);

		// A partir de la vista, se recogen cada uno de los elementos que se van a utilizar
		imagen = (ImageView) item.findViewById(R.id.imgTomoLibro);
		titulo = (TextView) item.findViewById(R.id.tvTitulo);
		autor = (TextView) item.findViewById(R.id.tvAutor);
		
		// Se le dan valores a los dintintos elementos de la lista
		imagen.setImageResource(R.drawable.book);
		
//		titulo.setText(datos.get(position).getTitulo());
//		autor.setText(datos.get(position).getAutor());
		titulo.setText(datos.get(position).getTitle());
		autor.setText(datos.get(position).getMetadata().getAuthors().get(0).getLastname());

		// Devolvemos la vista para que se muestre en el ListView.
		return item;
	}

}
