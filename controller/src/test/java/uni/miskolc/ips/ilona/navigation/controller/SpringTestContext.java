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
        return ontologyGenerationService;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public OntologyDAO ontologyDAO() throws OWLOntologyCreationException {
        OntologyDAO ontologyDAO = new OntologyDAOImpl("src/resources/ILONABASE.owl", "src/resources/dummy.owl");
        return ontologyDAO;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public ZoneDAO zoneDAO() throws FileNotFoundException {
        ZoneDAO zoneDAO = new MySQLZoneDAO("src/resources/mybatis-configuration.xml", System.getProperty("database.host"), Integer.parseInt(System.getProperty("database.port")), System.getProperty("database.database"), System.getProperty("database.user"), System.getProperty("database.password"));
        return zoneDAO;
    }
}
