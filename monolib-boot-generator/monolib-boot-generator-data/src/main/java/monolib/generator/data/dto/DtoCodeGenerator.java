package monolib.generator.data.dto;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import monolib.annotations.GenerateDto;
import monolib.annotations.generator.CodeGenerator;
import monolib.data.api.model.EntityDtoBase;
import monolib.data.api.model.LogicalEntityDtoBase;
import monolib.generator.core.AbstractCodeGenerator;
import monolib.generator.core.GeneratorNameResolver;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

@NoArgsConstructor
@AutoService(CodeGenerator.class)
public class DtoCodeGenerator extends AbstractCodeGenerator {

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return GenerateDto.class;
    }

    @Override
    protected TypeSpec.Builder generateBuilder() {
        return TypeSpec.classBuilder(GeneratorNameResolver.resolveDtoName(getEntity()))
                .addModifiers(Modifier.PUBLIC)
                .superclass(getBaseDto())
                .addAnnotation(ClassName.get(Getter.class))
                .addAnnotation(ClassName.get(Setter.class))
                .addAnnotation(ClassName.get(NoArgsConstructor.class))
                .addAnnotation(
                        AnnotationSpec.builder(ClassName.get(EqualsAndHashCode.class))
                                .addMember("callSuper", "true")
                                .build()
                );
    }

    @Override
    public void builderAdditionals() {
        builder.addMethod(new DtoConstructorGenerator(context, builder).generate());
    }

    private ClassName getBaseDto() {
        var superclass = getEntity().getSuperclass();
        if (superclass instanceof LogicalEntityDtoBase) {
            return ClassName.get(LogicalEntityDtoBase.class);
        }
        return ClassName.get(EntityDtoBase.class);
    }

}
