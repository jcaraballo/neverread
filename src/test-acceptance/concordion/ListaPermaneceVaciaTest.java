package concordion;

import org.concordion.integration.junit3.ConcordionTestCase;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import server.NeverReadServer;

import java.io.File;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ListaPermaneceVaciaTest extends ConcordionTestCase {
    private WebDriver webDriver;

    @SuppressWarnings(value = "unused")
    public String articleListAfterAdding(String url) throws InterruptedException {
        webDriver.findElement(By.name("url")).sendKeys(url, Keys.ENTER);
        List<WebElement> pendingArticles = webDriver.findElements(By.cssSelector("li"));

        return convertListOfArticlesToString(pendingArticles);
    }

    private static String convertListOfArticlesToString(List<WebElement> pendingArticles) {
        if (pendingArticles.isEmpty()) {
            return "vacía";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(pendingArticles.get(0).getText());

            for (int i = 1; i < pendingArticles.size(); i++) {
                stringBuilder.append(", ").append(pendingArticles.get(i).getText());
            }
            return stringBuilder.toString();
        }
    }

    @Before
    public void setUp() throws Exception {
        startBuildAntWebApplication();
        webDriver = startWebDriver();
    }

    @After
    public void tearDown() throws Exception {
        webDriver.close();
    }

    private void startBuildAntWebApplication() {
        NeverReadServer helloTestServer = new NeverReadServer();
        helloTestServer.start(8081);
    }

    private WebDriver startWebDriver() {
//        WebDriver driver = new FirefoxDriver(new FirefoxBinary(new File("../tools/firefox-rc4.0.1-64bit/firefox-bin")), new FirefoxProfile());
        WebDriver driver = new HtmlUnitDriver();
        driver.get("http://localhost:8081");
        return driver;
    }
}