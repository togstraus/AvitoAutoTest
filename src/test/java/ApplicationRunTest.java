import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
@SuppressWarnings("unused")
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefs",
        tags = "@avito"
)
public class ApplicationRunTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
