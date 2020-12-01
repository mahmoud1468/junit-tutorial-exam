package junit.tutorial.exam.register;

import junit.tutorial.exam.processor.file.FileProcessor;
import junit.tutorial.exam.processor.file.exception.FileProcessingException;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationSystem implements FileProcessor {

    // name-date, where:
    //      - name is concatenation of first name and last name (each starting with capital letters)
    //      - date is an 8-digit number, representing year (4 digits), month (2 digits), and day (2 digits).
    // A valid example is: ArashKamngir-20201201
    private static final String REGISTRATION_FILE_NAME_PATTERN = "([A-Z][a-z]{1,}){2}\\-\\d{8}";

    private Map<String, RegisteredPerson> registeredPersons = new HashMap<>();

    @Override
    public void process(File file) throws FileProcessingException {
        try{
           registerFrom(file.getName());
        }
        catch (Exception e){
            throw new FileProcessingException(e);
        }
    }

    public void registerFrom(String fileName) {
        validateFileName(fileName);
        String firstName = extractFirstName(fileName);
        String lastName = extractLastName(fileName);
        LocalDate date = extractRegistrationDate(fileName);
        RegisteredPerson person = new RegisteredPerson(firstName, lastName, date);
        addToRegistered(person);
    }

    public boolean isRegistered(String firstName, String lastName){
        String key = createKeyFrom(firstName, lastName);
        return registeredPersons.containsKey(key);
    }

    public LocalDate getRegistrationDate (String firstName, String lastName){
        String key = createKeyFrom(firstName, lastName);
        RegisteredPerson registeredPerson = registeredPersons.get(key);
        return registeredPerson != null ? registeredPerson.getRegisteredDate() : null;
    }

    private void addToRegistered(RegisteredPerson person) {
        String key = createKeyFrom(person);
        if (!isRegisteredBeforeLastRegistration(person))
            registeredPersons.put(key, person);
    }

    private String createKeyFrom(RegisteredPerson person) {
        return createKeyFrom(person.getFirstName(), person.getLastName());
    }

    private String createKeyFrom(String firstName, String lastName){
        return firstName + lastName;
    }

    private boolean isRegisteredBeforeLastRegistration(RegisteredPerson person) {
        String key = createKeyFrom(person);
        return registeredPersons.containsKey(key) &&
                registeredPersons.get(key).getRegisteredDate().isAfter(person.getRegisteredDate());
    }

    private LocalDate extractRegistrationDate(String fileName) {
        int index = fileName.indexOf("-");
        int year = Integer.parseInt(fileName.substring(index+1,index+5));
        int month = Integer.parseInt(fileName.substring(index+5,index+7));
        int day = Integer.parseInt(fileName.substring(index+7,index+9));
        return LocalDate.of(year, month, day);
    }

    private String extractLastName(String fileName) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]{1,}");
        Matcher matcher = pattern.matcher(fileName);
        matcher.find();
        matcher.find();
        return matcher.group(0);
    }

    private String extractFirstName(String fileName) {
        Pattern pattern = Pattern.compile("[A-Z][a-z]{1,}");
        Matcher matcher = pattern.matcher(fileName);
        matcher.find();
        return matcher.group(0);
    }

    private void validateFileName(String fileName) {
        if (fileName==null)
            throw new IllegalArgumentException("fileName is null!");
        if (!fileName.matches(REGISTRATION_FILE_NAME_PATTERN))
            throw new InvalidRegistrationFileNameException("fileName does not comply with the required format");
    }
}
