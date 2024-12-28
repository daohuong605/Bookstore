package application;

import javafx.beans.property.*;

public class OrderDetail {

    private StringProperty bookTitle;
    private IntegerProperty quantity;
    private DoubleProperty price;
    private DoubleProperty subtotal;

    public OrderDetail(String bookTitle, int quantity, double price, double subtotal) {
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.subtotal = new SimpleDoubleProperty(subtotal);
    }

    public StringProperty bookTitleProperty() {
        return bookTitle;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public DoubleProperty subtotalProperty() {
        return subtotal;
    }

    // Getters and Setters
    public String getBookTitle() {
        return bookTitle.get();
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.set(bookTitle);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getSubtotal() {
        return subtotal.get();
    }

    public void setSubtotal(double subtotal) {
        this.subtotal.set(subtotal);
    }
}
