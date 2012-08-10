package org.expressme.modules.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for file operation.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class FileUtils {

    private static final int MAX_BUFFER_SIZE = 4096;

    /**
     * Delete a file.
     */
    public static boolean deleteFile(File file) {
        if (! file.isFile())
            return false;
        if (file.delete())
            return true;
        return false;
    }

    public static String[] readAsStringArray(String path) {
        String content = readText(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader(content));
            List<String> list = new ArrayList<String>(20);
            for (;;) {
                String line = reader.readLine();
                if (line==null)
                    break;
                line = line.trim();
                if (line.length()>0)
                    list.add(line.trim());
            }
            return list.toArray(new String[list.size()]);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (reader!=null) {
                try {
                    reader.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * Read file content as text. The file must can be found in classpath.
     * 
     * @param path Path search in classpath.
     * @return File content as String.
     */
    public static String readText(String path) {
        InputStream input = null;
        try {
            input = FileUtils.class.getClassLoader().getResourceAsStream(path);
            if (input==null) {
                throw new IOException("Cannot find '" + path + "'.");
            }
            Reader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder(4096);
            char[] buffer = new char[1024];
            for (;;) {
                int n = reader.read(buffer);
                if (n==(-1))
                    break;
                sb.append(buffer, 0, n);
            }
            return sb.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (input!=null) {
                try {
                    input.close();
                }
                catch(IOException e) {
                }
            }
        }
    }

    /**
     * Read file content as byte array.
     */
    public static byte[] readFile(File file) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream((int)file.length());
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            for (;;) {
                int len = input.read(buffer);
                if (len==(-1))
                    break;
                output.write(buffer, 0, len);
            }
        }
        finally {
            close(input);
        }
        return output.toByteArray();
    }

    public static void readFile(File file, OutputStream output) throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            for (;;) {
                int n = input.read(buffer);
                if (n==(-1))
                    break;
                output.write(buffer, 0, n);
            }
            output.flush();
        }
        finally {
            close(input);
        }
    }

    private static void close(InputStream input) {
        if (input != null) {
            try {
                input.close();
            }
            catch (IOException e) {}
        }
    }

}
