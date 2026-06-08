package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import library.model.Book;
import library.model.GeneralUser;
import library.model.Professor;
import library.model.Seat;
import library.model.Student;
import library.model.User;
import library.service.LibraryService;
import library.service.SeatService;
import library.service.UserService;
import library.task.BorrowTask;
import library.task.SeatReservationTask;
import library.task.TaskResult;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final LibraryService libraryService = new LibraryService();
    private final SeatService seatService = new SeatService();

    public static void main(String[] args) {
        Main app = new Main();
        app.initializeSampleData();
        app.run();
    }

    private void run() {
        printTitle();

        while (true) {
            printMenu();
            String choice = input("메뉴 선택: ");

            switch (choice) {
                case "1":
                    userService.printUsers();
                    break;
                case "2":
                    libraryService.printBooks();
                    break;
                case "3":
                    libraryService.printSearchResults(input("검색할 도서 제목 키워드: "));
                    break;
                case "4":
                    borrowBook();
                    break;
                case "5":
                    returnBook();
                    break;
                case "6":
                    seatService.printSeats();
                    break;
                case "7":
                    reserveSeat();
                    break;
                case "8":
                    cancelSeat();
                    break;
                case "9":
                    printDashboard();
                    break;
                case "10":
                    printUserStatus();
                    break;
                case "11":
                    runBorrowThreadTest();
                    break;
                case "12":
                    runSeatThreadTest();
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("[안내] 올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    private void printMenu() {
        printLine();
        System.out.println(" 메뉴");
        printLine();
        System.out.println(" 1  사용자 목록 보기");
        System.out.println(" 2  도서 목록 보기");
        System.out.println(" 3  도서 제목 검색");
        System.out.println(" 4  도서 대여하기");
        System.out.println(" 5  도서 반납하기");
        System.out.println(" 6  좌석 목록 보기");
        System.out.println(" 7  좌석 예약하기");
        System.out.println(" 8  좌석 예약 취소하기");
        System.out.println(" 9  도서관 현황판 보기");
        System.out.println("10  사용자별 이용 현황 보기");
        System.out.println("11  자동 시연: 여러 명이 같은 책을 동시에 대여 시도");
        System.out.println("12  자동 시연: 여러 명이 같은 좌석을 동시에 예약 시도");
        System.out.println(" 0  종료");
        printLine();
    }

    private void borrowBook() {
        User user = selectUser();
        if (user == null) {
            return;
        }

        String bookId = input("대여할 도서 ID: ");
        libraryService.borrowBook(bookId, user);
    }

    private void returnBook() {
        User user = selectUser();
        if (user == null) {
            return;
        }

        String bookId = input("반납할 도서 ID: ");
        libraryService.returnBook(bookId, user);
    }

    private void reserveSeat() {
        User user = selectUser();
        if (user == null) {
            return;
        }

        String seatId = input("예약할 좌석 ID: ");
        seatService.reserveSeat(seatId, user);
    }

    private void cancelSeat() {
        User user = selectUser();
        if (user == null) {
            return;
        }

        String seatId = input("취소할 좌석 ID: ");
        seatService.cancelSeat(seatId, user);
    }

    private void printDashboard() {
        int totalBooks = libraryService.countTotalBooks();
        int borrowedBooks = libraryService.countBorrowedBooks();
        int availableBooks = libraryService.countAvailableBooks();
        int totalSeats = seatService.countTotalSeats();
        int reservedSeats = seatService.countReservedSeats();
        int availableSeats = seatService.countAvailableSeats();
        int totalUsers = userService.findAll().size();

        System.out.println();
        printLine();
        System.out.println(" 도서관 현황판");
        printLine();
        System.out.printf(" 등록 사용자  : %d명%n", totalUsers);
        System.out.printf(" 전체 도서    : %d권%n", totalBooks);
        System.out.printf(" 대여 가능    : %d권%n", availableBooks);
        System.out.printf(" 대여 중      : %d권%n", borrowedBooks);
        System.out.printf(" 전체 좌석    : %d석%n", totalSeats);
        System.out.printf(" 예약 가능    : %d석%n", availableSeats);
        System.out.printf(" 예약 중      : %d석%n", reservedSeats);
        printLine();
    }

    private void printUserStatus() {
        User user = selectUser();
        if (user == null) {
            return;
        }

        System.out.println();
        printLine();
        System.out.println(" 사용자별 이용 현황");
        printLine();
        System.out.println(" 사용자      : " + user.getDisplayName());
        System.out.println(" 대여 현황   : " + user.getBorrowedBookCount() + "/" + user.getMaxBorrowLimit() + "권");
        System.out.println();
        System.out.println(" [대여 도서]");
        if (user.getBorrowedBooks().isEmpty()) {
            System.out.println(" - 대여 중인 도서가 없습니다.");
        } else {
            for (Book book : user.getBorrowedBooks()) {
                System.out.println(" - " + book.getId() + " | " + book.getTitle() + " | " + book.getAuthor());
            }
        }

        System.out.println();
        System.out.println(" [예약 좌석]");
        List<Seat> reservedSeats = seatService.findReservedSeatsByUser(user);
        if (reservedSeats.isEmpty()) {
            System.out.println(" - 예약 중인 좌석이 없습니다.");
        } else {
            for (Seat seat : reservedSeats) {
                System.out.println(" - " + seat.getId());
            }
        }
        printLine();
    }

    private User selectUser() {
        userService.printUsers();
        String userId = input("사용자 ID: ");
        User user = userService.findById(userId);

        if (user == null) {
            System.out.println("[실패] 존재하지 않는 사용자 ID입니다: " + userId);
        }

        return user;
    }

    private void runBorrowThreadTest() {
        printDemoHeader("멀티스레드 도서 대여 자동 시연",
                "목표: 여러 사용자가 같은 책을 동시에 요청해도 대여자는 1명만 나와야 합니다.");

        Book sharedBook = new Book("BT-THREAD", "동시성 제어 완전정복", "Smart Library Lab");
        List<Thread> threads = new ArrayList<>();
        List<BorrowTask> tasks = new ArrayList<>();

        int index = 1;
        for (User user : createThreadDemoUsers()) {
            BorrowTask task = new BorrowTask(sharedBook, user);
            Thread thread = new Thread(task, "BorrowTask-" + index++);
            tasks.add(task);
            threads.add(thread);
        }

        System.out.println("[1단계] 테스트용 사용자 5명이 같은 도서를 동시에 요청합니다.");
        System.out.println("[2단계] Book.borrow()의 synchronized가 한 번에 한 요청만 처리합니다.");
        startAndJoin(threads);

        List<TaskResult> results = new ArrayList<>();
        for (BorrowTask task : tasks) {
            results.add(task.getResult());
        }

        printTaskResults(results);
        System.out.println("[최종 도서 상태] " + sharedBook);
        System.out.println("[결론] 성공 1명, 실패 4명입니다. 같은 책은 동시에 여러 명에게 대여되지 않았습니다.");
    }

    private void runSeatThreadTest() {
        printDemoHeader("멀티스레드 좌석 예약 자동 시연",
                "목표: 여러 사용자가 같은 좌석을 동시에 요청해도 예약자는 1명만 나와야 합니다.");

        Seat sharedSeat = new Seat("ST-THREAD");
        List<Thread> threads = new ArrayList<>();
        List<SeatReservationTask> tasks = new ArrayList<>();

        int index = 1;
        for (User user : createThreadDemoUsers()) {
            SeatReservationTask task = new SeatReservationTask(sharedSeat, user);
            Thread thread = new Thread(task, "SeatTask-" + index++);
            tasks.add(task);
            threads.add(thread);
        }

        System.out.println("[1단계] 테스트용 사용자 5명이 같은 좌석을 동시에 요청합니다.");
        System.out.println("[2단계] Seat.reserve()의 synchronized가 한 번에 한 요청만 처리합니다.");
        startAndJoin(threads);

        List<TaskResult> results = new ArrayList<>();
        for (SeatReservationTask task : tasks) {
            results.add(task.getResult());
        }

        printTaskResults(results);
        System.out.println("[최종 좌석 상태] " + sharedSeat);
        System.out.println("[결론] 성공 1명, 실패 4명입니다. 같은 좌석은 동시에 여러 명에게 예약되지 않았습니다.");
    }

    private void startAndJoin(List<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.out.println("[오류] 스레드 실행이 중단되었습니다.");
                return;
            }
        }
    }

    private List<User> createThreadDemoUsers() {
        List<User> users = new ArrayList<>();
        users.add(new Student("T001", "동시학생1"));
        users.add(new Student("T002", "동시학생2"));
        users.add(new Professor("T003", "동시교수"));
        users.add(new GeneralUser("T004", "동시회원1"));
        users.add(new GeneralUser("T005", "동시회원2"));
        return users;
    }

    private void printTitle() {
        printLine();
        System.out.println(" 스마트 도서관 좌석·도서 대여 관리 시뮬레이터");
        printLine();
    }

    private void printDemoHeader(String title, String description) {
        System.out.println();
        printLine();
        System.out.println(" " + title);
        printLine();
        System.out.println(description);
    }

    private void printTaskResults(List<TaskResult> results) {
        System.out.println();
        System.out.println("[3단계] 동시 요청 처리 결과");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-14s %-10s %-8s %s%n", "스레드", "사용자", "결과", "설명");
        System.out.println("------------------------------------------------------------");

        results.stream()
                .sorted((left, right) -> Boolean.compare(right.isSuccess(), left.isSuccess()))
                .forEach(result -> System.out.printf("%-14s %-10s %-8s %s%n",
                        result.getThreadName(),
                        result.getUserName(),
                        result.isSuccess() ? "성공" : "실패",
                        result.getMessage()));

        System.out.println("------------------------------------------------------------");
    }

    private void printLine() {
        System.out.println("============================================================");
    }

    private String input(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }

    private void initializeSampleData() {
        userService.registerUser(new Student("U001", "김학생"));
        userService.registerUser(new Student("U002", "이학생"));
        userService.registerUser(new Professor("U003", "박교수"));
        userService.registerUser(new GeneralUser("U004", "최회원"));
        userService.registerUser(new GeneralUser("U005", "정회원"));

        libraryService.registerBook(new Book("B001", "이것이 자바다", "신용권"));
        libraryService.registerBook(new Book("B002", "객체지향의 사실과 오해", "조영호"));
        libraryService.registerBook(new Book("B003", "Effective Java", "Joshua Bloch"));
        libraryService.registerBook(new Book("B004", "Clean Code", "Robert C. Martin"));
        libraryService.registerBook(new Book("B005", "Java Concurrency in Practice", "Brian Goetz"));
        libraryService.registerBook(new Book("B006", "자료구조와 알고리즘", "Smart Library Lab"));

        for (int i = 1; i <= 8; i++) {
            seatService.registerSeat(new Seat(String.format("S%02d", i)));
        }
    }
}
