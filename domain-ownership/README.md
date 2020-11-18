# Domain ownership

Start the application with `./gradlew bootRun`

There are two users configured, `alice` and `bob`, both with `secret` as their password.


## Create a meeting

You may create a meeting by `POST`ing the time of the meeting. In this example, Alice plans a meeting:

`curl --user alice:secret -H "Content-Type: application/json" --data '{"dateTime": "2020-11-11T11:11:11"}' http://localhost:8080/meetings`

This returns a `meetingId`: `{"meetingId": YOUR_MEETING_ID}`

## Cancel a meeting

If then Bob tries to cancel it:

`curl -i --user bob:secret -X DELETE http://localhost:8080/meetings/YOUR_MEETING_ID`

He will then receive a `403 Forbidden`, as expected.

Should Alice cancel it, it will succeed.


