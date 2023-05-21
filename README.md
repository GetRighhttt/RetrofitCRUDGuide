# RetrofitCRUDGuide
#### A library which allows us to easily embed networkin in our application.
#### A type-safe HTTP client for Android.
#### Make it simple to convert RESTFUL HTTP methods into methods you can execute in your application.

### Internet Protocol Suite
- Set of communication protocols that specifies how data should be packaged, addresed, roouted, and received.
- Called TCP/IP model
- Examples: TCP, IP, DNS, DHCP, SSH, FTP, HTTP, HTTPS

### HTTP - Hypertext Transfer Protocol(HTTP) Secure(HTTPS)
- Define a requeset/response protocol
- Define how to communicate data between client and the server

### HTTP Methods
- Formats for procedures that a client request a web service to execute
- Client Request consist of 3 parts: <verb><address><protocol>
- Also adds in headers[metadata about the request] and a request body
  Server response: <protocol><response code>
- Safe: doesn't alter state of server
- Idempotent: identical request can be made repeatedly with same effect
  #### Method Types(Most common)
  - GET - reading data from server [safe and idempotent]
  - POST - creating data on a server
  - PUT - updating data on a server
  - PATCH - updating a particular part of the data on a server
  - DELETE - delete from server
  - HEAD - fetches data but will not return response body [safe and idempotent]

### Representational State Transfer(REST)
- A set of rules or constraints when creating a webs service that use HTTP methods
- Web services that conform to these protocols are said to be RESTFUL
- Most APIs that we use in Android are said to be RESTFUL
