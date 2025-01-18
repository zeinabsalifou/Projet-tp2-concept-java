// classe Json1035
   public class Json1035 {
    // Constructeur
    public Json1035() {
    }

    public String serialize(Object o) {
        // Utilisation de SerialisationReflector pour obtenir la représentation JSON de l'objet
        SerialisationReflector reflector = new SerialisationReflector(o);
        return reflector.toJson(o); // Retourne la chaîne JSON
    }

    // Désérialise une chaîne JSON en un objet de type spécifique.
    public <T> T deserialize(String json, Class<T> toType) {
        try {
            // Création d'une nouvelle instance de l'objet du type spécifié
            T instance = (T)toType.getDeclaredConstructor().newInstance();

            // Utilisation de DeSerialisationReflector pour remplir l'objet avec les valeurs du JSON
            DeSerialisationReflector reflector = new DeSerialisationReflector(instance);
            return (T)reflector.fromJson(json, instance);
        } catch (Exception e) {
            e.printStackTrace();// En cas d'erreur, on affiche l'exception
            return null;
        }
    }

    //Convertit une chaîne en un type spécifique
   private Object convertToType(String value, Class<?> type) {
       // Si le type cible est un Integer
        if (type != Integer.TYPE && type != Integer.class) {
            // Si le type est un Boolean, on convertit la chaîne en boolean, sinon on retourne la valeur brute
            if (type != Double.TYPE && type != Double.class) {
                return type != Boolean.TYPE && type != Boolean.class ? value : Boolean.parseBoolean(value);
            } else {
                // Si le type est un Double, on convertit la chaîne en Double
                return Double.parseDouble(value);
            }
        } else {
            // Si le type est un Integer, on convertit la chaîne en Integer
            return Integer.parseInt(value);
        }
    }



}

