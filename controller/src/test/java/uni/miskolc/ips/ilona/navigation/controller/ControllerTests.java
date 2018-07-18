package uni.miskolc.ips.ilona.navigation.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ NavigationPersonRequestTest.class, NavigationZoneRequestTest.class,
		OntologyGenerationControllerTest.class, WayFindingControllerTest.class })
public class ControllerTests {

}
