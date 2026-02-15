package tpo.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IntrusionEvent {
    private final Room room;
    private final List<Person> intruders;
    private final List<Person> defenders;
    @Getter private boolean noiseRaised;

    public IntrusionEvent(Room room, List<Person> intruders, List<Person> defenders) {
        this.room = Objects.requireNonNull(room);
        this.intruders = copyValidated(intruders, "intruders");
        this.defenders = copyValidated(defenders, "defenders");
        if (room.getDoors().isEmpty()) {
            throw new IllegalArgumentException("room must have at least one door");
        }
    }

    public void trigger() {
        noiseRaised = true;
        for (Door door : room.getDoors()) {
            door.open();
        }
        for (Person intruder : intruders) {
            room.addOccupant(intruder);
        }
        for (Person defender : defenders) {
            defender.pushAside();
        }
    }

    private static List<Person> copyValidated(List<Person> source, String name) {
        Objects.requireNonNull(source, name + " list is null");
        if (source.isEmpty()) {
            throw new IllegalArgumentException(name + " list is empty");
        }
        List<Person> copy = new ArrayList<>(source.size());
        for (Person p : source) {
            copy.add(Objects.requireNonNull(p, name + " contains null"));
        }
        return copy;
    }
}
