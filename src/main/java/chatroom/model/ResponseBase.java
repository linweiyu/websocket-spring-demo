package chatroom.model;

public class ResponseBase<T> {
    /**
     * 0 : fail 1 : success
     */
    private int signal;

    private int type;

    private T body;

    public ResponseBase(){}

    public ResponseBase(int signal, int type, T body) {
        this.signal = signal;
        this.type = type;
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
