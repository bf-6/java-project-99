package hexlet.code.service;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> index() {
        return userRepository.findAll().stream().map(userMapper::map).toList();
    }

    public UserDTO show(long id) {
        return userMapper.map(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!")));
    }

    public UserDTO create(UserCreateDTO userDTO) {
        var user = userMapper.map(userDTO);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public UserDTO update(long id, UserUpdateDTO userDTO) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found!"));
        userMapper.update(userDTO, user);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public void destroy(long id) {
        userRepository.deleteById(id);
    }

}
