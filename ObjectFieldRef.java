import java.lang.reflect.Field;

// La classe ObjectFieldRef étend FieldRef et est utilisée pour gérer les champs d'objets
// (objets complexes ou personnalisés, non primitifs) pendant la sérialisation et la désérialisation.

public class ObjectFieldRef extends FieldRef {
    private DeSerialisationReflector reflector = new DeSerialisationReflector(this.getField().getType());
    // Création d'un objet DeSerialisationReflector pour gérer la sérialisation et la désérialisation des objets imbriqués
    public ObjectFieldRef(Field field) {
        super(field);
    }

    // methode de serialisation pour un champs d'objet
    protected String toJson(Object instance) {
        Object value = this.getValue(instance);
        String var10000 = this.getName();
        return "\"" + var10000 + "\":" + this.reflector.toJson(value);
    }

    // Méthode de désérialisation pour un champ d'objet
    public void fromJson(String json, Object instance) {
        try {
            Object nestedInstance = this.getField().getType().getDeclaredConstructor().newInstance();
            this.reflector.fromJson(json, nestedInstance);
            this.setValue(instance, nestedInstance);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing field: " + this.getName(), e);
        }
    }
}
