package tn.esprit.spring.ServiceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.SkierServicesImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SkierServiceImplTest {
    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private IRegistrationRepository registrationRepository;
    @InjectMocks
    private SkierServicesImpl skierServices;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void retrieveAllSkiers(){
        List<Skier> skierList = new ArrayList<>();
        when (skierRepository.findAll()).thenReturn(skierList);
        List<Skier> result = skierServices.retrieveAllSkiers();
        assertNotNull(result);
        assertEquals(skierList,result);
        verify(skierRepository,times(1)).findAll();
    }
}
