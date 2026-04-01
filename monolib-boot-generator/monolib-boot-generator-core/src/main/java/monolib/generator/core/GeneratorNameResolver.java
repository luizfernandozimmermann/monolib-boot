package monolib.generator.core;

import lombok.experimental.UtilityClass;
import monolib.annotations.GenerateController;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.TypeElement;

@UtilityClass
public class GeneratorNameResolver {

    public static final String ENTITY = "Entity";

    public static String resolveDtoName(TypeElement element) {
        return resolveElementName(element).replace(ENTITY, "Dto");
    }

    public static String resolveRepositoryName(TypeElement element) {
        return resolveElementName(element).replace(ENTITY, "BaseRepository");
    }

    public static String resolveCRUDServiceName(TypeElement element) {
        return resolveElementName(element).replace(ENTITY, "CRUDService");
    }

    public static String resolveControllerName(TypeElement element, GenerateController.Operation operation) {
        return resolveElementName(element).replace(ENTITY, StringUtils.capitalize(operation.name().toLowerCase()) + "Controller");
    }

    public static String resolveHandlerName(TypeElement element, GenerateController.Operation operation) {
        return resolveElementName(element).replace(ENTITY, StringUtils.capitalize(operation.name().toLowerCase()) + "Handler");
    }

    public static String resolveCapitalizedEntityName(TypeElement element) {
        return StringUtils.uncapitalize(resolveElementName(element)).replace(ENTITY, StringUtils.EMPTY);
    }

    private static String resolveElementName(TypeElement element) {
        return element.getSimpleName().toString();
    }
}
