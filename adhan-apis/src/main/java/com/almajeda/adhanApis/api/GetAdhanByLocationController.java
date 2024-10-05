package com.almajeda.adhanApis.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.almajeda.adhanApis.Service.GetAdhanByLocationService;
import com.almajeda.adhanApis.api.body.LocationBody;
import com.almajeda.adhanApis.data.dto.Location;

import java.util.Map;
import java.util.HashMap;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GetAdhanByLocationController {

    private final GetAdhanByLocationService getAdhanByLocationService;

    @RequestMapping("/getAdhanByLocation")
    public ResponseEntity<Map<String, Object>> getAdhanByLocation(@RequestBody LocationBody locationBody) {
        Location location = new Location(locationBody);
        String[] adhanTimes = getAdhanByLocationService.getAdhanByLocation(location);

        Map<String, Object> response = new HashMap<>();
        response.put("adhanTimes", adhanTimes);

        return ResponseEntity.ok(response);
    }

}
