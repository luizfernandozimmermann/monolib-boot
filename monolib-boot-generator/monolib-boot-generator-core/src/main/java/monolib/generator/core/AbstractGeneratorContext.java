package monolib.generator.core;

import lombok.NoArgsConstructor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

@NoArgsConstructor
public abstract class AbstractGeneratorContext {

    protected GeneratorContext context;

    protected AbstractGeneratorContext(GeneratorContext context) {
        this.context = context;
    }

    protected void registerContext(GeneratorContext context) {
        this.context = context;
    }

    protected ProcessingEnvironment getEnv() {
        return context.getEnv();
    }

    protected TypeElement getEntity() {
        return context.getEntity();
    }

    protected String getPackage(TypeElement element) {
        return getEnv().getElementUtils()
                .getPackageOf(element)
                .getQualifiedName()
                .toString();
    }

}
