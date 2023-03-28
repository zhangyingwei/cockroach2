package com.zhangyingwei.cockroach2.monitor.http.server;

import com.zhangyingwei.cockroach2.common.utils.IdUtils;
import com.zhangyingwei.cockroach2.common.utils.LogUtils;
import com.zhangyingwei.cockroach2.monitor.http.server.action.ICAction;
import com.zhangyingwei.cockroach2.monitor.http.server.exception.MethodNotMatchException;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Request;
import com.zhangyingwei.cockroach2.monitor.http.server.session.Response;
import com.zhangyingwei.cockroach2.monitor.http.server.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CockroachHttpServer {
    private Thread httpThread;
    private String staticBasePath = "/static";
    private String templateBasePath = "/templates";
    private Map<String, ICAction> actionMap = new ConcurrentHashMap<String, ICAction>();

    public CockroachHttpServer() {
        this.httpThread = new Thread(new HttpWorker());
        this.httpThread.setDaemon(true);
        this.httpThread.setName("cuckroach-http-"+IdUtils.getId(CockroachHttpServer.class.getName()));
//        System.out.println(resourceBasePath);
    }

    public void start() {
        this.httpThread.start();
    }

    public void registeAction(String route,ICAction action) {
        this.actionMap.put(route, action);
    }

    /**
     * 请求处理总线
     */
    class HttpWorker implements Runnable {
        private int defaultPort = 8000;
        private ServerSocket socket;

        public HttpWorker() {
            try {
                this.socket = new ServerSocket(this.getPort());
                log.info("{}: http server is running with port: {}", LogUtils.getExecutorTagColor("monitor"), LogUtils.getTagColor(this.defaultPort));
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        private int getPort() {
            if (this.isportUsed()) {
                this.defaultPort++;
                return getPort();
            }
            return this.defaultPort;
        }

        private boolean isportUsed() {
            try {
                new Socket("127.0.0.1", this.defaultPort);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public void run() {
            ExecutorService workers = Executors.newFixedThreadPool(10, (runnable) -> {
                Thread thread = new Thread(runnable);
                thread.setName("request-worker-"+ IdUtils.getId(RequestWorker.class.getName()));
                thread.setDaemon(true);
                return thread;
            });
            while (true) {
                try {
                    Socket accept = this.socket.accept();
                    workers.submit(new RequestWorker(accept));
                } catch (IOException e) {
                    log.error(e.getLocalizedMessage());
                }
            }
        }

        /**
         * 处理每一个请求
         */
        class RequestWorker implements Runnable {
            private final Socket currentSocket;

            public RequestWorker(Socket socket) {
                this.currentSocket = socket;
            }

            @Override
            public void run() {
                try {
                    Request request = new Request(this.currentSocket);
                    log.debug("{} : {}", request.getMethod(), request.getPath());
                    Response response = new Response(this.currentSocket, request);
                    this.doResponse(request,response);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MethodNotMatchException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        this.currentSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void doResponse(Request request, Response response) {
                String path = request.getPath();
                URL staticPath = this.getResourcesUrl(staticBasePath.concat(path));
                ICAction action = actionMap.get(path);
                try {
                    if (action != null) {
                        response.resourcesOk();
                        String result = action.doAction(request, response);
                        URL htmlPath = this.getResourcesUrl(templateBasePath.concat("/").concat(result));
                        if (response.getHeader().getContentType().contains("text/html")) {
                            response.getWriter().println(FileUtils.getContent(CockroachHttpServer.class, htmlPath));
                        } else if (response.getHeader().getContentType().contains("application/json")) {
                            response.getWriter().println(result);
                        } else {
                            log.error(response.getHeader().getContentType());
                        }
                    } else {
                        if (FileUtils.exits(CockroachHttpServer.class,staticPath)) {
                            response.resourcesOk().getWriter().println(FileUtils.getContent(CockroachHttpServer.class,staticPath));
                        } else {
                            response.resourcesNotFound();
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    response.getWriter().flush();
                }
            }

            private URL getResourcesUrl(String concat) {
                return CockroachHttpServer.class.getResource(concat);
            }
        }

    }

}
