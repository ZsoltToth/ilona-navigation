package uni.miskolc.ips.ilona.navigation.web;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uni.miskolc.ips.ilona.measurement.persist.ZoneDAO;
import uni.miskolc.ips.ilona.measurement.persist.mysql.MySQLZoneDAO;
import uni.miskolc.ips.ilona.navigation.persist.OntologyDAO;
import uni.miskolc.ips.ilona.navigation.persist.ontology.OntologyDAOImpl;
import uni.miskolc.ips.ilona.navigation.service.OntologyGenerationService;
import uni.miskolc.ips.ilona.navigation.service.WayfindingService;
import uni.miskolc.ips.ilona.navigation.service.impl.OntologyGenerationServiceImpl;
import uni.miskolc.ips.ilona.navigation.service.impl.WayfindingServiceImpl;

import java.io.FileNotFoundException;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "uni.miskolc.ips.ilona.navigation.controller")
public class IlonaNavigationApplicationContext extends WebMvcConfigurerAdapter {

    @Bean(autowire = Autowire.BY_NAME)
    public OntologyGenerationService ontologyGenerationService() throws FileNotFoundException, OWLOntologyCreationException {
        OntologyGenerationService ontologyGenerationService = new OntologyGenerationServiceImpl(ontologyDAO(), zoneDAO());
        System.out.println("ontologyGenerationService");
        return ontologyGenerationService;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public OntologyDAO ontologyDAO() throws OWLOntologyCreationException {
        System.out.println("....-.-.-.-.-.-.-.-.-");
        OntologyDAO ontologyDAO = new OntologyDAOImpl(System.getProperty("baseOntologyPath"), System.getProperty("navigationOntologyPath"));
        System.out.println("ontologyDAO");
        return ontologyDAO;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public ZoneDAO zoneDAO() throws FileNotFoundException {
        ZoneDAO zoneDAO = new MySQLZoneDAO(System.getProperty("configFilePath"), System.getProperty("database.host"), Integer.parseInt(System.getProperty("database.port")), System.getProperty("database.database"), System.getProperty("database.user"), System.getProperty("database.password"));
        System.out.println("zoneDAO");
        return zoneDAO;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public WayfindingService wayfindingService() throws FileNotFoundException, OWLOntologyCreationException {
        WayfindingService wayfindingService = new WayfindingServiceImpl(ontologyDAO(), zoneDAO());
        System.out.println("WayfindingService");
        return wayfindingService;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");

    }

}
