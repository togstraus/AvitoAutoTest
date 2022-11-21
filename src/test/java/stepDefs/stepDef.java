package stepDefs;

import enumiration.Categories;
import enumiration.Filters;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

public class stepDef {

    @ParameterType(".*")
    public Categories categories(String category) {
        return Categories.valueOf(category);
    }

    @ParameterType(".*")
    public Filters filters(String filter) {
        return Filters.valueOf(filter);
    }

    WebDriver driver = new ChromeDriver();

    @Пусть("открыт ресурс авито")
    public void открытРесурсАвито() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver.get("https://www.avito.ru/");
    }

    @И("в выпадающем списке категорий выбрана {categories}")
    public void вВыпадающемСпискеКатегорийВыбранаКатегория(Categories category) throws InterruptedException {
        if (driver.findElement(By.xpath("//button")).getAttribute("class").contains("form-part-buttonElement")) {
            //version 1
            try {
                System.out.println("Avito version 1");
                WebElement rubricator = driver.findElement(By.xpath("//select[@name='category_id']"));
                rubricator.click();
                WebElement selector = driver.findElement(By.xpath("//option[text()='" + category.getName() + "']"));
                selector.click();
            } catch (Exception e) {
                throw new NoSuchElementException(e);
            }
        } else if (driver.findElement(By.xpath("//a[@href='/tolyatti/transport']")).getAttribute("class").contains("xshq8")) {
            WebElement rubricator = driver.findElement(By.xpath("//button[contains(@class, 'rubricatorButton')]"));
            rubricator.click();
            Thread.sleep(1_000);
            if (driver.findElement(By.xpath("//div[@data-marker='more-popup']")).getAttribute("class").contains("top-rubricator-tooltip")) {
                //version 2.1
                try {
                    System.out.println("Avito version 2.1");
                    WebElement selector = driver.findElement(By.xpath("//p[contains(text(), 'Электроника')]"));
                    selector.click();
                    WebElement categoryButton = driver.findElement(By.xpath("//strong[contains(@data-name, '" + category.getName() + "')]"));
                    categoryButton.click();
                } catch (Exception e) {
                    throw new NoSuchElementException(e);
                }
            } else {
                //version 2.2
                try {
                    System.out.println("Avito version 2.2");
                    WebElement categoryButton = driver.findElement(By.xpath("//a[text()='" + category.getName() + "']"));
                    categoryButton.click();
                } catch (Exception e) {
                    throw new NoSuchElementException(e);
                }
            }
        } else {
            //version 3
            try {
                System.out.println("Avito version 3");
                WebElement rubricator = driver.findElement(By.xpath("//button[contains(@class, 'rubricatorButton')]"));
                rubricator.click();
                Thread.sleep(1_000);
                WebElement selector = driver.findElement(By.xpath("//p[contains(text(), 'Электроника')]"));
                selector.click();
                WebElement categoryButton = driver.findElement(By.xpath("//strong[contains(@data-name, '" + category.getName() + "')]"));
                categoryButton.click();
            } catch (Exception e) {
                throw new NoSuchElementException(e);
            }
        }
    }

    @И("в поле поиска введено значение {word}")
    public void вПолеПоискаВведеноЗначение(String string) {
        WebElement input = driver.findElement(By.xpath("//input[@data-marker=\"search-form/suggest\"]"));
        input.sendKeys(string);
        input.sendKeys(Keys.RETURN);
    }

    @И("активирован чекбокс только Новые")
    public void активированЧекбоксТолькоНовые() throws InterruptedException {
        //чекбокс с фотографией отсутствует
        Thread.sleep(1_000);
        WebElement checkBox = driver.findElement(By.xpath("//span[contains(@class, 'checkbox')][contains(text(), 'Новые')]"));
        ///ancestor::label"
//        WebElement checkBox = new WebDriverWait(driver, Duration.ofMillis(2_000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'checkbox')][contains(text(), 'Новые')]/ancestor::label")));
        checkBox.click();
    }

    @Тогда("кликнуть по выпадающему списку региона")
    public void кликнутьПоВыпадающемуСпискуРегиона() {
        WebElement region = driver.findElement(By.xpath("//div[@data-marker='search-form/region']"));
        region.click();
    }

    @Тогда("в поле регион введено значение {word}")
    public void вПолеРегионВведеноЗначение(String arg0) throws InterruptedException {
        WebElement input = driver.findElement(By.xpath("//input[@data-marker='popup-location/region/input']"));
        input.sendKeys(arg0);
        Thread.sleep(1_000);
        WebElement region = driver.findElement(By.xpath("//strong[contains(text(), '" + arg0 + "')]/ancestor::li"));
        region.click();
        Thread.sleep(1_000);
    }

    @И("нажата кнопка поиска объявления")
    public void нажатаКнопкаПоискаОбъявления() {
        WebElement saveButton = driver.findElement(By.xpath("//button[@data-marker='popup-location/save-button']"));
        saveButton.click();
    }

    @Тогда("открылась страница результаты по запросу {word}")
    public void открыласьСтраницаРезультатыПоЗапросу(String arg0) {
        WebElement header = driver.findElement(By.xpath("//h1"));
        String actual = header.getText();
        System.out.println(actual + ":");
        assertTrue(actual.contains("Объявления по запросу «" + arg0 + "»"));
    }

    @И("в выпадающем списке сортировка выбрано значение {filters}")
    public void вВыпадающемСпискеСортировкаВыбраноЗначение(Filters arg0) throws InterruptedException {
        WebElement filter = driver.findElement(By.xpath("//option[text()='" + arg0.getName() + "']"));
        Thread.sleep(1_000);
        filter.click();
    }

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
}
