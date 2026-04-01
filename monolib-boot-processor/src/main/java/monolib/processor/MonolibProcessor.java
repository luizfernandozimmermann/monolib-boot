package monolib.processor;

import com.google.auto.service.AutoService;
import monolib.annotations.generator.CodeGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

@AutoService(javax.annotation.processing.Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public class MonolibProcessor extends AbstractProcessor {

    private List<CodeGenerator> generators;

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);

        generators = ServiceLoader
                .load(CodeGenerator.class, getClass().getClassLoader())
                .stream()
                .map(ServiceLoader.Provider::get)
                .toList();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (var element : roundEnv.getRootElements()) {
            if (element instanceof TypeElement entity) {
                process(entity);
            }
        }
        return false;
    }

    private void process(TypeElement entity) {
        generators.stream()
                .filter(generator -> generator.supports(entity))
                .forEach(generator -> generator.generate(entity, processingEnv));
    }

}
