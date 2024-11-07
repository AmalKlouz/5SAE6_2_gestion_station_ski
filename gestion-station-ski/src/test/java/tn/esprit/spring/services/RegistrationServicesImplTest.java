package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationServicesImplTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRegistrationAndAssignToSkier() {
        // Arrange
        Long skierId = 1L;
        Skier skier = new Skier();
        skier.setNumSkier(skierId);

        Registration registration = new Registration();
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, skierId);

        // Assert
        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        verify(skierRepository).findById(skierId);
        verify(registrationRepository).save(registration);
    }

    @Test
    void assignRegistrationToCourse() {
        // Arrange
        Long registrationId = 1L;
        Long courseId = 2L;
        Registration registration = new Registration();
        Course course = new Course();
        course.setNumCourse(courseId);

        when(registrationRepository.findById(registrationId)).thenReturn(Optional.of(registration));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.assignRegistrationToCourse(registrationId, courseId);

        // Assert
        assertNotNull(result);
        assertEquals(course, result.getCourse());
        verify(registrationRepository).findById(registrationId);
        verify(courseRepository).findById(courseId);
        verify(registrationRepository).save(registration);
    }

    @Test
    void addRegistrationAndAssignToSkierAndCourse() {
        // Arrange
        Long skierId = 1L;
        Long courseId = 2L;
        Skier skier = new Skier();
        skier.setNumSkier(skierId);
        skier.setDateOfBirth(LocalDate.now().minusYears(20));

        Course course = new Course();
        course.setNumCourse(courseId);
        course.setTypeCourse(TypeCourse.INDIVIDUAL);

        Registration registration = new Registration();
        registration.setNumWeek(1);

        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skierId, courseId);

        // Assert
        assertNotNull(result);
        assertEquals(skier, result.getSkier());
        assertEquals(course, result.getCourse());
        verify(skierRepository).findById(skierId);
        verify(courseRepository).findById(courseId);
        verify(registrationRepository).save(registration);
    }

    @Test
    void numWeeksCourseOfInstructorBySupport() {
        // Arrange
        Long instructorId = 1L;
        Support support = Support.SKI;
        List<Integer> weeks = Collections.singletonList(1);

        when(registrationRepository.numWeeksCourseOfInstructorBySupport(instructorId, support)).thenReturn(weeks);

        // Act
        List<Integer> result = registrationServices.numWeeksCourseOfInstructorBySupport(instructorId, support);

        // Assert
        assertEquals(weeks, result);
        verify(registrationRepository).numWeeksCourseOfInstructorBySupport(instructorId, support);
    }
}
