import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Disabled("Тест отключен по условиям задачи")
    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    void mainTimeoutTest() throws Exception {
        Main.main(new String[1]);
    }

}