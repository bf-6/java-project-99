package hexlet.code.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDTO {

    @Size(min = 1)
    @NotBlank
    private String name;

    private long index;
    private String description;

    @NotBlank
    private String taskStatus;

    private long assigneeId;
}
