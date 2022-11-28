import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GrowLabSearchTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() throws InterruptedException {
        loginAsManager();
        Thread.sleep(2000);
        dr.get("https://derrick686.softr.app/clients");
    }

    @Test
    public void searchByCompanyPositiveTest() throws InterruptedException {
        searchFieldFill("montag");
        click(By.xpath("/html/body/div[1]/section[2]/div[2]/div/div/div[1]/div[1]/a"));
        String managerCardHref = "https://derrick686.softr.app/profile?recordId=recm13ay4zumPZQDB";
        Assert.assertEquals(dr.getCurrentUrl(), managerCardHref);
    }

    @Test
    public void searchByCompanyPositiveTest_2PersonFound() throws InterruptedException {
        searchFieldFill("montag");
        int peopleFound = dr.findElements(By.cssSelector(".js-list-item.position-relative")).size();
        Assert.assertEquals(peopleFound, 2);
    }

    @Test
    public void searchByCompanyPositiveTest_1PersonFound() throws InterruptedException {
        searchFieldFill("worman");
        int peopleFound = dr.findElements(By.cssSelector(".js-list-item.position-relative")).size();
        Assert.assertEquals(peopleFound, 1);
    }

    @Test
    public void searchByNamePositiveTest() throws InterruptedException {
        searchFieldFill("lucie");
        String otherNames = dr.getPageSource();
        Assert.assertTrue(otherNames.contains("lucie"));
        Assert.assertFalse(otherNames.contains("billye"));

        }

        @Test
    public void searchNegativeTest() throws InterruptedException {
            searchFieldFill("qwerty");
            String noResultsFound = dr.getPageSource();
            Assert.assertTrue(noResultsFound.contains("No results found, try adjusting your search and filters"));
        }
 //




    }

