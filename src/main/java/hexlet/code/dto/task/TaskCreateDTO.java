package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {

    @Size(min = 1)
    @NotBlank
    private String title;

    private Long index;
    private String content;

    @NotBlank
    private String status;

    @JsonProperty("assignee_id")
    private Long assigneeId;

    private Set<Long> labelIds;
}
