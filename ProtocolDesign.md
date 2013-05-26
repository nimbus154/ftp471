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
Format
```
get\n
[file to download]\n
[ephemeral port]\n
```
Example: 
```
get\n
secret_knowledge.txt\n
1234\n
```

###### Server Response
```
[file size in bytes]\n
connecting\n
```
Example: 
```
10000\n
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

##### `put` Message
`put` uploads a file to the server.  If a file with that name already exists on
the server, the file will be overwritten.

The server responds by attempting to connect to the client on the ephemeral
port.

###### Client Request
Format:
```
put\n
[file to upload]\n
[size of file in bytes]\n
[ephemeral port]\n
```
Example:
```
put\n
secret_knowledge.txt\n
256\n
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
Format:
```
ls\n
[ephemeral port]\n
```

Example:
```
ls\n
1234\n
```

###### Server Response

```
[length of ls response in bytes]\n
connecting\n
```

Example:
```
24\n
connecting\n
```

If insufficient arugments:
```
insufficient arguments\n
```

If there's an error with the server's interal state:
```
internal error\n
```

### Data Channel
The data channel is where files are actually transferred. The channel is very
simple: it only sends or receives raw data. The control channel tells the data
channel whether it should send or receive. The control channel also tells
the data channel how much data it should receive. It is the data channel's
responsibility to ensure all the data arrives.

The data channel has two operations: upload and download. It can transfer files
or strings.

TCP buffer overflows are managed by passing the expected size of data to the data
channel.

String data is transferred as UTF-8. Files are transferred as raw bytes.
