package monolib.generator.data.dto;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import monolib.annotations.GenerateDto;
import monolib.data.api.annotation.Field;
import monolib.generator.core.AbstractGeneratorContext;
import monolib.generator.core.AnnotationWrapper;
import monolib.generator.core.GeneratorContext;
import monolib.generator.core.GeneratorNameResolver;
import monolib.generator.data.utils.FieldGeneratorUtils;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

public class DtoConstructorFieldBuilder extends AbstractGeneratorContext {

    private final DtoConstructorFieldAnnotationBuilder annotationBuilder;

    public DtoConstructorFieldBuilder(GeneratorContext context) {
        super(context);
        annotationBuilder = new DtoConstructorFieldAnnotationBuilder(context);
    }

    public FieldSpec build(VariableElement field) {
        var type = resolveDtoType(field);
        if (type == null) {
            return null;
        }
        return build(field, type);
    }

    private FieldSpec build(VariableElement field, TypeName type) {
        var name = field.getSimpleName().toString();
        var builder = FieldSpec.builder(type, name, Modifier.PRIVATE);
        addValidationAnnotations(builder, field);
        return builder.build();
    }

    private void addValidationAnnotations(FieldSpec.Builder fieldBuilder, VariableElement field) {
        for (var mirror : field.getAnnotationMirrors()) {
            var annotationType = mirror.getAnnotationType().toString();
            if (!annotationType.equals(Field.class.getName())) {
                continue;
            }
            fieldBuilder.addAnnotations(annotationBuilder.build(mirror));
        }
    }

    private TypeName resolveDtoType(VariableElement field) {
        if (FieldGeneratorUtils.isRelation(field)) {
            return resolveRelation(field);
        }
        if (isCollection(field)) {
            return resolveCollection(field);
        }
        return TypeName.get(field.asType());
    }

    private ParameterizedTypeName resolveCollection(VariableElement field) {
        var argClass = getFieldTypeElement(field);

        if (argClass == null || !AnnotationWrapper.of(argClass, GenerateDto.class).isPresent()) {
            return null;
        }

        var dtoType = GeneratorNameResolver.resolveDtoName(argClass);
        return ParameterizedTypeName.get(
                ClassName.get(List.class),
                ClassName.get(getPackage(argClass), dtoType)
        );
    }

    private TypeElement getFieldTypeElement(VariableElement field) {
        var declaredType = (DeclaredType) field.asType();
        var arg = declaredType.getTypeArguments().getFirst();
        return (TypeElement) getEnv().getTypeUtils().asElement(arg);
    }

    private ClassName resolveRelation(VariableElement field) {
        var fieldClass = (TypeElement) getEnv().getTypeUtils().asElement(field.asType());

        if (fieldClass == null || !AnnotationWrapper.of(fieldClass, GenerateDto.class).isPresent()) {
            return null;
        }
        var dtoType = GeneratorNameResolver.resolveDtoName(fieldClass);

        return ClassName.get(getPackage(fieldClass), dtoType);
    }

    private boolean isCollection(VariableElement field) {
        return field.getAnnotation(OneToMany.class) != null || field.getAnnotation(ManyToMany.class) != null;
    }

}
