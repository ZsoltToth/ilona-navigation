package uni.miskolc.ips.ilona.navigation.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.FileDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.parameters.OntologyCopy;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.navigation.persist.OntologyDAO;
import uni.miskolc.ips.ilona.navigation.persist.ontology.IlonaIRIs;
import uni.miskolc.ips.ilona.navigation.service.OntologyGenerationService;

public class OntologyGenerationServiceImpl implements OntologyGenerationService {

	private OntologyDAO ontologyDAO;
	private ZoneDAO zoneDAO;

	public OntologyGenerationServiceImpl(OntologyDAO ontologyDAO, ZoneDAO zoneDAO) {
		super();
		this.ontologyDAO = ontologyDAO;
		this.zoneDAO = zoneDAO;
	}

	/**
	 * A method which returns the base ontology structure
	 * @return the base ontology as a file
	 */
	@Override
	public File baseOntology() {
		OWLOntology ontology = ontologyDAO.getBaseOntology();
		try {
			File tempFile = File.createTempFile("ontology", ".owl");
			FileWriter writer = new FileWriter(tempFile);
			writer.write(ontology.toString());
			writer.close();
			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * A method which generates a raw ontology, filled with only the zones from the Zone database of the system.
	 * @return the filled ontology as a file
	 */
	@Override
	public File generateRawOntology() {
		File result = baseOntology();
		Map<String, Zone> zoneIndividuals = new HashMap();
			//Query all of the individuals from the Zone Database into the ontology File
			result=ontologyDAO.addZones(result,zoneDAO.readZones());
		return result;
	}

}
