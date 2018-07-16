package uni.miskolc.ips.ilona.navigation.service.impl.gateway;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uni.miskolc.ips.ilona.measurement.controller.dto.MeasurementDTO;
import uni.miskolc.ips.ilona.measurement.controller.dto.PositionDTO;
import uni.miskolc.ips.ilona.navigation.service.gateway.PositionQueryService;
import uni.miskolc.ips.ilona.navigation.service.gateway.PositionQueryServiceSIConfig;

import java.util.UUID;

public class PositionQueryServiceIT {
    @Test
    public void setAlgorithmTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(PositionQueryServiceSIConfig.class);
        PositionQueryService positionQueryService = context.getBean("PositionQueryGateway", PositionQueryService.class);

        String response = positionQueryService.setPositioningAlgorithm("knn");
        System.out.println(response);
    }

    @Test
    public void getLocation(){
        ApplicationContext context = new AnnotationConfigApplicationContext(PositionQueryServiceSIConfig.class);
        PositionQueryService positionQueryService = context.getBean("PositionQueryGateway", PositionQueryService.class);
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setId(UUID.randomUUID().toString());
        PositionDTO positionDTO1 = new PositionDTO();
        positionDTO1.setId("1");
        measurementDTO.setPosition(positionDTO1);

        PositionDTO positionDTO = positionQueryService.getLocation(measurementDTO);
        System.out.println(positionDTO.getId());
    }
}
