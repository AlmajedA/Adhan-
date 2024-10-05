package com.almajeda.adhanApis.api.body;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationBody {
    private float latitude;
    private float longitude;
    private float elevation;
    private float timeZone;
}
