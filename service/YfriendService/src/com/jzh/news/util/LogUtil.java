package com.jzh.news.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * JDK自带的Log的工具类（手写，勿喷。。）
 * 
 * @author DarkRanger
 * @date 20160615
 *
 */
public class LogUtil {
    
    // 正常的日期格式
    public static final String DATE_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
    // 不带符号的日期格式，用来记录时间戳
    public static final String DATE_PATTERN_NOMARK = "yyyyMMddHHmmss";

    /**
     * 为log设置等级
     * 
     * @param log
     * @param level
     */
    public static void setLogLevel(Logger log, Level level) {
        log.setLevel(level);
    }

    /**
     * 为log添加控制台handler
     * 
     * @param log
     *            要添加handler的log
     * @param level
     *            控制台的输出等级
     */
    public static void addConsoleHandler(Logger log, Level level) {
        // 控制台输出的handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        // 设置控制台输出的等级（如果ConsoleHandler的等级高于或者等于log的level，则按照FileHandler的level输出到控制台，如果低于，则按照Log等级输出）
        consoleHandler.setLevel(level);

        // 添加控制台的handler
        log.addHandler(consoleHandler);
    }

    /**
     * 为log添加文件输出Handler
     * 
     * @param log
     *            要添加文件输出handler的log
     * @param level
     *            log输出等级
     * @param filePath
     *            指定文件全路径
     */
    public static void addFileHandler(Logger log, Level level, String filePath) {
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(filePath);
            // 设置输出文件的等级（如果FileHandler的等级高于或者等于log的level，则按照FileHandler的level输出到文件，如果低于，则按照Log等级输出）
            fileHandler.setLevel(level);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {

                    // 设置文件输出格式
                    return "[ " + getCurrentDateStr(DATE_PATTERN_FULL) + " - Level:"
                            + record.getLevel().getName().substring(0, 1) + " ]-" + "[" + record.getSourceClassName()
                            + " -> " + record.getSourceMethodName() + "()] " + record.getMessage() + "\n";
                }
            });

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 添加输出文件handler
        log.addHandler(fileHandler);
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getCurrentDateStr(String pattern) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}