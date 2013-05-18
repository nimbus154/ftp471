# Simple FTP Protocol Design
This document outlines the design of the simple FTP protocol implemented in this
repository.

## Channels
The protocol is divided into two channels:
 1. Control
 1. Data

The **control channel** exists to send requests and responses for data transfer.
Data transfer happens on the **data channel**.

### Control Channel Commands
The control channel must support the following four commands:

 * **get** download files
 * **put** upload files
 * **ls** list the files currently on the server
 * **quit** close the connection

`get`, `put`, and `ls` are considered data transfer operations. See the eponymous
section for details.

#### Control Channel Message Format

##### Request
4 byte message type
4 byte size, transferred in ASCII (thus, largest argument size is 9999 bytes)
0-9999 bytes of data

Each message type has predetermined format for the data portion of the message.

##### Response
A ten-byte string describing the action the server will take in response to the
request.

##### `quit` Message
`quit` is the simplest message. It consists of only a the message type. No data
is transferred with it.
Message type: quit
Size: [this field is ignored]
Data: [this field is ignored]

###### Client Request
```
quit
```

###### Server Response
```
closed
```

##### `get` Message
Message type: get
Size: size in bytes of arguments that follow
Data: [name of file to retrieve] [ephermal port to use for file transfer, in
ASCII]

Data fields are delimited by spaces.

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
get secret_knowledge.txt 1234
```

###### Server Response
```
connecting
```

If no file with that name exists, the server reponds with a `not found` message.

##### `put` Message
Message type: put
Size: size in bytes of arguments that follow
Data: [name of file to upload] [ephermal port to use for file transfer, in ASCII]

Data fields are delimited by spaces.

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
put secret_knowledge.txt 1234
```

###### Server Response
```
connecting
```

If a file with that name already exists on the server, the file will be
overwritten.

##### `ls` Message
Message type: ls
Size: size in bytes of arguments that follow
Data: [ephermal port to use for file transfer, in ASCII]

Data fields are delimited by spaces.

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
put secret_knowledge.txt 1234
```

###### Server Response
```
connecting
```

If a file with that name already exists on the server, the file will be
overwritten.

##### `ls` Message
Message type: ls
Size: [ignored by server]
Data: [e

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
put secret_knowledge.txt 1234
```

###### Server Response
```
connecting
```
### Data Channel
The data channel is where fiels are actually transferred. At this time, I'm not
exactly sure how this will work. Since it uses sendfile, I don't think this will
be as involved.

I must handle: 
 * throttling sender
 * errors in file transfer

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
put secret_knowledge.txt 1234
```

###### Server Response
```
connecting
```
### Data Channel
The data channel is where fiels are actually transferred. At this time, I'm not
exactly sure how this will work. Since it uses sendfile, I don't think this will
be as involved.

I must handle: 
 * throttling sender
 * errors in file transfer
