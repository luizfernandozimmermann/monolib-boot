package monolib.generator.data.service;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import lombok.NoArgsConstructor;
import monolib.annotations.GenerateCRUDService;
import monolib.annotations.generator.CodeGenerator;
import monolib.data.api.service.EntityCRUDServiceBase;
import monolib.generator.core.AbstractCodeGenerator;
import monolib.generator.core.GeneratorNameResolver;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

@NoArgsConstructor
@AutoService(CodeGenerator.class)
public class CRUDServiceCodeGenerator extends AbstractCodeGenerator {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return GenerateCRUDService.class;
    }

    @Override
    protected TypeSpec.Builder generateBuilder() {
        return TypeSpec.classBuilder(GeneratorNameResolver.resolveCRUDServiceName(getEntity()))
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(
                        ClassName.get(EntityCRUDServiceBase.class),
                        ClassName.get(getEntity())
                ))
                .addAnnotation(Service.class);
    }

}
