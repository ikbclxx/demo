package demo_book.demo;

public class Response <T>{


    private  T data;
    private  boolean success;
    private String message;

    public static <K>Response<K> newSuccess(K data){
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static Response<Void> newFail(String errorMsg){
        Response<Void> response = new Response<>();
        response.setMessage(errorMsg);
        response.setSuccess(false);
        return response;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
