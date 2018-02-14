package chatroom.model;

public class User {

    private String id;

    private String name;

    public User(String name) {
        setId();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        this.id = java.util.UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
