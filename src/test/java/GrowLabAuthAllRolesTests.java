import org.testng.annotations.Test;

public class GrowLabAuthAllRolesTests extends TestBase {
    @Test
    public void loginAsManagerPositiveTest() {
        login("billye@example.com");
        pause(1500);
        assertTrueByPageSource("PROJECT OVERVIEW"); //check if there is element with this text on a page
        assertTrueByPageSource("CLIENTS");          // same
        assertTrueByPageSource("TEAM");             // same
        assertTrueByPageSource("INVOICES");         // same
        logoutForManagerAcc();
    }

    @Test
    public void loginAsClientPositiveTest() {
        login("lucie@example.com");
        pause(1500);
        assertTrueByPageSource("PROJECTS OVERVIEW");    //check if there is element with this text on a page
        assertFalseByPageSource("CLIENTS");             //check if there is NOT an element with this text on a page
        assertFalseByPageSource("TEAM");                //check if there is NOT an element with this text on a page
        assertTrueByPageSource("INVOICES");             //check if there is element with this text on a page
        logoutForClientAcc();
    }

    @Test
    public void loginAsConsultantPositiveTest() {
        login("edra@example.com");
        pause(1500);
        assertTrueByPageSource("PROJECT OVERVIEW"); // same as in the test for manager acc
        assertTrueByPageSource("CLIENTS");
        assertTrueByPageSource("TEAM");
        assertTrueByPageSource("INVOICES");
        logoutForConsultantAcc();
    }




}
