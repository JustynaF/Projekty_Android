package justynafirkowska.datacollector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private Date birth;

    public Person(int id, String fName, String lName, Date birth) {
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd-MM-yyyy");
        return this.firstName + " " + this.lastName + ": " + simpleDate.format(this.birth);
    }
}
