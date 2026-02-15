package tpo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Room {
    @NonNull private final String name;
    private final List<Door> doors = new ArrayList<>();
    private final List<Person> occupants = new ArrayList<>();

    public void addDoor(Door door) {
        doors.add(Objects.requireNonNull(door));
    }

    public void addOccupant(Person person) {
        occupants.add(Objects.requireNonNull(person));
    }

    public List<Door> getDoors() {
        return Collections.unmodifiableList(doors);
    }

    public List<Person> getOccupants() {
        return Collections.unmodifiableList(occupants);
    }
}
