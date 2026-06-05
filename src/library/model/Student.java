package library.model;

public class Student extends User {
    public Student(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBorrowLimit() {
        return 3;
    }

    @Override
    public String getUserType() {
        return "학생";
    }
}

