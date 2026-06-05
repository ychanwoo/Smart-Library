package library.interfaces;

import library.exception.LibraryException;
import library.model.User;

public interface Reservable {
    void reserve(User user) throws LibraryException;

    void cancel(User user) throws LibraryException;
}

