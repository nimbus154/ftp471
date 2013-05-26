# ftp471

A bare-bones FTP server. Final class project for CPSC471: Computer Networking.

## Dependencies
You will need to install these two programs in order to build and run ftp471:
 * JDK 7
 * [Maven](http://maven.apache.org/)

## Build
From the project root directory, run:
```bash
mvn clean package
```
This will build and package all of the source files.

## Run
Both the client and server use bash wrappers, so you don't have to invoke them
with `java -jar <jar name>`.

### Server
The server bash wrapper is located in ftp-server.
```bash
./serv <port to listen on>
```

Ex:
```bash
./serv 2020 # listen on port 2020
```

### Client
The server bash wrapper is located in ftp-client.
```bash
./cli <host> <port>
```

Ex:
```bash
./cli google.com 2020 # connect to google.com on port 2020
```

## Commands
After connecting to the server, the client will drop into an interactive shell.

* `ls` list files on the server
* `get <filename>` download a file
* `put <filename>` upload a file
* `quit` disconnect from the server

Please be aware that if you `get` a file with the same name as a local file, you
will overwrite your local file. Similarly, if you `put` a file with the same
name as another file on the server, you will overwite the remote file.
