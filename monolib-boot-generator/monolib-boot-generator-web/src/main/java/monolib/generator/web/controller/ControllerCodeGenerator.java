package monolib.generator.web.controller;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import lombok.NoArgsConstructor;
import monolib.annotations.GenerateController;
import monolib.annotations.generator.CodeGenerator;
import monolib.generator.core.AbstractCodeGenerator;
import monolib.generator.core.GeneratorNameResolver;
import monolib.web.api.EntityHandler;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

@NoArgsConstructor
@AutoService(CodeGenerator.class)
public class ControllerCodeGenerator extends AbstractCodeGenerator {

    public static final String MONOLIB_WEB_HANDLER = "monolib.web.handler";

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
        return TypeSpec.classBuilder(GeneratorNameResolver.resolveHandlerName(getEntity(), currentOperation))
                .addModifiers(Modifier.PUBLIC)
                .superclass(
                        ParameterizedTypeName.get(
                                getOperationHandler(),
                                ClassName.get(getEntity()),
                                ClassName.get(getPackage(getEntity()), GeneratorNameResolver.resolveDtoName(getEntity()))
                        )
                ).addAnnotation(
                        AnnotationSpec.builder(ClassName.get(EntityHandler.class))
                                .addMember("path", "$S", getEntityPath())
                                .build()
                );
    }

    private String getEntityPath() {
        return getConfig("group", String.class) + "/" + GeneratorNameResolver.resolveCapitalizedEntityName(getEntity());
    }

    private ClassName getOperationHandler() {
        return switch (currentOperation) {
            case CREATE -> ClassName.get(MONOLIB_WEB_HANDLER, "CreateEntityHandler");
            case READ -> ClassName.get(MONOLIB_WEB_HANDLER, "ReadEntityHandler");
            case UPDATE -> ClassName.get(MONOLIB_WEB_HANDLER, "UpdateEntityHandler");
            case DELETE -> ClassName.get(MONOLIB_WEB_HANDLER, "DeleteEntityHandler");
        };
    }

}
