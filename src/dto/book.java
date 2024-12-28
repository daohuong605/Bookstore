package dto;

public class book {
    private String bookid;
    private String genreid;
    private String author;
    private String title;
    private String publisher;
    private String price;
    private String stock;
    private String img;

    public book(String Bookid, String Genreid, String Author, String Title, String Publisher, String Price, String Stock, String img) {
        this.bookid = Bookid;
        this.genreid = Genreid;
        this.author = Author;
        this.title = Title;
        this.publisher = Publisher;
        this.price = Price;
        this.stock = Stock;
        this.img = img;
    }


    public String getBookid() {
        return bookid;
    }

    public String getGenreid() {
        return genreid;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPrice() {
        return price;
    }

    public String getStock() {
        return stock;
    }
    
    public String getImg() {
        return img;
    }
    
    public void setImg(String img) {
        this.img = img;
    }
}
