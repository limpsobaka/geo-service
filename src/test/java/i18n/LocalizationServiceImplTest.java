package i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceImplTest {

  @MethodSource("testLocaleSource")
  @ParameterizedTest
  public void testLocale(Country country, String expected) {
    //assert
    LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

    //act
    String result = localizationService.locale(country);

    //assert
    assertEquals(expected, result);
  }

  public static Stream<Arguments> testLocaleSource() {
    return Stream.of(Arguments.of(Country.USA, "Welcome"),
            Arguments.of(Country.RUSSIA, "Добро пожаловать"),
            Arguments.of(Country.BRAZIL, "Welcome"),
            Arguments.of(Country.GERMANY, "Welcome"));
  }
}
