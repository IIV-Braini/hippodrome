import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {
    @ParameterizedTest
    @CsvSource({ "Василек", "Петрушка", "Конь" })
    void getNameTest(String name) {
        assertEquals(name, new Horse(name, 1).getName());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 20000.0, 3.0, 0.0000001 })
    void getSpeedTest(double speed) {
        assertEquals(speed, new Horse("name", speed).getSpeed());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 20000.0, 3.0, 0.0000001 })
    void getDistanceTest(double distance) {
        assertEquals(distance, new Horse("name", 1.0, distance).getDistance());
        assertEquals(0, new Horse("name", 1.0).getDistance());
    }

    @ParameterizedTest
    @CsvSource({ "0.00001, 0.0", "2.2, 10.0", "15646456, 3,6" })
    void moveTest(double speed, double distance) {
        Horse horse = spy(new Horse("Василек", speed, distance));
        try (MockedStatic<Horse> utilities  = Mockito.mockStatic(Horse.class)) {
            utilities.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(1.0);
            horse.move();
            assertEquals(distance + speed, horse.getDistance());
            utilities.verify(horse::move);
        }
    }

    @ParameterizedTest
    @NullSource
    void whenHorseConstructorSubmitNull(String name){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Horse(name,  1.0);
                }
        );
        assertEquals( "Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("emptyStringFactory")
    void whenHorseConstructorSubmitEmptyName(String name){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Horse("  ", 1.0);
                }
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    static Stream<String> emptyStringFactory() {
        return Stream.of("  ", "          ",  "  ", "", "                      ");
    }

    @ParameterizedTest
    @ValueSource(doubles = { -20000.0, -3.0, -0.0000001 })
    void whenHorseConstructorSubmitNegativeSpeed (double speed) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Horse("name", speed);
                }
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { -20000.0, -3.0, -0.0000001 })
    void whenHorseConstructorSubmitNegativeDistance (double distance) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Horse("name", 1.0, distance);
                }
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

}