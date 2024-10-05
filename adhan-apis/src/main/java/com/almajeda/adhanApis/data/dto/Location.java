package com.almajeda.adhanApis.data.dto;

import java.time.LocalDateTime;

import com.almajeda.adhanApis.api.body.LocationBody;

import lombok.Getter;

@Getter
public class Location {
    private float latitude;
    private float longitude;
    private float elevation;
    private float timeZone;
    private int shadowFactor = 1;
    private int jafariFajrAngle = 16;
    private int jafariIshaAngle = 14;
    private LocalDateTime currentDateTime = LocalDateTime.now();

    public Location(LocationBody locationBody) {
        this.latitude = locationBody.getLatitude();
        this.longitude = locationBody.getLongitude();
        this.elevation = locationBody.getElevation();
        this.timeZone = locationBody.getTimeZone();
    }

}
