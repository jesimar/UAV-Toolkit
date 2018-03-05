import BaseHTTPServer
import json
import traceback
import time
from requisitions import GET_URLS, POST_URLS

vehicle = None

class Handler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_GET(self):
        global vehicle
        if self.path not in GET_URLS:
            self.send_error(404, "File not found")
            return
        request = {
            'vehicle': vehicle
        }
        try:
            response = GET_URLS[self.path](request)
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            self.wfile.write(json.dumps(response))
        except:
            response = {}
            self.send_response(500)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            self.wfile.write(json.dumps(response))
            traceback.print_exc()

    def do_POST(self):
        global vehicle
        if self.path not in POST_URLS:
            self.send_error(404, "File not found")
            return
        data = self.rfile.read(int(self.headers['Content-Length']))
        request = {
            'body': json.loads(data),
            'vehicle': vehicle
        }
        try:
            response = POST_URLS[self.path](request)
            self.send_response(200)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            self.wfile.write(json.dumps(response))
        except:
            response = {}
            self.send_response(500)
            self.send_header("Content-type", "application/json")
            self.end_headers()
            self.wfile.write(json.dumps(response))
            traceback.print_exc()

def runServer(con):
    global vehicle
    vehicle = con
    PORT = 50000
    HOST = "Localhost"
    print "Server Starts - %s:%s" % (HOST, PORT)
    print time.asctime()
    httpd = BaseHTTPServer.HTTPServer(("", PORT), Handler)
    print "serving at port", PORT
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    print "Server Stops - %s:%s" % (HOST, PORT)
    print time.asctime()
