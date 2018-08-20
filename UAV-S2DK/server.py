#Authors: Jesimar da Silva Arantes and Andre Missaglia
#Date: 01/06/2017
#Last Update: 15/03/2018
#Description: Code that initializes the HTTP server that responds to GET and POST requests.
#Descricao: Codigo que inicia o servidor HTTP que responde a requisicoes GET e POST.

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

def runServer(con, host, port):
    global vehicle
    vehicle = con
    print "Server Starts - %s:%s" % (host, port)
    print time.asctime()
    httpd = BaseHTTPServer.HTTPServer((host, port), Handler)
    print "serving at port", port
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    print "Server Stops - %s:%s" % (host, port)
    print time.asctime()
