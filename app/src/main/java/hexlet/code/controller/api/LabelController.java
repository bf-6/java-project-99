package hexlet.code.controller.api;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping
    public ResponseEntity<List<LabelDTO>> index() {
        var result = labelService.index();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping("/{id}")
    public LabelDTO show(@PathVariable long id) {
        return labelService.show(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(@RequestBody @Valid LabelCreateDTO labelDTO) {
        return labelService.create(labelDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO update(@RequestBody @Valid LabelUpdateDTO labelDTO, @PathVariable long id) {
        return labelService.update(id, labelDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        labelService.destroy(id);
    }
}
