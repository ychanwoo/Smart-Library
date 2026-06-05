package library.model;

public class Professor extends User {
    public Professor(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBorrowLimit() {
        return 10;
    }

    @Override
    public String getUserType() {
        return "교수";
    }
}

