package tn.esprit.spring;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.IPisteServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc // Optional: Use if you are testing controllers
class GestionStationSkiApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(GestionStationSkiApplicationTests.class);

	@MockBean
	private IPisteRepository pisteRepository; // Use MockBean for Spring integration testing

	@Autowired
	private IPisteServices pisteServices; // Autowire the service to test its behavior

	@Test
	void contextLoads() {
		// This test just checks if the context loads
	}

	@Test
	@Order(0)
	public void ajouterPisteTest() {
		// Créer une nouvelle instance de Piste
		Piste p = new Piste();
		p.setNamePiste("Piste Verte");
		p.setLength(1200);

		// Configure the mock repository to return the piste when saved
		Mockito.when(pisteRepository.save(Mockito.any(Piste.class))).thenReturn(p);

		// Sauvegarder la piste dans le service
		Piste savedPiste = pisteServices.addPiste(p);

		// Loguer les informations de la piste
		log.info(savedPiste.toString());

		// Vérifier que l'ID de la piste n'est pas nul après la sauvegarde
		Assertions.assertNotNull(savedPiste.getNumPiste(), "L'ID de la piste ne devrait pas être nul après la sauvegarde");
	}

	@Test
	@Order(1)
	public void modifierPisteTest() {
		// Trouver une piste existante ou en créer une nouvelle
		Piste p = new Piste();
		p.setNumPiste(1L);
		p.setNamePiste("Italienne");

		// Configure the mock repository to return the piste when found
		Mockito.when(pisteRepository.findById(1L)).thenReturn(Optional.of(p));
		Mockito.when(pisteRepository.save(Mockito.any(Piste.class))).thenReturn(p);

		// Sauvegarder la modification de la piste dans le service
		Piste updatedPiste = pisteServices.retrievePiste(1L);
		updatedPiste.setNamePiste("Italienne");
		pisteServices.addPiste(updatedPiste);

		// Loguer les informations de la piste modifiée
		log.info("Piste modifiée : " + updatedPiste.toString());

		// Vérifier que la modification a bien eu lieu
		Assertions.assertEquals("Italienne", updatedPiste.getNamePiste(), "Le nom de la piste devrait être modifié");
	}

	@Test
	@Order(2)
	public void supprimerPisteTest() {
		// Créer une piste pour la suppression
		Piste p = new Piste();
		p.setNamePiste("Piste à supprimer");
		p.setLength(1000);

		// Configure the mock repository to return the piste when saved
		Mockito.when(pisteRepository.save(Mockito.any(Piste.class))).thenReturn(p);
		Mockito.when(pisteRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(p));

		// Sauvegarder et ensuite supprimer la piste via le service
		Piste savedPiste = pisteServices.addPiste(p);
		Long idPiste = savedPiste.getNumPiste();

		// Delete the piste
		pisteServices.removePiste(idPiste);

		// Vérifier que la piste a bien été supprimée
		Mockito.verify(pisteRepository).deleteById(idPiste);
	}

	@Test
	@Order(3)
	public void getAllPistesTest() {
		// Récupérer toutes les pistes
		List<Piste> pistes = List.of(new Piste(1L, "Piste 1", 1200), new Piste(2L, "Piste 2", 1500));
		Mockito.when(pisteRepository.findAll()).thenReturn(pistes);

		// Récupérer toutes les pistes via le service
		List<Piste> fetchedPistes = pisteServices.retrieveAllPistes();

		// Loguer les informations des pistes
		for (Piste p : fetchedPistes) {
			log.info(p.toString());
		}

		// Vérifier que la liste des pistes n'est pas vide
		Assertions.assertFalse(fetchedPistes.isEmpty(), "La liste des pistes ne devrait pas être vide");
	}

	@Test
	@Order(4)
	public void getPisteByIdTest() {
		// Ajouter une nouvelle piste pour le test
		Piste p = new Piste();
		p.setNamePiste("Piste spéciale");
		p.setLength(1400);

		// Configure the mock repository to return the piste when saved
		Mockito.when(pisteRepository.save(Mockito.any(Piste.class))).thenReturn(p);
		Mockito.when(pisteRepository.findById(p.getNumPiste())).thenReturn(Optional.of(p));

		// Récupérer la piste par son ID
		Piste fetchedPiste = pisteServices.retrievePiste(p.getNumPiste());

		// Loguer les informations de la piste récupérée
		log.info(fetchedPiste.toString());

		// Vérifier que la piste existe et correspond à celle créée
		Assertions.assertNotNull(fetchedPiste, "La piste aurait dû être trouvée");
		Assertions.assertEquals("Piste spéciale", fetchedPiste.getNamePiste(), "Le nom de la piste ne correspond pas");
	}
	public void sampleListOfPistes() {
		List<Piste> pistes = new ArrayList<>();
		pistes.add(new Piste(12L, "Italienne", "rouge", 12, ""));
	}
}
