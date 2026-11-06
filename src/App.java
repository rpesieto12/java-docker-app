import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Main Application Context
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "{\"status\": \"success\", \"message\": \"Hello from Java inside Docker!!!\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                byte[] responseBytes = response.getBytes();
                exchange.sendResponseHeaders(200, responseBytes.length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            }
        });

        // Dedicated Health Check Endpoint
        server.createContext("/health", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "{\"status\": \"UP\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                byte[] responseBytes = response.getBytes();
                exchange.sendResponseHeaders(200, responseBytes.length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }
            }
        });

        server.setExecutor(null); // Default executor
        System.out.println("Server started on port " + port);
        server.start();
    }
}