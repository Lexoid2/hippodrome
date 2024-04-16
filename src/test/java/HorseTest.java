import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {

    private static final Horse testHorse = new Horse("Dusty", 2.5, 1.0);

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if name is null")
    void testConstructorWithNullName() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 0.0));

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Constructor should throw IllegalArgumentException for blank names")
    @ValueSource(strings = { " ", "\t", "\n", "\f", "\r", "\u000B",
            "\u001C", "\u001D", "\u001E", "\u001F", "\u2028", "\u2029" })
    void testConstructorWithEmptyName(String name) {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 0.0));

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if speed is negative")
    void testConstructorWithNegativeSpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Sunshine", -1.0));

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException if distance is negative")
    void testConstructorWithNegativeDistance() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Cloud", 0.0, -1.0));

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("GetName should return the correct name")
    void testGetName() {
        assertEquals("Dusty", testHorse.getName());
    }

    @Test
    @DisplayName("GetSpeed should return the correct speed")
    void testGetSpeed() {
        assertEquals(2.5, testHorse.getSpeed());
    }

    @Test
    @DisplayName("GetDistance should return initial distance for a known horse")
    void testGetDistanceInitial() {
        assertEquals(1.0, testHorse.getDistance());
    }

    @Test
    @DisplayName("GetDistance should return zero for a new horse")
    void testGetDistanceNewHorse() {
        assertEquals(0.0, new Horse("Rose", 2.0).getDistance());
    }

    @ParameterizedTest
    @DisplayName("Move method should calculate distance correctly based on random double input")
    @CsvSource({
            "0.28, 0.7",
            "0.48, 1.2",
            "0.55, 1.375",
            "0.21, 0.525",
            "0.73, 1.825"
    })
    void testMove(double randomDouble, double result) {
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);
            Horse horse = new Horse("Bella", 2.5);
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            assertEquals(horse.getDistance(), result, 0.001);
        }
    }
}
