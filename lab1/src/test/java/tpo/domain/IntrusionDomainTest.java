package tpo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntrusionDomainTest {

    @Test
    @DisplayName("Ошибка: null-комната")
    void failsWhenRoomIsNull() {
        Attire attire = defaultAttire();
        Person intruder = new Person("I", Mood.ANGRY, Role.INTRUDER, attire);
        Person defender = new Person("D", Mood.NEUTRAL, Role.LACKEY, attire);

        assertThrows(NullPointerException.class, () -> new IntrusionEvent(null, List.of(intruder), List.of(defender)));
    }

    @Test
    @DisplayName("Ошибка: null-список нарушителей")
    void failsWhenIntruderListIsNull() {
        Attire attire = defaultAttire();
        Person defender = new Person("D", Mood.NEUTRAL, Role.LACKEY, attire);
        Room room = roomWithDoor();

        assertThrows(NullPointerException.class, () -> new IntrusionEvent(room, null, List.of(defender)));
    }

    @Test
    @DisplayName("Ошибка: null-список защитников")
    void failsWhenDefenderListIsNull() {
        Attire attire = defaultAttire();
        Person intruder = new Person("I", Mood.ANGRY, Role.INTRUDER, attire);
        Room room = roomWithDoor();

        assertThrows(NullPointerException.class, () -> new IntrusionEvent(room, List.of(intruder), null));
    }

    @Test
    @DisplayName("Ошибка: пустой список нарушителей")
    void failsWhenIntruderListEmpty() {
        Attire attire = defaultAttire();
        Person defender = new Person("D", Mood.NEUTRAL, Role.LACKEY, attire);
        Room room = roomWithDoor();

        assertThrows(IllegalArgumentException.class, () -> new IntrusionEvent(room, List.of(), List.of(defender)));
    }

    @Test
    @DisplayName("Ошибка: пустой список защитников")
    void failsWhenDefenderListEmpty() {
        Attire attire = defaultAttire();
        Person intruder = new Person("I", Mood.ANGRY, Role.INTRUDER, attire);
        Room room = roomWithDoor();

        assertThrows(IllegalArgumentException.class, () -> new IntrusionEvent(room, List.of(intruder), List.of()));
    }

    @Test
    @DisplayName("Ошибка: список нарушителей содержит null")
    void failsWhenIntruderListContainsNull() {
        Attire attire = defaultAttire();
        Person defender = new Person("D", Mood.NEUTRAL, Role.LACKEY, attire);
        Room room = roomWithDoor();

        assertThrows(NullPointerException.class, () -> new IntrusionEvent(room, List.of(new Person("I", Mood.ANGRY, Role.INTRUDER, attire), null), List.of(defender)));
    }

    @Test
    @DisplayName("Ошибка: в комнате нет дверей")
    void failsWhenRoomHasNoDoors() {
        Attire attire = defaultAttire();
        Person intruder = new Person("I", Mood.ANGRY, Role.INTRUDER, attire);
        Person defender = new Person("D", Mood.NEUTRAL, Role.LACKEY, attire);
        Room roomWithoutDoors = new Room("Empty hall");

        assertThrows(IllegalArgumentException.class, () -> new IntrusionEvent(roomWithoutDoors, List.of(intruder), List.of(defender)));
    }

    @Test
    @DisplayName("При активации события поднимается шум")
    void raisesNoiseOnTrigger() {
        Scenario scenario = createScenario();
        scenario.event().trigger();

        assertTrue(scenario.event().isNoiseRaised());
    }

    @Test
    @DisplayName("Открытая дверь может закрываться")
    void openDoorCanBeClosed() {
        Scenario scenario = createScenario();
        scenario.event().trigger();

        assertEquals(DoorState.OPEN, scenario.leftDoor().getState());
        assertEquals(DoorState.OPEN, scenario.rightDoor().getState());

        scenario.leftDoor().close();
        scenario.rightDoor().close();

        assertEquals(DoorState.CLOSED, scenario.leftDoor().getState());
        assertEquals(DoorState.CLOSED, scenario.rightDoor().getState());
    }

    @Test
    @DisplayName("Нарушители распахивают все двери")
    void opensAllDoorsWhenIntrudersBurstIn() {
        Scenario scenario = createScenario();
        scenario.event().trigger();


        assertEquals(DoorState.OPEN, scenario.leftDoor().getState());
        assertEquals(DoorState.OPEN, scenario.rightDoor().getState());
    }

    @Test
    @DisplayName("До вторжения в комнате только лакеи")
    void roomInitiallyContainsOnlyDefenders() {
        Scenario scenario = createScenario();

        assertEquals(2, scenario.room().getOccupants().size());
        assertTrue(scenario.room().getOccupants().containsAll(List.of(scenario.firstDefender(), scenario.secondDefender())));
    }

    @Test
    @DisplayName("После вторжения в комнате 4 человека")
    void intrudersJoinOccupantsAfterBurstingIn() {
        Scenario scenario = createScenario();
        scenario.event().trigger();

        assertTrue(scenario.room().getOccupants().containsAll(List.of(scenario.firstIntruder(), scenario.secondIntruder())));
        assertEquals(4, scenario.room().getOccupants().size());
    }

    @Test
    @DisplayName("Лакеи оттолкнуты")
    void defendersArePushedAside() {
        Scenario scenario = createScenario();
        scenario.event().trigger();

        assertTrue(scenario.firstDefender().isPushedAside());
        assertTrue(scenario.secondDefender().isPushedAside());
    }

    @Test
    @DisplayName("Лакеи тщетно пытаются преградить путь")
    void defendersAttemptToBlockPath() {
        Scenario scenario = createScenario();
        scenario.event().trigger();

        assertTrue(scenario.firstDefender().isAttemptedToBlock());
        assertTrue(scenario.secondDefender().isAttemptedToBlock());
    }

    @Test
    @DisplayName("Нарушители остаются сердитыми")
    void intrudersRemainAngry() {
        Scenario scenario = createScenario();

        assertEquals(Mood.ANGRY, scenario.firstIntruder().getMood());
        assertEquals(Mood.ANGRY, scenario.secondIntruder().getMood());
    }

    @Test
    @DisplayName("Нарушители в выцветших синих балахонах с поясами")
    void intrudersWearFadedBlueRobesWithBelts() {
        Scenario scenario = createScenario();
        Attire attire = scenario.firstIntruder().getAttire();

        assertEquals(ColorTone.FADED_BLUE, attire.getColorTone());
        assertEquals(GarmentType.ROBE, attire.getMainGarment());
        assertEquals(GarmentType.BELT, attire.getAccessory());
    }

    @Test
    @DisplayName("Принадлежность зафиксирована: Круксванский университет")
    void affiliationCapturedAsCruxvanUniversity() {
        Scenario scenario = createScenario();
        assertEquals("Cruxvan University", scenario.firstIntruder().getAttire().getAffiliation());
    }

    private Scenario createScenario() {
        Attire intruderAttire = new Attire(ColorTone.FADED_BLUE, GarmentType.ROBE, GarmentType.BELT, "Cruxvan University");
        Person firstIntruder = new Person("Intruder1", Mood.ANGRY, Role.INTRUDER, intruderAttire);
        Person secondIntruder = new Person("Intruder2", Mood.ANGRY, Role.INTRUDER, intruderAttire);

        Person firstDefender = new Person("Lackey1", Mood.NEUTRAL, Role.LACKEY, intruderAttire);
        Person secondDefender = new Person("Lackey2", Mood.NEUTRAL, Role.LACKEY, intruderAttire);

        Room room = new Room("Audience Hall");
        Door leftDoor = new Door("Left Door");
        Door rightDoor = new Door("Right Door");
        room.addDoor(leftDoor);
        room.addDoor(rightDoor);
        room.addOccupant(firstDefender);
        room.addOccupant(secondDefender);

        IntrusionEvent event = new IntrusionEvent(room, List.of(firstIntruder, secondIntruder), List.of(firstDefender, secondDefender));
        return new Scenario(event, room, leftDoor, rightDoor, firstIntruder, secondIntruder, firstDefender, secondDefender);
    }

    private Attire defaultAttire() {
        return new Attire(ColorTone.FADED_BLUE, GarmentType.ROBE, GarmentType.BELT, "Cruxvan University");
    }

    private Room roomWithDoor() {
        Room room = new Room("Hall");
        room.addDoor(new Door("Only Door"));
        return room;
    }

    private record Scenario(
            IntrusionEvent event,
            Room room,
            Door leftDoor,
            Door rightDoor,
            Person firstIntruder,
            Person secondIntruder,
            Person firstDefender,
            Person secondDefender
    ) {}
}
