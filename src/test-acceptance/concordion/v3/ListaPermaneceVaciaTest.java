package concordion.v3;

import concordion.Functional;
import concordion.v3.tools.PageDriver;
import org.concordion.integration.junit3.ConcordionTestCase;
import org.junit.After;
import org.junit.Before;
import server.NeverReadServer;

import java.util.List;

import static concordion.Functional.join;

public class ListaPermaneceVaciaTest extends ConcordionTestCase {
    private PageDriver page;
    private NeverReadServer neverread;

    @SuppressWarnings(value = "unused")
    public String articleListAfterAdding(String url) throws InterruptedException {
        page.enterIntoNewArticlesTextBox(url);
        List<String> pendingArticles = page.getArticlesInListOfArticles();

        return convertListOfArticlesToString(pendingArticles);
    }

    private static String convertListOfArticlesToString(List<String> pendingArticles) {
        String joined = join(pendingArticles, ", ");
        return joined.isEmpty() ? "vacía" : joined;
    }

    @Before
    public void setUp() throws Exception {
        startBuildAntWebApplication(8081);
        page = PageDriver.start("http://localhost:8081");
    }

    @After
    public void tearDown() throws Exception {
        page.close();
        neverread.stop();
    }

    private void startBuildAntWebApplication(int port) {
        neverread = new NeverReadServer();
        neverread.start(port);
    }
}