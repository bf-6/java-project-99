package hexlet.code.service;

import hexlet.code.dto.task.status.TaskStatusCreateDTO;
import hexlet.code.dto.task.status.TaskStatusDTO;
import hexlet.code.dto.task.status.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceAlreadyExistsException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {

    @Autowired
    private TaskStatusRepository statusRepository;

    @Autowired
    private TaskStatusMapper statusMapper;

    public List<TaskStatusDTO> index() {
        return statusRepository.findAll().stream().map(statusMapper::map).toList();
    }

    public TaskStatusDTO show(long id) {
        return statusMapper.map(statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status with id " + id + " not found!")));
    }

    public TaskStatusDTO create(TaskStatusCreateDTO statusData) {
        statusRepository.findAll().stream()
                .filter(existingStatus -> existingStatus.equals(statusData))
                .findAny()
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException("Status " + statusData.getName() + " already exists");
                });
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
