package hexlet.code.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {

    private String title;

    private Long index;
    private String content;

    private String status;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;

    private Set<Long> taskLabelIds = new HashSet<>();
}
