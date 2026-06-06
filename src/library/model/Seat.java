package library.model;

import library.exception.LibraryException;
import library.interfaces.Reservable;

public class Seat implements Reservable {
    private final String id;
    private User reservedBy;

    public Seat(String id) {
        this.id = id;
    }

    @Override
    public synchronized void reserve(User user) throws LibraryException {
        if (reservedBy != null) {
            throw new LibraryException("이미 예약된 좌석입니다. 현재 예약자: " + reservedBy.getName());
        }

        reservedBy = user;
    }

    @Override
    public synchronized void cancel(User user) throws LibraryException {
        if (reservedBy == null) {
            throw new LibraryException("현재 예약된 좌석이 아닙니다.");
        }

        if (!reservedBy.equals(user)) {
            throw new LibraryException("예약 취소 실패: " + reservedBy.getName() + "님이 예약한 좌석입니다.");
        }

        reservedBy = null;
    }

    public String getId() {
        return id;
    }

    public synchronized boolean isReserved() {
        return reservedBy != null;
    }

    public synchronized User getReservedBy() {
        return reservedBy;
    }

    public synchronized String getStatus() {
        return reservedBy == null ? "예약 가능" : "예약 중(" + reservedBy.getName() + ")";
    }

    @Override
    public synchronized String toString() {
        return id + " | " + getStatus();
    }
}
