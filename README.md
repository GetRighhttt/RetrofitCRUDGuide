# RetrofitCRUDGuide

My Android networking notes and Retrofit example. The app retrieves posts and user details from JSONPlaceholder. The notes cover converters, concurrency, REST, and HTTP methods.

Retrofit is a type-safe HTTP client for Android and the JVM. An annotated interface defines HTTP requests as methods the app can call.

## Data converters

Converters translate request and response bodies into application types. Retrofit supports converters for formats and libraries such as:

- Moshi
- Gson
- Jackson
- Protobuf
- Wire
- Simple XML

## Concurrency

Concurrency lets multiple tasks make progress during overlapping periods. Parallelism runs tasks at the same time on different cores.

Asynchronous work lets the caller continue before a result is ready. In Android, move blocking I/O and expensive computation off the main thread so the UI can respond to input and draw frames. Update Views on the main thread.

## Coroutines

Kotlin coroutines support asynchronous work through suspension. A suspended coroutine releases its thread until it can resume. A coroutine is not a thread, and launching one does not automatically move work to a background thread. Its dispatcher determines where it runs.

Use a lifecycle-owned scope for Android work. Retrofit's suspending API calls can wait for a network result without blocking the main thread. Blocking I/O and CPU-heavy work still need an appropriate dispatcher.

<br>

## Networking overview

### Internet protocol suite

The internet protocol suite, often called TCP/IP, defines how devices address, transmit, route, and receive data. It includes protocols such as IP, TCP, UDP, DNS, and HTTP. These protocols operate at different layers.

### Representational state transfer (REST)

REST is an architectural style with constraints such as client-server separation, stateless requests, and a uniform interface. HTTP APIs often model resources through URLs and operate on them with HTTP methods. Using HTTP alone does not make an API RESTful.

### HTTP and HTTPS

HTTP defines requests and responses between clients and servers. HTTPS protects HTTP traffic with TLS.

### HTTP methods

An HTTP/1.1 request line contains a method, request target, and protocol version. Headers supply metadata, and some requests include a body. A response has a status line, headers, and an optional body.

A safe method does not request a change to server state. An idempotent method has the same intended effect when repeated as it does when called once; responses can still differ.

#### Common methods

- GET retrieves a resource. It is safe and idempotent.
- POST submits data for processing, often to create a resource. It is not inherently idempotent.
- PUT creates or replaces the resource at a known URL. It is idempotent.
- PATCH applies a partial update. It is not inherently idempotent.
- DELETE removes the resource at a URL. It is idempotent.
- HEAD requests response headers without a response body. It is safe and idempotent.
