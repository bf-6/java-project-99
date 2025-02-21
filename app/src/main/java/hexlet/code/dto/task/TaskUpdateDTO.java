package hexlet.code.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TaskUpdateDTO {

    @Size(min = 1)
    @NotBlank
    private JsonNullable<String> name;

    private JsonNullable<Long> index;
    private JsonNullable<String> description;

    @NotBlank
    private JsonNullable<String> taskStatus;

    private JsonNullable<Long> assigneeId;

}
