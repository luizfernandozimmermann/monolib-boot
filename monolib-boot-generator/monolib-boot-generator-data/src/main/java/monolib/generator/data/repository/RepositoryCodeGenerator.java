package monolib.generator.data.repository;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import lombok.NoArgsConstructor;
import monolib.annotations.GenerateRepository;
import monolib.annotations.generator.CodeGenerator;
import monolib.data.api.repository.EntityBaseRepository;
import monolib.generator.core.AbstractCodeGenerator;
import monolib.generator.core.GeneratorNameResolver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

@NoArgsConstructor
@AutoService(CodeGenerator.class)
public class RepositoryCodeGenerator extends AbstractCodeGenerator {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return GenerateRepository.class;
    }

    @Override
    protected TypeSpec.Builder generateBuilder() {
        return TypeSpec.interfaceBuilder(GeneratorNameResolver.resolveRepositoryName(getEntity()))
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(
                        ClassName.get(EntityBaseRepository.class),
                        ClassName.get(getEntity())
                ))
                .addAnnotation(getRepositoryAnnotation());
    }

    private Class<? extends Annotation> getRepositoryAnnotation() {
        if (Boolean.TRUE.equals(getConfig("noRepositoryBean", Boolean.class))) {
            return NoRepositoryBean.class;
        }
        return Repository.class;
    }

}
