package library.interfaces;

import library.exception.LibraryException;
import library.model.User;

public interface Borrowable {
    void borrow(User user) throws LibraryException;

    void returnBook(User user) throws LibraryException;
}

