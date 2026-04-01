package monolib.annotations.generator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public interface CodeGenerator {
    boolean supports(TypeElement element);
    void generate(TypeElement element, ProcessingEnvironment env);
}
