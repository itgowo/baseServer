package com.itgowo.baseserver;

import com.itgowo.baseserver.base.BaseConfig;
import com.itgowo.baseserver.base.HttpServerInitCallback;
import com.itgowo.servercore.http.HttpServerManager;
import com.itgowo.servercore.utils.LogU;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerManager {
    private static ExecutorService executorService = new ThreadPoolExecutor(1, 200, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(0);
    private static HttpServerManager httpServerManager = new HttpServerManager();
    private static Logger log;

    public static void setLogger(Logger log) {
        ServerManager.log = log;
    }

    public static Logger getLogger() {
        if (log == null) {
            synchronized (ServerManager.class) {
                log = LogU.getLogU("com.itgowo.server.WebSocketServerManager", Level.ALL);
            }
        }
        return log;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    private static HttpServerInitCallback httpServerInitCallback = null;
    private static onErrorListener onErrorListener = null;

    public static void setOnErrorListener(ServerManager.onErrorListener onErrorListener) {
        ServerManager.onErrorListener = onErrorListener;
    }

    public static void onError(Exception e) {
        if (onErrorListener != null) {
            onErrorListener.onError(e);
        }
    }

    public static HttpServerManager getHttpServerManager() {
        return httpServerManager;
    }

    public static void stop() {
        if (httpServerManager != null) httpServerManager.stop();
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    /**
     * 使用界面控制服务，查找指定的启动服务文件
     *
     * @return
     */
    public static boolean searchMainClass() {
        if (httpServerInitCallback != null) {
            return true;
        }
        try {
            String mainClass = BaseConfig.getServerMainClass();
            if (mainClass != null && mainClass.trim().length() != 0) {
                Class c = Class.forName(mainClass);
                httpServerInitCallback = (HttpServerInitCallback) c.newInstance();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 使用界面控制服务，初始化后台服务框架
     */
    public static boolean initServer() {
        if (httpServerInitCallback == null) {
            return false;
        } else {
            try {
                httpServerInitCallback.onServerConfigPrepare(ServerManager.getHttpServerManager());
            } catch (Exception e) {
                httpServerInitCallback.onError(e);
            }
        }
        int portint = BaseConfig.getServerPort();
        Thread mGameThread = new Thread(() -> {
            try {
                Thread.currentThread().setName("ServerMainThread");
                httpServerManager.start(portint);
            } catch (Exception mEm) {
                httpServerInitCallback.onError(mEm);
            }
        });
        mGameThread.start();
        return true;
    }

    public interface onErrorListener {
        void onError(Exception e);
    }
}