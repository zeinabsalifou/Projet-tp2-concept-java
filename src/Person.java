import java.util.List;

// classe personne
public class Person {
    @Alias("full_name")
    private String name;
    private int age;
    @Ignored
    private List<String> hobbies;

    public Person() {
    }

    // constructeur
    public Person(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
    }

    // Ajout des méthodes getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
    // methode Tostring
    @Override
    public String toString() {
        String var10000 = this.name;
        return "Person{name='" + var10000 + "', age=" + this.age + ", hobbies=" + hobbies +"}";
    }
}
