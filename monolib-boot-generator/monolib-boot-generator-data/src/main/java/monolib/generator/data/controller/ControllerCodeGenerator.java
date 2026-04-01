package monolib.generator.data.controller;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import monolib.annotations.GenerateController;
import monolib.annotations.generator.CodeGenerator;
import monolib.generator.core.AbstractCodeGenerator;
import monolib.generator.core.GeneratorNameResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

@NoArgsConstructor
@AutoService(CodeGenerator.class)
public class ControllerCodeGenerator extends AbstractCodeGenerator {

    public static final String MONOLIB_DATA_CONTROLLER = "monolib.data.api.controller";

    private GenerateController.Operation currentOperation;

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return GenerateController.class;
    }

    @Override
    public void generate(TypeElement element, ProcessingEnvironment env) {
        for (var operation : getConfig("operations", GenerateController.Operation[].class)) {
            currentOperation = operation;
            super.generate(element, env);
        }
        currentOperation = null;
    }

    @Override
    protected TypeSpec.Builder generateBuilder() {
        return TypeSpec.classBuilder(GeneratorNameResolver.resolveControllerName(getEntity(), currentOperation))
                .addModifiers(Modifier.PUBLIC)
                .superclass(
                        ParameterizedTypeName.get(
                                getOperationHandler(),
                                ClassName.get(getEntity()),
                                ClassName.get(getPackage(getEntity()), GeneratorNameResolver.resolveDtoName(getEntity()))
                        )
                ).addAnnotation(Component.class);
    }

    @Override
    public void builderAdditionals() {
        builder.addField(buildRepositoryField());
    }

    private FieldSpec buildRepositoryField() {
        var fieldClass = ClassName.get(getPackage(getEntity()), GeneratorNameResolver.resolveRepositoryName(getEntity()));
        return FieldSpec.builder(fieldClass, "repository", Modifier.PROTECTED)
                .addAnnotation(Autowired.class)
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get(Getter.class))
                                .addMember("value", "$T.PROTECTED", AccessLevel.class)
                                .build()
                ).build();
    }



    private ClassName getOperationHandler() {
        return switch (currentOperation) {
            case CREATE -> ClassName.get(MONOLIB_DATA_CONTROLLER, "EntityCreateController");
            case READ -> ClassName.get(MONOLIB_DATA_CONTROLLER, "EntityReadController");
            case UPDATE -> ClassName.get(MONOLIB_DATA_CONTROLLER, "EntityUpdateController");
            case DELETE -> ClassName.get(MONOLIB_DATA_CONTROLLER, "EntityDeleteController");
        };
    }

}
