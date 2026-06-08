package library.task;

public class TaskResult {
    private final String threadName;
    private final String userName;
    private final boolean success;
    private final String message;

    public TaskResult(String threadName, String userName, boolean success, String message) {
        this.threadName = threadName;
        this.userName = userName;
        this.success = success;
        this.message = message;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

