package com.almajeda.adhan_apis.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almajeda.adhan_apis.Service.GetAdhanByLocationService;
import com.almajeda.adhan_apis.api.body.LocationBody;
import com.almajeda.adhan_apis.data.dto.Location;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GetAdhanByLocation {

    private final GetAdhanByLocationService getAdhanByLocationService;

    @RequestMapping("/getAdhanByLocation")
    public float[] getAdhanByLocation(@RequestBody LocationBody locationBody) {
        Location location = new Location(locationBody);
        return getAdhanByLocationService.getAdhanByLocation(location);
    }

}
