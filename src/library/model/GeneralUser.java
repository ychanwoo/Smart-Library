package library.model;

public class GeneralUser extends User {
    public GeneralUser(String id, String name) {
        super(id, name);
    }

    @Override
    public int getMaxBorrowLimit() {
        return 2;
    }

    @Override
    public String getUserType() {
        return "일반회원";
    }
}

