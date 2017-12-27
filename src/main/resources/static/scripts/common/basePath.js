function getRoot() {
    var hostname = location.hostname;
    var pathname = location.pathname;
    var port = location.port;
    var protocol = location.protocol;
    return protocol + "//" + hostname + ":" + port;
}

var basePath = getRoot();