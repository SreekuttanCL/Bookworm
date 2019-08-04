package com.example.bookworm.Models;

import android.widget.ImageView;

public class BookDetails {

    private ImageView bookImage;
    private String bookName;
    private String authorName;
    private String edition;
    private String price;

    public BookDetails(){

    }

    public BookDetails(ImageView bookImage, String bookName, String authorName, String edition, String price){
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.authorName = authorName;
        this.edition = edition;
        this.price = price;

    }

    public ImageView getBookImage() {
        return bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getEdition() {
        return edition;
    }

    public String getPrice() {
        return price;
    }
}
