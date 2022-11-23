import io.cucumber.java.AfterStep;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.webdriver;

public class hook {
    @Attachment(value = "Screenshot", type = "image/png")
    @SuppressWarnings("all")
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
    @SuppressWarnings("unused")
    public void after() {
        takeScreenshot(webdriver().driver().getWebDriver());
    }
}
