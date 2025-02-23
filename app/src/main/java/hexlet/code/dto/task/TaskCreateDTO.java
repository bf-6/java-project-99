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
    private String title;

    private long index;
    private String content;

    @NotBlank
    private String status;
    private long assigneeId;
}
