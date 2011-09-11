package concordion;

import org.concordion.integration.junit3.ConcordionTestCase;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.junit.Before;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import server.NeverReadServer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ListaPermaneceVaciaTest extends ConcordionTestCase {
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        startBuildAntWebApplication();
        startSeleniumServer();
        selenium = startSeleniumRemoteControl();
    }

    @SuppressWarnings(value = "unused")
    public String articleListAfterAdding(String url) throws InterruptedException {
        selenium.type("url", url);
        selenium.type("url", "\13");
        int numberOfPendingArticles = selenium.getCssCount("css=li").intValue();
        List<String> pendingArticles = new ArrayList<String>();
        for (int i=1; i<=numberOfPendingArticles; i++){
            pendingArticles.add(selenium.getText("css=li:nth-of-type(" + i + ")"));
        }

        if(pendingArticles.size()==0){
            return "vacia";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(pendingArticles.get(0));

            for (int i = 1; i < pendingArticles.size(); i++) {
                stringBuilder.append(", ").append(pendingArticles.get(i));
            }
            return stringBuilder.toString();
        }
    }

    private void startBuildAntWebApplication() {
        NeverReadServer helloTestServer = new NeverReadServer();
        helloTestServer.start(8081);
    }

    private Selenium startSeleniumRemoteControl() {
        // tip: use firefox-4.0-32bit or similar for 32 bit linux installations
        Selenium selenium = new DefaultSelenium("localhost", 9091, "*firefox tools/firefox-rc4.0.1-64bit/firefox-bin", "http://localhost:8081");
        selenium.start();

        selenium.open("http://localhost:8081");
        selenium.setTimeout("60000");
        return selenium;
    }

    private void startSeleniumServer() throws Exception {
        RemoteControlConfiguration remoteControlConfiguration = new RemoteControlConfiguration();
        remoteControlConfiguration.setSingleWindow(true);
        remoteControlConfiguration.setPort(9091);
        Logger.getLogger("org.openqa.selenium").setLevel(Level.WARNING);
        new SeleniumServer(false, remoteControlConfiguration).start();
    }
}