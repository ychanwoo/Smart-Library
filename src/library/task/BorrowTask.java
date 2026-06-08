package library.task;

import library.exception.LibraryException;
import library.model.Book;
import library.model.User;

public class BorrowTask implements Runnable {
    private final Book book;
    private final User user;
    private TaskResult result;

    public BorrowTask(Book book, User user) {
        this.book = book;
        this.user = user;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            book.borrow(user);
            result = new TaskResult(threadName, user.getName(), true, "'" + book.getTitle() + "' 대여 성공");
        } catch (LibraryException exception) {
            result = new TaskResult(threadName, user.getName(), false, exception.getMessage());
        }
    }

    public TaskResult getResult() {
        return result;
    }
}
