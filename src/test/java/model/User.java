package model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String id;
    private String type;
    private HashMap<String, String> attributes;

    public User(String id, String name, String age, String gender) {
        this.id = id;
        this.type = "people";

        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("age", age);
        data.put("gender", gender);

        this.attributes = data;



    }

    public User(String name) {
        this.type = "people";

        HashMap<String, String> data = new HashMap<>();
        data.put("name", name);

        this.attributes = data;



    }


    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(type, user.type) && Objects.equals(attributes, user.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, attributes);
    }
}
