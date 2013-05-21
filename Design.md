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
```
command\n
arguments\n
```

Each message type has predetermined format for the data portion of the message.

##### Response
A short response explaining the action taken by the server, ending in \n.

##### `quit` Message
`quit` is the simplest message. It consists of only a the message type. No data
is transferred with it.

###### Client Request
```
quit\n
```

###### Server Response
none

##### `get` Message
`get` requests to retrieve a file from the server.

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
get\n
secret_knowledge.txt\n
1234\n
```

###### Server Response
```
connecting\n
```

If insufficient arugments:
```
insufficient arguments\n
```

If file not found:
```
not found\n
```

If no file with that name exists, the server reponds with a `not found` message.

##### `put` Message
`put` uploads a file to the server.  If a file with that name already exists on
the server, the file will be overwritten.

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
```
put\n
secret_knowledge.txt\n
1234\n
```

###### Server Response
```
connecting\n
```

If insufficient arugments:
```
insufficient arguments\n
```

If file not found:
```
not found\n
```

##### `ls` Message
`ls` lists all files in the server-side directory.

###### Client Request
```
ls
```

###### Server Response
```
file1\n
file2\n
file3\n
```

### Data Channel
The data channel is where files are actually transferred. At this time, I'm not
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
