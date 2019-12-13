# Replay events demo

## Starting the application

`./gradlew bootRun` starts the application and 1,000 test orders will be placed and dispatched.

## Replaying

To trigger a replay, `POST` to `/replay`: `curl -XPOST http://localhost:8080/replay`. It will return `Replay started...` if succesful.

To track current replay progression, `GET /replay`: `curl http://localhost:8080/replay` It will return the current progression (for example `Replay at 109/2457 (4% complete)`) or indicate if no replay is busy: `No replay active`.
