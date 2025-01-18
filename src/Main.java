import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Json1035 jsonProcessor = new Json1035();

        // Test de sérialisation
        Person person = new Person("zeinab", 18, Arrays.asList("sport", "lecture", "voyage"));
        String json = jsonProcessor.serialize(person);
        System.out.println("Serialized JSON:");
        System.out.println(json);

        // Vérification des résultats de la sérialisation
       System.out.println(json.contains("\"full_name\":\"zeinab\"") ? "Test Passed" : "Test Failed");
       System.out.println(json.contains("\"age\":\"18\"") ? "Test Passed" : "Test Failed");

        // Test de désérialisation
        Person deserializedPerson = jsonProcessor.deserialize(json, Person.class);
        System.out.println("\nDeserialized Object:");
        System.out.println(deserializedPerson);

        // Vérification des résultats de la désérialisation
        System.out.println("zeinab".equals(deserializedPerson.getName()) ? "Test Passed" : "Test Failed");
        System.out.println(deserializedPerson.getAge() == 18? "Test Passed" : "Test Failed");
        System.out.println(deserializedPerson.getHobbies() == null ? "Test Passed" : "Test Failed"); // hobbies doivent etre ignorés


    }
    }

