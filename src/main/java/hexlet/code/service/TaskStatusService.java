package hexlet.code.service;

import hexlet.code.dto.task.status.TaskStatusCreateDTO;
import hexlet.code.dto.task.status.TaskStatusDTO;
import hexlet.code.dto.task.status.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TaskStatusService {

    private final TaskStatusRepository statusRepository;
    private final TaskStatusMapper statusMapper;

    public List<TaskStatusDTO> index() {
        return statusRepository.findAll().stream().map(statusMapper::map).toList();
    }

    public TaskStatusDTO show(long id) {
        return statusMapper.map(statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status with id " + id + " not found!")));
    }

    public TaskStatusDTO create(TaskStatusCreateDTO statusData) {
        var status = statusMapper.map(statusData);
        statusRepository.save(status);
        return statusMapper.map(status);
    }

    public TaskStatusDTO update(long id, TaskStatusUpdateDTO statusData) {
        var status = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status with id " + id + " not found!"));
        statusMapper.update(statusData, status);
        statusRepository.save(status);
        return statusMapper.map(status);
    }

    public void destroy(long id) {
        statusRepository.deleteById(id);
    }

}
