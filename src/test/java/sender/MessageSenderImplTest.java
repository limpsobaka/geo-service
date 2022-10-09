package sender;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderImplTest {

  @MethodSource("testSendSource")
  @ParameterizedTest
  public void testSend(String ip, String expectedString) {
    //assert
    GeoService geoService = Mockito.mock(GeoService.class);
    Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
            .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
    Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
            .thenReturn(new Location("New York", Country.USA, null, 0));

    LocalizationService localizationService = Mockito.mock(LocalizationService.class);
    Mockito.when(localizationService.locale(Country.RUSSIA))
            .thenReturn("Добро пожаловать");
    Mockito.when(localizationService.locale(Country.USA))
            .thenReturn("Welcome");

    MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
    Map<String, String> headers = new HashMap<>();
    headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

    //act
    String result = messageSender.send(headers);

    //assert
    assertEquals(expectedString, result);
  }

  public static Stream<Arguments> testSendSource() {
    return Stream.of(Arguments.of("172.123.12.19", "Добро пожаловать"),
            Arguments.of("172.162.25.85", "Добро пожаловать"),
            Arguments.of("172.1.23.65", "Добро пожаловать"),
            Arguments.of("172.98.25.36", "Добро пожаловать"),
            Arguments.of("96.44.183.149", "Welcome"),
            Arguments.of("96.23.48.24", "Welcome"),
            Arguments.of("96.129.32.125", "Welcome"),
            Arguments.of("96.234.12.54", "Welcome"),
            Arguments.of("96.97.146.250", "Welcome"));
  }
}
