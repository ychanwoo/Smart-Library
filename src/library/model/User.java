package library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class User {
    private final String id;
    private final String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    protected User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract int getMaxBorrowLimit();

    public abstract String getUserType();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public synchronized boolean canBorrowMore() {
        return borrowedBooks.size() < getMaxBorrowLimit();
    }

    public synchronized void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public synchronized void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public synchronized int getBorrowedBookCount() {
        return borrowedBooks.size();
    }

    public synchronized List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(new ArrayList<>(borrowedBooks));
    }

    public String getDisplayName() {
        return "[" + getUserType() + "] " + name + "(" + id + ")";
    }

    @Override
    public String toString() {
        return getDisplayName() + " - 대여 " + getBorrowedBookCount() + "/" + getMaxBorrowLimit() + "권";
    }
}

