package junit.tutorial.exam.register;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisteredPerson {

    private final String firstName;
    private final String lastName;
    private final LocalDate registeredDate;

    public RegisteredPerson(String firstName, String lastName, LocalDate registeredDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredDate = registeredDate;
    }
}
