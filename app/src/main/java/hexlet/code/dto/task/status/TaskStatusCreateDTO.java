package hexlet.code.dto.task.status;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusCreateDTO {

    @Size(min = 1)
    private String name;

    @Size(min = 1)
    private String slug;

}
