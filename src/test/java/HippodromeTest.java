import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Лошадь №" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses()); // Проверяем объекты. Если это один и тот же объект, значит он содержит те же объекты и в той же последовательности
    }

    @Test
    void moveTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(spy(new Horse("Лошадь №" + i, i)));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (int i = 0; i < horses.size(); i++) {
            verify(horses.get(i)).move();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "9999.01, 9999, 9999.00000001",
            "0.01, 0.001, 0.00000001",
            "3, 2, 1",
    })
    void getWinnerTest(double distance1, double distance2, double distance3) {
        List<Horse> horses = List.of(
                new Horse("Василек", 1, distance1),
                new Horse("Ромашка", 3, distance2),
                new Horse("Лютик", 10, distance3)
        );
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals("Василек", hippodrome.getWinner().getName());
    }

    @ParameterizedTest
    @NullSource
    void whenHippodromeConstructorSubmitNull(List<Horse> horses){
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Hippodrome(horses);
                }
        );
        assertEquals( "Horses cannot be null.", exception.getMessage());
    }


    @Test
    void whenHorseConstructorSubmitEmptyList(){
        List<Horse> horses = new ArrayList<>();
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Hippodrome(horses);
                }
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }
}