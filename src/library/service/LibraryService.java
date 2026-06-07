package library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import library.exception.LibraryException;
import library.model.Book;
import library.model.User;

public class LibraryService {
    private final Map<String, Book> books = new LinkedHashMap<>();

    public void registerBook(Book book) {
        books.put(book.getId(), book);
    }

    public Book findById(String id) {
        return books.get(id);
    }

    public Collection<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public int countTotalBooks() {
        return books.size();
    }

    public int countBorrowedBooks() {
        int count = 0;
        for (Book book : books.values()) {
            if (book.isBorrowed()) {
                count++;
            }
        }
        return count;
    }

    public int countAvailableBooks() {
        return countTotalBooks() - countBorrowedBooks();
    }

    public List<Book> searchByTitle(String keyword) {
        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        List<Book> results = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase(Locale.ROOT).contains(normalizedKeyword)) {
                results.add(book);
            }
        }

        return results;
    }

    public void borrowBook(String bookId, User user) {
        Book book = books.get(bookId);
        if (book == null) {
            printFailure("도서 대여", "존재하지 않는 도서 ID입니다: " + bookId);
            return;
        }

        try {
            book.borrow(user);
            printSuccess("도서 대여", user.getName() + "님이 '" + book.getTitle() + "' 도서를 대여했습니다.");
        } catch (LibraryException exception) {
            printFailure("도서 대여", exception.getMessage());
        }
    }

    public void returnBook(String bookId, User user) {
        Book book = books.get(bookId);
        if (book == null) {
            printFailure("도서 반납", "존재하지 않는 도서 ID입니다: " + bookId);
            return;
        }

        try {
            book.returnBook(user);
            printSuccess("도서 반납", user.getName() + "님이 '" + book.getTitle() + "' 도서를 반납했습니다.");
        } catch (LibraryException exception) {
            printFailure("도서 반납", exception.getMessage());
        }
    }

    public void printBooks() {
        System.out.println("\n[도서 목록]");
        for (Book book : books.values()) {
            System.out.println("- " + book);
        }
    }

    public void printSearchResults(String keyword) {
        List<Book> results = searchByTitle(keyword);
        System.out.println("\n[도서 검색 결과] keyword = " + keyword);

        if (results.isEmpty()) {
            System.out.println("- 검색 결과가 없습니다.");
            return;
        }

        for (Book book : results) {
            System.out.println("- " + book);
        }
    }

    private void printSuccess(String action, String message) {
        System.out.println("[성공][" + action + "] " + message);
    }

    private void printFailure(String action, String message) {
        System.out.println("[실패][" + action + "] " + message);
    }
}
