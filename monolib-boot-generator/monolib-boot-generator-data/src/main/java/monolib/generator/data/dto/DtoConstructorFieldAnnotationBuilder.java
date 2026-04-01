package monolib.generator.data.dto;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import monolib.generator.core.AbstractGeneratorContext;
import monolib.generator.core.GeneratorContext;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public class DtoConstructorFieldAnnotationBuilder extends AbstractGeneratorContext {

    public DtoConstructorFieldAnnotationBuilder(GeneratorContext context) {
        super(context);
    }

    public AnnotationSpec build(AnnotationMirror mirror) {
        var builder = AnnotationSpec.builder(ClassName.get((TypeElement) mirror.getAnnotationType().asElement()));
        for (var entry : getEnv().getElementUtils().getElementValuesWithDefaults(mirror).entrySet()) {
            var name = entry.getKey().getSimpleName().toString();
            var value = buildAnnotationValue(entry.getValue());
            builder.addMember(name, "$L", value.toString());
        }
        return builder.build();
    }

    private CodeBlock buildAnnotationValue(AnnotationValue annotationValue) {
        var value = annotationValue.getValue();

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
        boolean first = true;

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
