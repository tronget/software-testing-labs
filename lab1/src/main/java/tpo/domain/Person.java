package tpo.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Person {
    @NonNull private final String name;
    @NonNull private final Mood mood;
    @NonNull private final Role role;
    @NonNull private final Attire attire;
    private boolean pushedAside;

    public void pushAside() {
        this.pushedAside = true;
    }
}
