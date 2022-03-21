package comp30910.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileIO {
    public static String readFileAsString(String filePath) throws IOException {
        URL url = Resources.getResource(filePath);
        return Resources.toString(url, Charsets.UTF_8);
    }

    public static void writeToFile(String filePath, String content, boolean append)
            throws IOException {
        File file = new File(filePath);
        CharSink sink;
        if (!append) {
            Files.createParentDirs(file);
            Files.touch(file);
            sink = Files.asCharSink(file, Charsets.UTF_8);
        } else {
            sink = Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND);
        }
        sink.write(content);
    }
}
