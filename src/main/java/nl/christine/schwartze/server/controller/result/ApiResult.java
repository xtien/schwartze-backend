package nl.christine.schwartze.server.controller.result;

public class ApiResult {

    public static final int OK = 0;
    public static final int NOT_OK = -1;

    private int resultCode;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResult(int result) {
        this.resultCode = result;
    }
}
