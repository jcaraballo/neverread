package concordion.v2;

import concordion.v2.tools.NeverReadDriver;
import org.concordion.integration.junit3.ConcordionTestCase;
import org.junit.After;
import org.junit.Before;
import server.NeverReadServer;

import java.util.List;

public class ListaPermaneceVaciaTest extends ConcordionTestCase {
    private NeverReadDriver driver;
    private NeverReadServer neverread;

    @SuppressWarnings(value = "unused")
    public String articleListAfterAdding(String article) throws InterruptedException {
        driver.addArticle(article);
        return convertListOfArticlesToString(driver.getListOfArticles());
    }

    private static String convertListOfArticlesToString(List<String> pendingArticles) {
        if (pendingArticles.isEmpty()) {
            return "vacía";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(pendingArticles.get(0));

            for (int i = 1; i < pendingArticles.size(); i++) {
                stringBuilder.append(", ").append(pendingArticles.get(i));
            }
            return stringBuilder.toString();
        }
    }

    @Before
    public void setUp() throws Exception {
        startApplication(8081);
        driver = NeverReadDriver.start("http://localhost:8081");
    }

    @After
    public void tearDown() throws Exception {
        driver.close();
        neverread.stop();
    }

    private void startApplication(int port) {
        neverread = new NeverReadServer();
        neverread.start(port);
    }
}