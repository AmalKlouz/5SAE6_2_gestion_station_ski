package tn.esprit.tpfoyer.control;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;
import java.util.List;

@RestController
@RequestMapping("/foyer")
public class FoyerRestController {
    
    private final IFoyerService foyerService;
    private final Counter getAllFoyersCounter;
    private final Counter getFoyerByIdCounter;
    private final Counter addFoyerCounter;
    private final Counter deleteFoyerCounter;
    private final Counter updateFoyerCounter;
    private final Timer getFoyersTimer;

    public FoyerRestController(IFoyerService foyerService, MeterRegistry registry) {
        this.foyerService = foyerService;
        
        // Initialize counters
        this.getAllFoyersCounter = Counter.builder("foyer_getall_requests_total")
            .description("Number of requests to get all foyers")
            .register(registry);
            
        this.getFoyerByIdCounter = Counter.builder("foyer_getbyid_requests_total")
            .description("Number of requests to get foyer by ID")
            .register(registry);
            
        this.addFoyerCounter = Counter.builder("foyer_add_requests_total")
            .description("Number of requests to add foyer")
            .register(registry);
            
        this.deleteFoyerCounter = Counter.builder("foyer_delete_requests_total")
            .description("Number of requests to delete foyer")
            .register(registry);
            
        this.updateFoyerCounter = Counter.builder("foyer_update_requests_total")
            .description("Number of requests to update foyer")
            .register(registry);
            
        // Initialize timer
        this.getFoyersTimer = Timer.builder("foyer_getall_duration_seconds")
            .description("Time taken to retrieve all foyers")
            .register(registry);
    }

    @GetMapping("/retrieve-all-foyers")
    public List<Foyer> getFoyers() {
        getAllFoyersCounter.increment();
        return getFoyersTimer.record(() -> foyerService.retrieveAllFoyers());
    }

    @GetMapping("/retrieve-foyer/{foyer-id}")
    public Foyer retrieveFoyer(@PathVariable("foyer-id") Long fId) {
        getFoyerByIdCounter.increment();
        return foyerService.retrieveFoyer(fId);
    }

    @PostMapping("/add-foyer")
    public Foyer addFoyer(@RequestBody Foyer f) {
        addFoyerCounter.increment();
        return foyerService.addFoyer(f);
    }

    @DeleteMapping("/remove-foyer/{foyer-id}")
    public void removeFoyer(@PathVariable("foyer-id") Long fId) {
        deleteFoyerCounter.increment();
        foyerService.removeFoyer(fId);
    }

    @PutMapping("/modify-foyer")
    public Foyer modifyFoyer(@RequestBody Foyer f) {
        updateFoyerCounter.increment();
        return foyerService.modifyFoyer(f);
    }
}
