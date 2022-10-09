package geo;

import entity.LocationToStringInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GeoServiceImplTest {
  GeoService geoService;

  @BeforeEach
  public void initEachTest() {
    geoService = new GeoServiceImpl();
  }

  @MethodSource("testByIpSource")
  @ParameterizedTest
  public void testByIp(String ip, Location expectedLocation) {
    //assert
    LocationToStringInterface locationToString = (Location location) -> location.getCountry() + " - "
            + location.getCity() + " - "
            + location.getStreet() + " - "
            + location.getBuiling();

    //act
    Location result = geoService.byIp(ip);

    //assert
    assertEquals(locationToString.toString(expectedLocation), locationToString.toString(result));
  }

  @Test
  public void testByCoordinates() {
    //assert
    //act
    //assert
    assertThrowsExactly(RuntimeException.class, () -> geoService.byCoordinates(10.5, 35.4),
            "Not implemented");
  }

  public static Stream<Arguments> testByIpSource() {
    Location localhost = new Location(null, null, null, 0);
    Location usaAdress = new Location("New York", Country.USA, " 10th Avenue", 32);
    Location russiaAdress = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
    Location russia = new Location("Moscow", Country.RUSSIA, null, 0);
    Location usa = new Location("New York", Country.USA, null, 0);
    return Stream.of(Arguments.of("172.123.12.19", russia),
            Arguments.of("96.125.32.45", usa),
            Arguments.of("172.56.47.26", russia),
            Arguments.of("172.95.123.56", russia),
            Arguments.of("96.81.10.23", usa),
            Arguments.of("96.0.0.5", usa),
            Arguments.of("127.0.0.1", localhost),
            Arguments.of("96.44.183.149", usaAdress),
            Arguments.of("172.0.32.11", russiaAdress));
  }
}
