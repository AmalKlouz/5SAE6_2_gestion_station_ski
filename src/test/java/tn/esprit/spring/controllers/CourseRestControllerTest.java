package tn.esprit.spring.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.services.ICourseServices;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseRestController.class)
class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICourseServices courseServices;

    @Test
    void addCourse() throws Exception {
        Course course = new Course();
        when(courseServices.addCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/course/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Sample Course\"}"))
                .andExpect(status().isOk());

        verify(courseServices, times(1)).addCourse(any(Course.class));
    }

    @Test
    void getAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseServices.retrieveAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/course/all"))
                .andExpect(status().isOk());

        verify(courseServices, times(1)).retrieveAllCourses();
    }

    @Test
    void updateCourse() throws Exception {
        Course course = new Course();
        when(courseServices.updateCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(put("/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Course\"}"))
                .andExpect(status().isOk());

        verify(courseServices, times(1)).updateCourse(any(Course.class));
    }

    @Test
    void getById() throws Exception {
        Long courseId = 1L;
        Course course = new Course();
        when(courseServices.retrieveCourse(courseId)).thenReturn(course);

        mockMvc.perform(get("/course/get/{id-course}", courseId))
                .andExpect(status().isOk());

        verify(courseServices, times(1)).retrieveCourse(courseId);
    }
}
