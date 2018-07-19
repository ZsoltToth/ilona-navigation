package uni.miskolc.ips.ilona.navigation.controller;

import com.mysql.jdbc.Driver;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uni.miskolc.ips.ilona.navigation.service.OntologyGenerationService;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContext.class)
public class OntologyGenerationControllerIntegrationTest {

    public static final String TEST_CREATE_TABLES = "src/resources/createTables.sql";
    public static final String TEST_DROP_TABLES = "src/resources/dropTables.sql";
    public static final String TEST_SETUP_SQL = "src/resources/setupTestDB.sql";
    public static final String TEST_TEAR_DOWN_SQL = "src/resources/teardownTestDB.sql";

    protected static String HOST;
    protected static int PORT;
    protected static String DATABASE;
    protected static String USER;
    protected static String PASSWORD;


    @Autowired
    private OntologyGenerationService ontologyGenerationService;
    private OntologyGenerationController test;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        /* Check SQL Scripts */
        File createTablesSQL = new File(TEST_CREATE_TABLES);
        File dropTablesSQL = new File(TEST_DROP_TABLES);
        File setupTestSQL = new File(TEST_SETUP_SQL);
        File teardownSQL = new File(TEST_TEAR_DOWN_SQL);

        Assume.assumeTrue(createTablesSQL.exists());
        Assume.assumeTrue(dropTablesSQL.exists());
        Assume.assumeTrue(setupTestSQL.exists());
        Assume.assumeTrue(teardownSQL.exists());

        /* Check System Properties */
        String host = System.getProperty("database.host");

        int port = -1;
        try {
            port = Integer.parseInt(System.getProperty("database.port"));
            System.out.println(port);
        } catch (NumberFormatException ex) {
            port = -1;
            Assume.assumeNoException(ex);
        }
        String database = System.getProperty("database.db");
        String user = System.getProperty("database.user");
        String password = System.getProperty("database.password");
        Assume.assumeNotNull(host, port, database, user, password);
        HOST = host;
        PORT = port;
        DATABASE = database;
        USER = user;
        PASSWORD = password;

        try {
            runSQLScript(TEST_CREATE_TABLES);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }


    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

        try {
            runSQLScript(TEST_DROP_TABLES);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }

    }

    @Before
    public void setUp() throws Exception {

        /* INSERT TEST RECORDS */
        try {
            runSQLScript(TEST_SETUP_SQL);
        } catch (Exception e) {
            Assume.assumeNoException(e);
        }

        test = new OntologyGenerationController(ontologyGenerationService);

    }


    @Test
    public void test() {
        test.index();

    }

    private static void runSQLScript(String scriptFile)
            throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

        Class.forName(Driver.class.getName());
        final String connectionURL = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE);
        Connection connection = DriverManager.getConnection(connectionURL, USER, PASSWORD);
        ScriptRunner runner = new ScriptRunner(connection);
        Reader reader = new FileReader(scriptFile);
        runner.runScript(reader);
        connection.close();
        reader.close();
    }

}
