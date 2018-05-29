OAuth2 with Discord demo 
========================

First, you will need to register an application with discord to obtain  a client-ID and a secret.

You can register a new app here: https://discordapp.com/developers/applications/me

Discord requires a whitelisted URL to redirect to upon succesful authentication. For now, http://localhost:8080/login/oauth2/code/discord should be fine. Add an optional description and icon to your liking. When created, note the Client-ID and Client Secret.

You can either specify these values in `src/main/resources/application.yml` or you can pass them as environment variables (`SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_DISCORD_CLIENT_ID` and `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_DISCORD_CLIENT_SECRET`)

Run using the `bootRun` task. Note you can run `assemble` in a separate process (using the `--continuous` flag) for hot deployment.
