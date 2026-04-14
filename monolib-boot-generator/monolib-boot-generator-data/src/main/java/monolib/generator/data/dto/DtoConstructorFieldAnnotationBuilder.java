package monolib.generator.data.dto;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import monolib.generator.core.AbstractGeneratorContext;
import monolib.generator.core.AnnotationWrapper;
import monolib.generator.core.GeneratorContext;
import monolib.generator.data.ValidationParams;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public class DtoConstructorFieldAnnotationBuilder extends AbstractGeneratorContext {

    public DtoConstructorFieldAnnotationBuilder(GeneratorContext context) {
        super(context);
    }

    public List<AnnotationSpec> build(AnnotationMirror mirror) {
        var wrapper = AnnotationWrapper.of(mirror);
        var annotation = wrapper.as(ValidationParams.class);
        var specs = new ArrayList<AnnotationSpec>();
        sizeValidation(specs, annotation);
        notNullValidation(specs, annotation);
        patternValidation(specs, annotation);
        return specs;
    }

    private void sizeValidation(List<AnnotationSpec> specs, ValidationParams annotation) {
        if (annotation.getMinLength() == null && annotation.getMaxLength() == null) {
            return;
        }
        var builder = AnnotationSpec.builder(ClassName.get(Size.class));
        addMember(builder, "min", annotation.getMinLength());
        addMember(builder, "max", annotation.getMaxLength());
        specs.add(builder.build());
    }

    private void addMember(AnnotationSpec.Builder builder, String field, Object value) {
        if (value == null) {
            return;
        }
        builder.addMember(field, buildAnnotationValue(value));
    }

    private void notNullValidation(List<AnnotationSpec> specs, ValidationParams annotation) {
        if (!Boolean.TRUE.equals(annotation.getNotNull())) {
            return;
        }
        specs.add(AnnotationSpec.builder(ClassName.get(NotNull.class)).build());
    }

    private void patternValidation(List<AnnotationSpec> specs, ValidationParams annotation) {
        if (annotation.getRegexp() == null) {
            return;
        }
        var builder = AnnotationSpec.builder(ClassName.get(Pattern.class));
        addMember(builder, "regexp", annotation.getRegexp());
        specs.add(builder.build());
    }

    private CodeBlock buildAnnotationValue(AnnotationValue annotationValue) {
        var value = annotationValue.getValue();

        return buildAnnotationValue(value);
    }

    private CodeBlock buildAnnotationValue(Object value) {
        if (value instanceof String s) return literal(s);
        if (value instanceof Boolean b) return literal(b);
        if (value instanceof Number n) return literal(n);

        if (value instanceof TypeMirror t) return classLiteral(t);
        if (value instanceof VariableElement e) return enumLiteral(e);
        if (value instanceof AnnotationMirror a) return annotationLiteral(a);
        if (value instanceof List<?> l) return arrayLiteral(l);

        return literal(value);
    }

    private CodeBlock literal(Object value) {
        return CodeBlock.of("$L", value);
    }

    private CodeBlock literal(String value) {
        return CodeBlock.of("$S", value);
    }

    private CodeBlock classLiteral(TypeMirror type) {
        return CodeBlock.of("$T.class", TypeName.get(type));
    }

    private CodeBlock enumLiteral(VariableElement enumConstant) {
        var enumType = (TypeElement) enumConstant.getEnclosingElement();

        return CodeBlock.of(
                "$T.$L",
                ClassName.get(enumType),
                enumConstant.getSimpleName()
        );
    }

    private CodeBlock annotationLiteral(AnnotationMirror annotation) {
        return CodeBlock.of("$L", build(annotation));
    }

    private CodeBlock arrayLiteral(List<?> list) {
        var builder = CodeBlock.builder().add("{");
        var first = true;

        for (var item : list) {
            if (!first) {
                builder.add(", ");
            }
            first = false;
            builder.add("$L", buildAnnotationValue((AnnotationValue) item));
        }

        return builder.add("}").build();
    }
}
