package library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import library.exception.LibraryException;
import library.model.Seat;
import library.model.User;

public class SeatService {
    private final Map<String, Seat> seats = new LinkedHashMap<>();

    public void registerSeat(Seat seat) {
        seats.put(seat.getId(), seat);
    }

    public Seat findById(String id) {
        return seats.get(id);
    }

    public Collection<Seat> findAll() {
        return new ArrayList<>(seats.values());
    }

    public int countTotalSeats() {
        return seats.size();
    }

    public int countReservedSeats() {
        int count = 0;
        for (Seat seat : seats.values()) {
            if (seat.isReserved()) {
                count++;
            }
        }
        return count;
    }

    public int countAvailableSeats() {
        return countTotalSeats() - countReservedSeats();
    }

    public List<Seat> findReservedSeatsByUser(User user) {
        List<Seat> reservedSeats = new ArrayList<>();
        for (Seat seat : seats.values()) {
            if (user.equals(seat.getReservedBy())) {
                reservedSeats.add(seat);
            }
        }
        return reservedSeats;
    }

    public void reserveSeat(String seatId, User user) {
        Seat seat = seats.get(seatId);
        if (seat == null) {
            printFailure("좌석 예약", "존재하지 않는 좌석 ID입니다: " + seatId);
            return;
        }

        try {
            seat.reserve(user);
            printSuccess("좌석 예약", user.getName() + "님이 " + seat.getId() + " 좌석을 예약했습니다.");
        } catch (LibraryException exception) {
            printFailure("좌석 예약", exception.getMessage());
        }
    }

    public void cancelSeat(String seatId, User user) {
        Seat seat = seats.get(seatId);
        if (seat == null) {
            printFailure("좌석 취소", "존재하지 않는 좌석 ID입니다: " + seatId);
            return;
        }

        try {
            seat.cancel(user);
            printSuccess("좌석 취소", user.getName() + "님이 " + seat.getId() + " 좌석 예약을 취소했습니다.");
        } catch (LibraryException exception) {
            printFailure("좌석 취소", exception.getMessage());
        }
    }

    public void printSeats() {
        System.out.println("\n[좌석 목록]");
        for (Seat seat : seats.values()) {
            System.out.println("- " + seat);
        }
    }

    private void printSuccess(String action, String message) {
        System.out.println("[성공][" + action + "] " + message);
    }

    private void printFailure(String action, String message) {
        System.out.println("[실패][" + action + "] " + message);
    }
}
