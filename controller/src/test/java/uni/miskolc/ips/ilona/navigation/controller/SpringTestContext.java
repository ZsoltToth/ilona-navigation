package uni.miskolc.ips.ilona.navigation.controller;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;
import uni.miskolc.ips.ilona.navigation.persist.OntologyDAO;
import uni.miskolc.ips.ilona.navigation.persist.ontology.OntologyDAOImpl;
import uni.miskolc.ips.ilona.navigation.service.OntologyGenerationService;
import uni.miskolc.ips.ilona.navigation.service.impl.OntologyGenerationServiceImpl;

import java.io.FileNotFoundException;

@Configuration
public class SpringTestContext {

    @Bean(autowire = Autowire.BY_NAME)
    public OntologyGenerationService ontologyGenerationService() throws FileNotFoundException, OWLOntologyCreationException {
        OntologyGenerationService ontologyGenerationService = new OntologyGenerationServiceImpl(ontologyDAO(), zoneDAO());
        System.out.println("ontologyGenerationService");
        return ontologyGenerationService;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public OntologyDAO ontologyDAO() throws OWLOntologyCreationException {
        System.out.println("....-.-.-.-.-.-.-.-.-");
        OntologyDAO ontologyDAO = new OntologyDAOImpl("src/resources/ILONABASE.owl", "src/resources/dummy.owl");
        System.out.println("ontologyDAO");
        return ontologyDAO;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public ZoneDAO zoneDAO() throws FileNotFoundException {
        ZoneDAO zoneDAO = new MySQLZoneDAO("src/resources/mybatis-configuration.xml", System.getProperty("database.host"), Integer.parseInt(System.getProperty("database.port")), System.getProperty("database.database"), System.getProperty("database.user"), System.getProperty("database.password"));
        System.out.println("zoneDAO");
        return zoneDAO;
    }
/*
    @Bean(autowire = Autowire.BY_NAME)
    public WayfindingService wayfindingService() throws FileNotFoundException, OWLOntologyCreationException {
        WayfindingService wayfindingService = new WayfindingServiceImpl(ontologyDAO(), zoneDAO());
        System.out.println("WayfindingService");
        return wayfindingService;
    }
    */
}
