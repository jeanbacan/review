/*
var connect = require('connect');
connect.createServer(
    connect.static(__dirname+"/build")
).listen(7000);*/
var http = require("http"),
    url = require("url"),
    path = require("path"),
    fs = require("fs")
    port = process.argv[2] || 7000;

http.createServer(function(request, response) {

    var uri = url.parse(request.url).pathname;
    var root = path.join(process.cwd(),'build');
    var filename = path.join(root, uri);


    fs.exists(filename, function(exists) {
        if(!exists) {
            if(request.url.indexOf('/&w=')>-1 || request.url.indexOf('.png')>-1){
                response.writeHead(404, {"Content-Type": "text/plain"});
                response.write("404 Not Found\n");
                response.end();
            }else{
                console.log('redirect: '+('/#'+request.url));
                response.writeHead(302, {
                    'Location': ('/#'+request.url)
                    //add other headers here...
                });
                response.end();
            }


            return;
        }

        if (fs.statSync(filename).isDirectory()) filename += '/index.html';

        fs.readFile(filename, "binary", function(err, file) {
            if(err) {
                response.writeHead(500, {"Content-Type": "text/plain"});
                response.write(err + "\n");
                response.end();
                return;
            }

            if (fs.statSync(filename).isFile() && filename.indexOf('.css')>-1){
                response.writeHead(200, {"Content-Type": "text/css"});
            } else if (fs.statSync(filename).isFile() && filename.indexOf('.js')>-1){
                response.writeHead(200, {"Content-Type": "text/javascript"});
            } else {
            	response.writeHead(200);
            }

            response.write(file, "binary");
            response.end();
        });
    });
}).listen(parseInt(port, 10));

console.log("Static file server running at\n  => http://localhost:" + port + "/\nCTRL + C to shutdown");
var exec = require('child_process').exec;
exec('start http://localhost:'+port, function (error, stdout, stderr) {});