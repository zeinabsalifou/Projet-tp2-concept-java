import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
// Annotation utilisée pour marquer les champs d'une classe qui doivent être ignorés
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignored {
}
