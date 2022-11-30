package stepDefs;

import io.cucumber.java.AfterStep;
import io.qameta.allure.Attachment;
import lombok.Getter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import stepDefs.enumiration.Categories;
import stepDefs.enumiration.Filters;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Getter
public class StepDef {

    @ParameterType(".*")
    public Categories categories(String category) {
        return Categories.valueOf(category);
    }

    @ParameterType(".*")
    public Filters filters(String filter) {
        return Filters.valueOf(filter);
    }

    private WebDriver driver;

    @Before
    public void driverOpen() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Step("Открыт ресурс Авито")
    @Пусть("открыт ресурс авито")
    public void открытРесурсАвито() throws InterruptedException {
        driver.get("https://www.avito.ru/");
        Thread.sleep(2_000);
    }

    @Step("В выпажающем списке категорий выбрана {category}")
    @И("в выпадающем списке категорий выбрана {categories}")
    public void вВыпадающемСпискеКатегорийВыбранаКатегория(Categories category) throws InterruptedException {
        if (driver.findElement(By.xpath("//button")).getAttribute("class").contains("form-part-buttonElement")) {
            //version 1
            System.out.println("Avito version 1");
            WebElement rubricator = driver.findElement(By.xpath("//select[@name='category_id']"));
            rubricator.click();
            WebElement selector = driver.findElement(By.xpath("//option[text()='" + category.getName() + "']"));
            selector.click();
        } else if (driver.findElement(By.xpath("//a[@href='/tolyatti/transport']")).getAttribute("class").contains("xshq8")) {
            WebElement rubricator = driver.findElement(By.xpath("//button[contains(@class, 'rubricatorButton')]"));
            rubricator.click();
            Thread.sleep(1_000);
            if (driver.findElement(By.xpath("//div[@data-marker='more-popup']")).getAttribute("class").contains("top-rubricator-tooltip")) {
                //version 2.1
                System.out.println("Avito version 2.1");
                WebElement selector = driver.findElement(By.xpath("//p[contains(text(), 'Электроника')]"));
                selector.click();
                WebElement categoryButton = driver.findElement(By.xpath("//strong[contains(@data-name, '" + category.getName() + "')]"));
                categoryButton.click();
            } else {
                //version 2.2
                System.out.println("Avito version 2.2");
                WebElement categoryButton = driver.findElement(By.xpath("//a[text()='" + category.getName() + "']"));
                categoryButton.click();
            }
        } else {
            //version 3
            System.out.println("Avito version 3");
            WebElement rubricator = driver.findElement(By.xpath("//button[contains(@class, 'rubricatorButton')]"));
            rubricator.click();
            Thread.sleep(1_000);
            WebElement selector = driver.findElement(By.xpath("//p[contains(text(), 'Электроника')]"));
            selector.click();
            WebElement categoryButton = driver.findElement(By.xpath("//strong[contains(@data-name, '" + category.getName() + "')]"));
            categoryButton.click();
        }
    }
    @Step("В поле поиска введено значение {string}")
    @И("в поле поиска введено значение {word}")
    public void вПолеПоискаВведеноЗначение(String string) {
        WebElement input = driver.findElement(By.xpath("//input[@data-marker=\"search-form/suggest\"]"));
        input.sendKeys(string);
        input.sendKeys(Keys.RETURN);
    }

    @Step("активирован чекбокс только Новые")
    @И("активирован чекбокс только Новые")
    public void активированЧекбоксТолькоНовые() throws InterruptedException {
        //чекбокс с фотографией отсутствует
        Thread.sleep(1_000);
        WebElement checkBox = driver.findElement(By.xpath("//span[contains(@class, 'checkbox')][contains(text(), 'Новые')]"));
        checkBox.click();
    }

    @Step("кликнуть по выпадающему списку региона")
    @Тогда("кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        WebElement region = driver.findElement(By.xpath("//div[@data-marker='search-form/region']"));
        region.click();
    }

    @Step("в поле регион введено значение {arg0}")
    @Тогда("в поле регион введено значение {word}")
    public void вПолеРегионВведеноЗначение(String arg0) throws InterruptedException {
        WebElement input = driver.findElement(By.xpath("//input[@data-marker='popup-location/region/input']"));
        input.sendKeys(arg0);
        Thread.sleep(1_000);
        WebElement region = driver.findElement(By.xpath("//strong[contains(text(), '" + arg0 + "')]/ancestor::li"));
        region.click();
        Thread.sleep(1_000);
    }

    @Step("нажата кнопка поиска объявления")
    @И("нажата кнопка поиска объявления")
    public void нажатаКнопкаПоискаОбъявления() {
        WebElement saveButton = driver.findElement(By.xpath("//button[@data-marker='popup-location/save-button']"));
        saveButton.click();
    }

    @Step("открылась страница: Результаты по запросу {arg0}")
    @Тогда("открылась страница результаты по запросу {word}")
    public void открыласьСтраницаРезультатыПоЗапросу(String arg0) {
        WebElement header = driver.findElement(By.xpath("//h1"));
        String actual = header.getText();
        System.out.println(actual + ":");
        assertTrue(actual.contains("Объявления по запросу «" + arg0 + "»"));
    }

    @Step("В выпадающем списке сортировка выбрано значние {arg0}")
    @И("в выпадающем списке сортировка выбрано значение {filters}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначение(Filters arg0) throws InterruptedException {
        WebElement filter = driver.findElement(By.xpath("//option[text()='" + arg0.getName() + "']"));
        Thread.sleep(1_000);
        filter.click();
    }

    @Step("В консоль выведено значние названия и цены {arg0} первых товаров")
    @И("в консоль выведено значение названия и цены {int} первых товаров")
    public void вКонсольВыведеноЗначениеНазванияИЦеныПервыхТоваров(int arg0) {
        List<WebElement> names = driver.findElements(By.xpath("//h3"));
        List<WebElement> prices = driver.findElements(By.xpath("//span[contains(@class,'price')]//meta[@itemprop='price']"));
        String content;
        for (int i = 0; i < arg0; i++) {
            System.out.print(i + 1 + " : ");
            content = prices.get(i).getAttribute("content");
            System.out.println(names.get(i).getText() + ": " + content + " рублей.");
        }
    }

    @SuppressWarnings("all")
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver) {
        Screenshot screenshot = new AShot().takeScreenshot(driver);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            ImageIO.write(screenshot.getImage(), "PNG", buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toByteArray();
    }

    @AfterStep
    public void after() {
        takeScreenshot(driver);
    }

    @After
    public void driverQuit() {
        driver.quit();
    }
}
