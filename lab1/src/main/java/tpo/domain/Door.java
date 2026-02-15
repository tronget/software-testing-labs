package tpo.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Door {
    @NonNull private final String name;
    private DoorState state = DoorState.CLOSED;

    public void open() {
        state = DoorState.OPEN;
    }

    public void close() {
        state = DoorState.CLOSED;
    }
}
