package com.example.bookworm.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookworm.Models.BookDetails;
import com.example.bookworm.R;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<BookDetails> {

    private Activity context;
    List<BookDetails> books;

    //constructor that inherits from the super
    public BookListAdapter(Activity context, List<BookDetails> books){
        super(context, R.layout.book_list, books);
        this.context = context;
        this.books = books;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        //this method will instantiate our layout file from the layout_artist_list.xml into
        //our view objects


        View v = convertView;
        if(v==null){
            LayoutInflater inflater = context.getLayoutInflater();
            v = inflater.inflate(R.layout.book_list
                    , null
                    , true);
        }


        BookDetails book = getItem(position);

        if(book != null) {
            ImageView bookImage = v.findViewById(R.id.imageView6);
            TextView textViewBookName = v.findViewById(R.id.booknamelist);
            TextView textViewAuthor = v.findViewById(R.id.authorlist);
            TextView textViewEdition = v.findViewById(R.id.editionList);
            TextView textViewPrice = v.findViewById(R.id.pricelist);


            bookImage.setImageResource(R.drawable.bookwormicon);
            textViewBookName.setText(book.getBookName());
            textViewAuthor.setText(book.getAuthorName());
            textViewEdition.setText(book.getEdition());
            textViewPrice.setText(book.getPrice());
        }
        //replace later
        return v;
    }

}
