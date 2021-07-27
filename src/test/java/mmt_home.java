import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class mmt_home {
    private WebDriver driver;
    public WebDriverWait wait;
    public void waitforelement(int timeout,By locator)
    {
        WebDriverWait wait=new WebDriverWait(driver,timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @BeforeTest
    public void setup()
    {
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/");


    }
    @Test
    public void TC001_SelectRadioButton()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        By radio_round_trip=By.xpath("//li[@data-cy='roundTrip']");
        By radio_oneway=By.xpath("oneWayTrip");
        WebElement radio_round_element=driver.findElement(By.xpath("//li[@data-cy='roundTrip']"));
        js.executeScript("arguments[0].scrollIntoView();",radio_round_element);
        waitforelement(20,radio_round_trip);
        if(driver.findElement(radio_round_trip).isSelected())
        {
            driver.findElement(radio_oneway).click();
        }

    }
    @Test
    public void TC002_EnterFromCity() throws InterruptedException {
        By from_city_label = By.xpath("//div[@class='fsw_inputBox searchCity inactiveWidget ']/label");
        waitforelement(15, from_city_label);
        driver.findElement(from_city_label).click();
        By from_city_autotext = By.xpath("//div[@class='hsw_autocomplePopup autoSuggestPlugin ']/div/input");
        waitforelement(15, from_city_autotext);
        driver.findElement(from_city_autotext).sendKeys("BLR");
        Thread.sleep(5000);
        List<WebElement> Listofitems = driver.findElements(By.xpath("//li[@role='option']"));
        for(int i=0;i<Listofitems.size();i++)
        {
            String text=Listofitems.get(i).getText();
            if(text.contains("Bengaluru"))
            {
                Listofitems.get(i).click();
                break;
            }
        }


}
    @Test
    public void TC003_assert_FromCity()
    {
        WebElement lbl_fromcity=driver.findElement(By.id("fromCity"));
        String actual_text=lbl_fromcity.getAttribute("value");
        String expected_text="Bengaluru";
        Assert.assertEquals(actual_text,expected_text);

    }
    @Test
    public void TC004_selectDate() throws InterruptedException {
        By datepicker=By.xpath("//div[@class='fsw_inputBox dates inactiveWidget ']/label");
        waitforelement(15,datepicker);
        driver.findElement(datepicker).click();
        Thread.sleep(3000);
        List<WebElement> dates=driver.findElements(By.xpath("//div[@aria-disabled='false']//div"));
        String first_month=driver.findElement(By.xpath("(//div[@class='DayPicker-Caption']//div)[1]")).getText();
        System.out.println(first_month);
        String second_month=driver.findElement(By.xpath("(//div[@class='DayPicker-Caption']//div)[2]")).getText();
        System.out.println(second_month);

    }


    @AfterTest
        public void teardown ()
        {
            driver.close();
        }

    }
