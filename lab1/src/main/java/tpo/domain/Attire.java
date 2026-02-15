package tpo.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Attire {
    @NonNull private final ColorTone colorTone;
    @NonNull private final GarmentType mainGarment;
    @NonNull private final GarmentType accessory;
    @NonNull private final String affiliation;
}
