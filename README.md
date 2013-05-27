policyextractor
=====================

Examines jar files for use of methods which require a Permission e.g. calls to the constructor: 
    java.net.Socket(...)
require the permission:
    java.net.SocketPermission "{host}:{port}", "connect"

This is very much alpha code.

## Future Extensions 
A custom annotation could be useful in extending this project. Annotations could flag those permissions which are required, but cannot be determined at compile time and also those permissions which are not required.  