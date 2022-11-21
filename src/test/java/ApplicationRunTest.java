import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("unused")
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefs",
        tags = "@avito"
)
public class ApplicationRunTest {
    @Test
    public static void run(String[] args) {
    }
}
