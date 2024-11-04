package tn.esprit.spring.services;

import org.mockito.InjectMocks;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;

import java.util.List;
@Service
public class PisteServicesImpl implements IPisteServices {

    private final IPisteRepository pisteRepository;

    public PisteServicesImpl(IPisteRepository pisteRepository) {
        this.pisteRepository = pisteRepository;
    }

    @Override
    public List<Piste> retrieveAllPistes() {
        return pisteRepository.findAll();
    }

    @Override
    public Piste addPiste(Piste piste) {
        return pisteRepository.save(piste);
    }

    @Override
    public void removePiste(Long numPiste) {
        pisteRepository.deleteById(numPiste);
    }

    @Override
    public Piste retrievePiste(Long numPiste) {
        return pisteRepository.findById(numPiste).orElse(null);
    }
}
