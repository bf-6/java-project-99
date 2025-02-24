package hexlet.code.dto.label;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelCreateDTO {

    @Size(min = 3, max = 1000)
    @Column(unique = true)
    private String name;

}
