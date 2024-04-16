import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if horse list is null")
    void testConstructorWithNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if horse list is empty")
    void testConstructorWithEmptyList() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("getHorses should return the correct list of horses")
    void testGetHorses() {
        List<Horse> listOfHorses = new ArrayList<>();

        for (int i = 0; i < 30; i++)
            listOfHorses.add(mock(Horse.class));

        assertEquals(new Hippodrome(listOfHorses).getHorses(), listOfHorses);
    }

    @Test
    @DisplayName("move should trigger the move method on all horses")
    void testMove() {
        List<Horse> listOfHorses = new ArrayList<>();

        for (int i = 0; i < 50; i++)
            listOfHorses.add(mock(Horse.class));

        new Hippodrome(listOfHorses).move();

        for (Horse horse : listOfHorses)
            verify(horse).move();
    }

    @Test
    @DisplayName("getWinner should return the horse with the greatest distance")
    void testGetWinner() {
        Hippodrome hippodrome = new Hippodrome(List.of(
                new Horse("Bella", 3.5, 4.6),
                new Horse("Chance", 4.2, 5.0),
                new Horse("Dakota", 1.5, 1.4),
                new Horse("Lady", 2.8, 3.3),
                new Horse("Cash", 0.6, 0.4)));

        assertEquals(hippodrome.getHorses().stream()
                .max(Comparator.comparingDouble(Horse::getDistance))
                .orElseThrow(() -> new NoSuchElementException("No horses in the race")),
                hippodrome.getWinner());
    }
}
