package library.task;

import library.exception.LibraryException;
import library.model.Seat;
import library.model.User;

public class SeatReservationTask implements Runnable {
    private final Seat seat;
    private final User user;
    private TaskResult result;

    public SeatReservationTask(Seat seat, User user) {
        this.seat = seat;
        this.user = user;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            seat.reserve(user);
            result = new TaskResult(threadName, user.getName(), true, seat.getId() + " 좌석 예약 성공");
        } catch (LibraryException exception) {
            result = new TaskResult(threadName, user.getName(), false, exception.getMessage());
        }
    }

    public TaskResult getResult() {
        return result;
    }
}
