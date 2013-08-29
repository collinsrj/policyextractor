policyextractor
=====================

Examines jar files for use of methods which require a Permission e.g. calls to the constructor: 
    java.net.Socket(...)
require the permission:
    java.net.SocketPermission "{host}:{port}", "connect"