package monolib.data.base.controller.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EntityPageable {
    private Set<String> fields;
    private String filter;
    private String orderBy;
    private int page = 0;
    private int size = 10;
}
