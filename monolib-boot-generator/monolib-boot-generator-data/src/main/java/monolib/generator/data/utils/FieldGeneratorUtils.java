package monolib.generator.data.utils;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.experimental.UtilityClass;

import javax.lang.model.element.VariableElement;

@UtilityClass
public class FieldGeneratorUtils {

    public boolean isRelation(VariableElement field) {
        return field.getAnnotation(ManyToOne.class) != null || field.getAnnotation(OneToOne.class) != null;
    }

}
