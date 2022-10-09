package entity;

import ru.netology.entity.Location;

@FunctionalInterface
public interface LocationToStringInterface {
  String toString(Location location);
}
