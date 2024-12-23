# Adhan API Documentation

Welcome to the **Adhan API**, a simple service to retrieve Islamic prayer times based on geographic location.

## Base URL
```https://adhan-apis.onrender.com/api```

## Endpoint
`GET /getAdhanByLocation`
Retrieves the prayer times for a specific location.

### Request Body Parameters
* **latitude** (`float`): Latitude of the location.
* **longitude** (`float`): Longitude of the location.
* **elevation** (`float`): Elevation in meters.
* **timeZone** (`int`): Time zone offset from UTC.
* **asrFactor** (`int`): Factor for calculating Asr prayer time (usually 1 or 2).

### Example Request Body
```
{
    "latitude": 26.4392, 
    "longitude": 50.0944,
    "elevation": 6,
    "timeZone": 3,
    "asrFactor": 1
}
```

## Response
The API will return a JSON object containing the prayer times.

### Example Response
```
{
    "adhanTimes": [
        "04:26 AM",
        "05:34 AM",
        "11:27 AM",
        "02:51 PM",
        "05:32 PM",
        "06:20 PM"
    ]
}
```


