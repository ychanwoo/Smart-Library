package library.model;

import library.exception.LibraryException;
import library.interfaces.Borrowable;

public class Book implements Borrowable {
    private final String id;
    private final String title;
    private final String author;
    private User borrowedBy;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public synchronized void borrow(User user) throws LibraryException {
        if (borrowedBy != null) {
            throw new LibraryException("이미 대여 중인 도서입니다. 현재 대여자: " + borrowedBy.getName());
        }

        if (!user.canBorrowMore()) {
            throw new LibraryException(user.getName() + "님의 대여 한도(" + user.getMaxBorrowLimit() + "권)를 초과했습니다.");
        }

        borrowedBy = user;
        user.addBorrowedBook(this);
    }

    @Override
    public synchronized void returnBook(User user) throws LibraryException {
        if (borrowedBy == null) {
            throw new LibraryException("현재 대여 중인 도서가 아닙니다.");
        }

        if (!borrowedBy.equals(user)) {
            throw new LibraryException("반납 실패: " + borrowedBy.getName() + "님이 대여한 도서입니다.");
        }

        borrowedBy = null;
        user.removeBorrowedBook(this);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public synchronized boolean isBorrowed() {
        return borrowedBy != null;
    }

    public synchronized User getBorrowedBy() {
        return borrowedBy;
    }

    public synchronized String getStatus() {
        return borrowedBy == null ? "대여 가능" : "대여 중(" + borrowedBy.getName() + ")";
    }

    @Override
    public synchronized String toString() {
        return id + " | " + title + " | " + author + " | " + getStatus();
    }
}
