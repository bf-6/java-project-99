package hexlet.code.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.task.status.TaskStatusDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.util.ModelGenerator;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskStatusControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private TaskStatusRepository statusRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskStatusMapper statusMapper;

    private TaskStatus testStatus;

    @BeforeEach
    void setUp() {
        statusRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        testStatus = Instancio.of(modelGenerator.getStatusModel()).create();
        statusRepository.save(testStatus);
    }

    @Test
    public void testIndex() throws Exception {
        var response = mockMvc.perform(get("/api/task_statuses").with(jwt()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        var body = response.getContentAsString();

        List<TaskStatusDTO> statusDTOList = objectMapper.readValue(body, new TypeReference<>() { });

        var actual = statusDTOList.stream().map(statusMapper::map).toList();
        var expected = statusRepository.findAll();

        assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testCreate() throws Exception {
        var data = Instancio.of(modelGenerator.getStatusModel())
                .create();

        var request = post("/api/task_statuses").with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var status = statusRepository.findBySlug(data.getSlug()).orElse(null);

        assertNotNull(status);
        assertThat(status.getName()).isEqualTo(data.getName());
        assertThat(status.getSlug()).isEqualTo(data.getSlug());
    }

    @Test
    void testPartialUpdate() throws Exception {
        var name = faker.color().name();
        var data = new HashMap<>();
        data.put("name", name);

        var request = put("/api/task_statuses/" + testStatus.getId()).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var status = statusRepository.findById(testStatus.getId()).orElseThrow();
        Assertions.assertThat(status.getName()).isEqualTo((name));
    }

    @Test
    void testUpdate() throws Exception {
        var name = faker.color().name();
        var slug = faker.internet().slug();

        var data = new HashMap<>();
        data.put("name", name);
        data.put("slug", slug);


        var request = put("/api/task_statuses/" + testStatus.getId()).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var status = statusRepository.findById(testStatus.getId()).orElseThrow();
        Assertions.assertThat(status.getName()).isEqualTo((name));
        Assertions.assertThat(status.getSlug()).isEqualTo((slug));
    }

    @Test
    void testShow() throws Exception {
        var request = get("/api/task_statuses/" + testStatus.getId()).with(jwt());
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testStatus.getName()),
                v -> v.node("slug").isEqualTo(testStatus.getSlug()));
    }

    @Test
    void testDelete() throws Exception {
        var data = Instancio.of(modelGenerator.getStatusModel())
                .create();

        statusRepository.save(data);

        var request = delete("/api/task_statuses/" + data.getId()).with(jwt());

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        data = statusRepository.findById(data.getId()).orElse(null);
        Assertions.assertThat(data).isNull();
    }
}
