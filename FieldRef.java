import java.lang.reflect.Field;

// Classe abstraite représentant une référence à un champ d'un objet.
public abstract class FieldRef {
    private String name;// Le nom du champs
    private Field field; //  // Le champ de l'objet auquel cette référence fait référence

    // Constructeur
    protected FieldRef(Field field) {
        this.field = field;
        this.name = field.getName();// Le nom du champ est initialisé par défaut
        this.field.setAccessible(true);

        // Le nom du champ est initialisé par défaut
        if (field.isAnnotationPresent(Alias.class)) {
            Alias alias = (Alias)field.getAnnotation(Alias.class);
            this.name = alias.value();
        }

    }

    // Getter pour le nom du champ
    public String getName() {
        return this.name;
    }
    // Getter pour le champ Field
    public Field getField() {
        return this.field;
    }

    // Récupère la valeur du champ dans l'instance donnée
    protected Object getValue(Object instance) {
        try {
            return this.field.get(instance);
        } catch (IllegalAccessException e) {
            throw new JsonProcessingException("Error accessing field: " + this.getName(), e);
        }
    }

    // Modifie la valeur du champ dans l'instance donnée
   protected void setValue(Object instance, Object value) {
       try {
           this.field.set(instance, value);// Modifie la valeur du champ dans l'objet
       } catch (IllegalAccessException e) {
           throw new JsonProcessingException("Error setting field: " + this.getName(), e);
       }
   }
    // Méthode abstraite pour sérialiser le champ en JSON
    protected abstract String toJson(Object var1);

    // Méthode abstraite pour désérialiser un champ à partir d'une chaîne JSON (à implémenter dans les classes concrètes)
    public abstract void fromJson(String var1, Object var2);
}

