package monolib.generator.data.dto;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import monolib.data.api.annotation.Field;
import monolib.generator.core.AbstractGeneratorContext;
import monolib.generator.core.GeneratorContext;
import monolib.generator.data.utils.FieldGeneratorUtils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

public class DtoConstructorGenerator extends AbstractGeneratorContext {

    private final DtoConstructorFieldBuilder fieldBuilder;
    private final TypeSpec.Builder typeBuilder;
    private MethodSpec.Builder constructor;

    public DtoConstructorGenerator(GeneratorContext context, TypeSpec.Builder typeBuilder) {
        super(context);
        this.typeBuilder = typeBuilder;
        fieldBuilder = new DtoConstructorFieldBuilder(context);
    }

    public MethodSpec generate() {
        constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(getEntity()), "entity")
                .addStatement("super(entity)");

        for (var element : getEntity().getEnclosedElements()) {
            if (element.getKind() != ElementKind.FIELD) {
                continue;
            }
            generateField((VariableElement) element);

        }
        return constructor.build();
    }

    private void generateField(VariableElement field) {
        if (ignoreField(field) || !hasValidFieldAnnotation(field)) {
            return;
        }

        var fieldSpec = fieldBuilder.build(field);
        if (fieldSpec != null) {
            typeBuilder.addField(fieldSpec);
            addConstructorMapping(field, fieldSpec.type);
        }
    }

    private boolean ignoreField(VariableElement field) {
        return field.getModifiers().contains(Modifier.STATIC) || field.getModifiers().contains(Modifier.TRANSIENT);
    }

    private boolean hasValidFieldAnnotation(VariableElement field) {
        var fieldAnnotation = field.getAnnotation(Field.class);
        return fieldAnnotation != null && fieldAnnotation.accessible();
    }

    private String capitalize(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private void addConstructorMapping(VariableElement field, TypeName dtoType) {
        var name = field.getSimpleName().toString();
        var getter = resolveGetter(field);

        if (dtoType instanceof ParameterizedTypeName ptn) {
            var argType = ptn.typeArguments.getFirst();
            constructor.addStatement(
                    "this.$L = entity.$L() != null ? entity.$L().stream().map($T::new).toList() : null",
                    name,
                    getter,
                    getter,
                    argType
            );
        } else if (FieldGeneratorUtils.isRelation(field)) {
            constructor.addStatement(
                    "this.$L = java.util.Optional.ofNullable(entity.$L()).map($T::new).orElse(null)",
                    name,
                    getter,
                    dtoType
            );
        } else {
            constructor.addStatement(
                    "this.$L = entity.$L()",
                    name,
                    getter
            );
        }
    }

    private String resolveGetter(VariableElement field) {
        var name = capitalize(field.getSimpleName().toString());
        return resolveGetterPrefix(field) + name;
    }

    private static String resolveGetterPrefix(VariableElement field) {
        if (field.asType().getKind() == TypeKind.BOOLEAN) {
            return "is";
        }
        return "get";
    }

}
