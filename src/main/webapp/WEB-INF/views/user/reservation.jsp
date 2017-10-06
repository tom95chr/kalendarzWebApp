<%--
  Created by IntelliJ IDEA.
  User: Lapek
  Date: 06.10.2017
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservation</title>
</head>
<body>
<h1>Host</h1>
<h2>${therapist.firstName} ${therapist.lastName} <br>
    Specialization: ${therapist.specialization}
    Description: ${therapist.description}
</h2>
<h1>Event details</h1>
<h2>
    name: ${event.name} <br>
    start: ${event.startDateTime}<br>
    end: ${event.endDateTime}<br>
    room: ${event.room}<br>
    free places: ${event.eventType.seats}<br>
</h2>
<h1>If you want to join this event please submit your email and confirm your reservation</h1>


</body>
</html>
