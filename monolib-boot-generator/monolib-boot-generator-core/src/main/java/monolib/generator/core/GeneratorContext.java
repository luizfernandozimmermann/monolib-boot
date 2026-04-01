package monolib.generator.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

@Getter
@AllArgsConstructor(staticName = "of")
public class GeneratorContext {

    private final ProcessingEnvironment env;
    private final TypeElement entity;

}
