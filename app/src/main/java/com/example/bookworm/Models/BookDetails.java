package com.example.bookworm.Models;

public class BookDetails {

    private String bookName;
    private String authorName;
    private String edition;
    private String price;

    public BookDetails(){

    }

    public BookDetails(String bookName, String authorName, String edition, String price){
        this.bookName = bookName;
        this.authorName = authorName;
        this.edition = edition;
        this.price = price;

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
