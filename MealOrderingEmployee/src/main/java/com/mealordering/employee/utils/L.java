package com.mealordering.employee.utils;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A more natural android logging facility. WARNING: CHECK OUT COMMON PITFALLS
 * BELOW Unlike {@link android.util.Log}, Log provides sensible defaults. Debug
 * and Verbose logging is enabled for applications that have
 * "android:debuggable=true" in their AndroidManifest.xml. For apps built using
 * SDK Tools r8 or later, this means any debug build. Release builds built with
 * r8 or later will have verbose and debug log messages turned off. The default
 * tag is automatically set to your app's packagename, and the current context
 * (eg. activity, service, application, etc) is appended as well. You can add an
 * additional parameter to the tag using {@link #Log(String)}. Log-levels can be
 * programatically overridden for specific instances using
 * {@link #Log(String, boolean, boolean)}. Log messages may optionally use
 * {@link String#format(String, Object...)} formatting, which will not be
 * evaluated unless the log statement is output. Additional parameters to the
 * logging statement are treated as varrgs parameters to
 * {@link String#format(String, Object...)} Also, the current file and line is
 * automatically appended to the tag (this is only done if debug is enabled for
 * performance reasons). COMMON PITFALLS: * Make sure you put the exception
 * FIRST in the call. A common mistake is to place it last as is the
 * android.util.Log convention, but then it will get treated as varargs
 * parameter. * vararg parameters are not appended to the log message! You must
 * insert them into the log message using %s or another similar format parameter
 * Usage Examples: Ln.v("hello there"); Ln.d("%s %s", "hello", "there"); Ln.e(
 * exception, "Error during some operation"); Ln.w( exception,
 * "Error during %s operation", "some other");
 */
public final class L {
    /**
     * config is initially set to BaseConfig() with sensible defaults, then
     * replaced by BaseConfig(ContextSingleton) during guice static injection
     * pass.
     */
    private static final BaseConfig CONFIG = new BaseConfig();
    private static final String LOG_FILE_NAME = "CatClaw.log";
    /**
     * print is initially set to Print(), then replaced by guice during static
     * injection pass. This allows overriding where the log message is delivered
     * to.
     */
    private static Print print = new Print();

    private L() {
    }

    public static int v(Throwable t) {
        return CONFIG.minimumLogLevel <= Log.VERBOSE ? print.println(
                Log.VERBOSE, Log.getStackTraceString(t)) : 0;
    }

    public static int v(Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.VERBOSE) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.VERBOSE, message);
    }

    public static int v(Throwable throwable, Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.VERBOSE) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s)
                + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.VERBOSE, message);
    }

    public static int d(Throwable t) {
        return CONFIG.minimumLogLevel <= Log.DEBUG ? print.println(Log.DEBUG,
                Log.getStackTraceString(t)) : 0;
    }

    public static int d(Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.DEBUG) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.DEBUG, message);
    }

    public static int d(Throwable throwable, Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.DEBUG) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s)
                + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.DEBUG, message);
    }

    public static int i(Throwable t) {
        return CONFIG.minimumLogLevel <= Log.INFO ? print.println(Log.INFO,
                Log.getStackTraceString(t)) : 0;
    }

    public static int i(Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.INFO) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.INFO, message);
    }

    public static int i(Throwable throwable, Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.INFO) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s)
                + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.INFO, message);
    }

    public static int w(Throwable t) {
        return CONFIG.minimumLogLevel <= Log.WARN ? print.println(Log.WARN,
                Log.getStackTraceString(t)) : 0;
    }

    public static int w(Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.WARN) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.WARN, message);
    }

    public static int w(Throwable throwable, Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.WARN) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s)
                + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.WARN, message);
    }

    public static int e(Throwable t) {
        return CONFIG.minimumLogLevel <= Log.ERROR ? print.println(Log.ERROR,
                Log.getStackTraceString(t)) : 0;
    }

    public static int e(Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.ERROR) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = args.length > 0 ? String.format(s, args) : s;
        return print.println(Log.ERROR, message);
    }

    public static int e(Throwable throwable, Object s1, Object... args) {
        if (CONFIG.minimumLogLevel > Log.ERROR) {
            return 0;
        }

        final String s = Strings.toString(s1);
        final String message = (args.length > 0 ? String.format(s, args) : s)
                + '\n' + Log.getStackTraceString(throwable);
        return print.println(Log.ERROR, message);
    }

    public static boolean isDebugEnabled() {
        return CONFIG.minimumLogLevel <= Log.DEBUG;
    }

    public static boolean isVerboseEnabled() {
        return CONFIG.minimumLogLevel <= Log.VERBOSE;
    }

    public static Config getConfig() {

        return CONFIG;
    }

    public static String logLevelToString(int loglevel) {
        switch (loglevel) {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";

            default:
                return "UNKNOWN";
        }

    }

    public static void setPrint(Print print) {
        L.print = print;
    }

    public interface Config {
        int getLoggingLevel();

        void setLoggingLevel(int level);
    }

    public static class BaseConfig implements Config {
        protected int minimumLogLevel = Log.VERBOSE;
        protected String packageName = "";
        protected String scope = "";
        protected boolean writeLog = true;

        protected BaseConfig() {
        }

        public BaseConfig(Application context) {
            try {
                packageName = context.getPackageName();
                final int flags = context.getPackageManager()
                        .getApplicationInfo(packageName, 0).flags;
                minimumLogLevel = (flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 ? Log.VERBOSE
                        : Log.INFO;
                scope = packageName.toUpperCase();
                L.d("Configuring Logging, minimum log level is %s",
                        logLevelToString(minimumLogLevel));
            } catch (Exception e) {
                Log.e(packageName, "Error configuring logger", e);
            }
        }

        @Override
        public int getLoggingLevel() {
            return minimumLogLevel;
        }

        @Override
        public void setLoggingLevel(int level) {
            minimumLogLevel = level;
        }
    }

    /**
     * Default implementation logs to android.util.Log
     */
    public static class Print {
        private static final int DEFAULT_STACK_TRACE_LINE_COUNT = 5;

        protected static String getScope() {
            if (CONFIG.minimumLogLevel <= Log.DEBUG) {
                final StackTraceElement trace = Thread.currentThread()
                        .getStackTrace()[DEFAULT_STACK_TRACE_LINE_COUNT];
                return CONFIG.scope + "/" + trace.getFileName() + ":"
                        + trace.getLineNumber();
            }

            return CONFIG.scope;
        }

        public int println(int priority, String msg) {
            return Log.println(priority, getScope(), processMessage(msg));
        }

        protected String processMessage(String msg) {
            String detailsMsg = "";
            if (CONFIG.minimumLogLevel <= Log.DEBUG) {
                detailsMsg = String.format("%s %s %s", new SimpleDateFormat(
                                "HH:mm:ss.SSS").format(System.currentTimeMillis()),
                        Thread.currentThread().getName(), msg
                );
            }
            return detailsMsg;
        }

    }

    static class Strings {
        private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

        private Strings() {

        }

        /**
         * Like join, but allows for a distinct final delimiter. For english
         * sentences such as "Alice, Bob and Charlie" use ", " and " and " as the
         * delimiters.
         *
         * @param delimiter     usually ", "
         * @param lastDelimiter usually " and "
         * @param objs          the objects
         * @param <T>           the type
         * @return a string
         */
        public static <T> String joinAnd(final String delimiter, final String lastDelimiter, final Collection<T> objs) {
            if (objs == null || objs.isEmpty()) {
                return "";
            }

            final Iterator<T> iter = objs.iterator();
            final StringBuilder buffer = new StringBuilder(Strings.toString(iter.next()));
            int i = 1;
            while (iter.hasNext()) {
                final T obj = iter.next();
                if (notEmpty(obj)) {
                    buffer.append(++i == objs.size() ? lastDelimiter : delimiter).append(Strings.toString(obj));
                }
            }
            return buffer.toString();
        }

        public static <T> String joinAnd(final String delimiter, final String lastDelimiter, final T... objs) {
            return joinAnd(delimiter, lastDelimiter, Arrays.asList(objs));
        }

        public static <T> String join(final String delimiter, final Collection<T> objs) {
            if (objs == null || objs.isEmpty()) {
                return "";
            }

            final Iterator<T> iter = objs.iterator();
            final StringBuilder buffer = new StringBuilder(Strings.toString(iter.next()));

            while (iter.hasNext()) {
                final T obj = iter.next();
                if (notEmpty(obj)) {
                    buffer.append(delimiter).append(Strings.toString(obj));
                }
            }
            return buffer.toString();
        }

        public static <T> String join(final String delimiter, final T... objects) {
            return join(delimiter, Arrays.asList(objects));
        }

        public static String toString(InputStream input) {
            StringWriter sw = new StringWriter();
            copy(new InputStreamReader(input, Charset.forName("UTF-8")), sw);
            return sw.toString();
        }

        public static String toString(Reader input) {
            StringWriter sw = new StringWriter();
            copy(input, sw);
            return sw.toString();
        }

        public static int copy(Reader input, Writer output) {
            long count = copyLarge(input, output);
            return count > Integer.MAX_VALUE ? -1 : (int) count;
        }

        public static long copyLarge(Reader input, Writer output) {
            try {
                char[] buffer = new char[DEFAULT_BUFFER_SIZE];
                long count = 0;
                int n;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                }
                return count;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static String toString(final Object o) {
            return toString(o, "");
        }

        public static String toString(final Object o, final String def) {
            return o == null ? def : o instanceof InputStream ? toString((InputStream) o) : o instanceof Reader ? toString((Reader) o)
                    : o instanceof Object[] ? Strings.join(", ", (Object[]) o) : o instanceof Collection ? Strings.join(", ", (Collection<?>) o) : o
                    .toString();
        }

        public static boolean isEmpty(final Object o) {
            return toString(o).trim().length() == 0;
        }

        public static boolean notEmpty(final Object o) {
            return toString(o).trim().length() != 0;
        }

        public static String md5(String s) {
            // http://stackoverflow.com/questions/1057041/difference-between-java-and-php5-md5-hash
            // http://code.google.com/p/roboguice/issues/detail?id=89
            try {

                final byte[] hash = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
                final StringBuilder hashString = new StringBuilder();

                for (byte aHash : hash) {
                    String hex = Integer.toHexString(aHash);

                    if (hex.length() == 1) {
                        hashString.append('0');
                        hashString.append(hex.charAt(hex.length() - 1));
                    } else {
                        hashString.append(hex.substring(hex.length() - 2));
                    }
                }

                return hashString.toString();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static String capitalize(String s) {
            final String c = Strings.toString(s);
            return c.length() >= 2 ? c.substring(0, 1).toUpperCase() + c.substring(1) : c.length() >= 1 ? c.toUpperCase() : c;
        }

        public static boolean equals(Object a, Object b) {
            return Strings.toString(a).equals(Strings.toString(b));
        }

        public static boolean equalsIgnoreCase(Object a, Object b) {
            return Strings.toString(a).toLowerCase().equals(Strings.toString(b).toLowerCase());
        }

        public static String[] chunk(String str, int chunkSize) {
            if (isEmpty(str) || chunkSize == 0) {
                return new String[0];
            }

            final int len = str.length();
            final int arrayLen = (len - 1) / chunkSize + 1;
            final String[] array = new String[arrayLen];
            for (int i = 0; i < arrayLen; ++i) {
                array[i] = str.substring(i * chunkSize, i * chunkSize + chunkSize < len ? i * chunkSize + chunkSize : len);
            }

            return array;
        }

        public static String namedFormat(String str, Map<String, String> substitutions) {
            for (Map.Entry<String, String> entry : substitutions.entrySet()) {
                str = str.replace('$' + entry.getKey(), entry.getValue());
            }

            return str;
        }

        public static String namedFormat(String str, Object... nameValuePairs) {
            if (nameValuePairs.length % 2 != 0) {
                throw new InvalidParameterException("You must include one value for each parameter");
            }

            final HashMap<String, String> map = new HashMap<String, String>(nameValuePairs.length / 2);
            for (int i = 0; i < nameValuePairs.length; i += 2) {
                map.put(Strings.toString(nameValuePairs[i]), Strings.toString(nameValuePairs[i + 1]));
            }

            return namedFormat(str, map);
        }

    }
}
