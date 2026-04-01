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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public abstract class AbstractCodeGenerator extends AbstractGeneratorContext implements CodeGenerator {

    protected AnnotationWrapper annotation;
    protected TypeSpec.Builder builder;

    protected abstract TypeSpec.Builder generateBuilder();

    @Override
    public boolean supports(TypeElement element) {
        annotation = getAnnotationWrapper(element);
        return annotation != null;
    }

    private AnnotationWrapper getAnnotationWrapper(TypeElement element) {
        var mirror = getAnnotationRecursive(element, getAnnotationClass().getCanonicalName());
        if (mirror == null) {
            return null;
        }
        return AnnotationWrapper.of(mirror);
    }

    protected abstract Class<? extends Annotation> getAnnotationClass();

    private AnnotationMirror getAnnotationRecursive(Element element, String targetAnnotation) {
        return getAnnotationRecursive(element, targetAnnotation, new HashSet<>(), null);
    }

    private AnnotationMirror getAnnotationRecursive(Element element, String targetAnnotation, Set<String> visited, AnnotationMirror root) {
        for (var mirror : element.getAnnotationMirrors()) {
            var annotationElement = mirror.getAnnotationType().asElement();
            var annotationName = ((TypeElement) annotationElement)
                    .getQualifiedName()
                    .toString();

            if (!visited.add(annotationName)) {
                continue;
            }

            var currentRoot = getAnnotationMirror(targetAnnotation, visited, annotationName, getCurrentRoot(root, mirror), annotationElement);
            if (currentRoot != null) {
                return currentRoot;
            }
        }

        return null;
    }

    private static AnnotationMirror getCurrentRoot(AnnotationMirror root, AnnotationMirror mirror) {
        return (root == null) ? mirror : root;
    }

    private AnnotationMirror getAnnotationMirror(String targetAnnotation, Set<String> visited, String annotationName, AnnotationMirror currentRoot, Element annotationElement) {
        if (annotationName.equals(targetAnnotation)) {
            return currentRoot;
        }
        return getAnnotationRecursive(annotationElement, targetAnnotation, visited, currentRoot);
    }

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
