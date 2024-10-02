# ktor-concurrent-viewers

Testing how many concurrent viewers ktor can handler

## Notes

- Capable of handling a lot of users
- Each user adds 1MB to the total process memory. Couldn't figure out if it's due to the websocket connection or netty connection