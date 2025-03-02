package hexlet.code.service;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceAlreadyExistsException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskSpecification specBuilder;
    private final LabelRepository labelRepository;

    public List<TaskDTO> index(TaskParamsDTO params) {
        var spec = specBuilder.build(params);
        var tasks = taskRepository.findAll(spec);
        var result = tasks.stream().map(taskMapper::map);
        return result.toList();
    }

    public TaskDTO show(long id) {
        return taskMapper.map(taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found!")));
    }

    public TaskDTO create(TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);

        // Получение существующих меток по ID
        if (taskData.getTaskLabelIds() != null) {
            Set<Label> existingLabels = labelRepository.findByIdIn(taskData.getTaskLabelIds());
            task.setLabels(existingLabels);
        }

        taskRepository.findAll().stream()
                .filter(existingTask -> existingTask.equals(task))
                .findAny()
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException("Task " + task.getName() + " already exists");
                });
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public TaskDTO update(long id, TaskUpdateDTO taskData) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found!"));
        taskMapper.update(taskData, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public void destroy(long id) {
        taskRepository.deleteById(id);
    }
}
