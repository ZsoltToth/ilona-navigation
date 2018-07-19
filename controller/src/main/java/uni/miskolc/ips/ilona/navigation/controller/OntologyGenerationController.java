package uni.miskolc.ips.ilona.navigation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uni.miskolc.ips.ilona.navigation.service.OntologyGenerationService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
public class OntologyGenerationController {

    private OntologyGenerationService ontologyGenerationService;

    @Autowired
    public OntologyGenerationController(OntologyGenerationService ontologyGenerationService) {
        this.ontologyGenerationService = ontologyGenerationService;
    }


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/navigation/ontology/template/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource downloadTemplateOntology(HttpServletResponse response) {
        File result = ontologyGenerationService.generateRawOntology();
        String fileName;
        try {
            fileName = URLEncoder.encode("generatedOntology.owl", "UTF-8");
            fileName = URLDecoder.decode(fileName, "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
            fileName = "generatedOntology.owl";
            e.printStackTrace();
        }

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        return new FileSystemResource(result);
    }
}
