package monolib.generator.core;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import monolib.annotations.generator.CodeGenerator;

import javax.annotation.processing.Generated;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

@NoArgsConstructor
public abstract class AbstractCodeGenerator extends AbstractGeneratorContext implements CodeGenerator {

    protected AnnotationWrapper annotation;
    protected TypeSpec.Builder builder;

    protected abstract TypeSpec.Builder generateBuilder();

    @Override
    public boolean supports(TypeElement element) {
        annotation = AnnotationWrapper.of(element, getAnnotationClass());
        return annotation.isPresent();
    }

    protected abstract Class<? extends Annotation> getAnnotationClass();

    protected  <T> T getConfig(String field, Class<T> returnType) {
        return annotation.get(field, returnType);
    }

    @Override
    public void generate(TypeElement element, ProcessingEnvironment env) {
        registerContext(GeneratorContext.of(env, element));
        builder = generateBuilder()
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get(Generated.class))
                                .addMember("value", "$S", getAnnotationClass().getSimpleName())
                                .build()
                );
        builderAdditionals();
        write();
    }

    public void builderAdditionals() {}

    @SneakyThrows
    protected void write() {
        JavaFile.builder(getPackage(getEntity()), builder.build())
                .build()
                .writeTo(getEnv().getFiler());
    }

}
