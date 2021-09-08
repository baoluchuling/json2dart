package template;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class FileUtil {

    public static void createFileWithClass(String filepath, String filename, String classtext) {
        Application applicationManager = ApplicationManager.getApplication();
        applicationManager.runWriteAction((Computable<Object>) () -> {
                    VirtualFile vfs = LocalFileSystem.getInstance().findFileByPath(filepath);

                    assert vfs != null;

                    String fileName = FileUtil.getFileName(filename);
                    VirtualFile resourceFileDir = null;

                    try {
                        resourceFileDir = vfs.findOrCreateChildData(null, fileName);
                    } catch (IOException var10) {
                        var10.printStackTrace();
                    }

                    assert resourceFileDir != null;

                    try {
                        resourceFileDir.setBinaryContent(classtext.getBytes());
                    } catch (IOException var9) {
                        var9.printStackTrace();
                    }
                    return null;
                }
        );
    }

    public static String getFileExtension(DataContext dataContext) {
        VirtualFile srcFile = LangDataKeys.VIRTUAL_FILE.getData(dataContext);
        //判断目录下是有assert文件夹
        //没有就创建一个
        assert srcFile != null;

        String dicPath = srcFile.getPath();
        String extension = srcFile.getExtension();

        if (extension != null) {
            String[] pathNames = dicPath.split("/");
            int count = pathNames.length;

            ArrayList<String> tmp = new ArrayList<>(Arrays.asList(pathNames));
            tmp.remove(count - 1);

            StringBuilder res = new StringBuilder();
            for (String path : tmp) {
                res.append(path).append("/");
            }

            dicPath = res.toString();
        }

        return dicPath;
    }

    public static String getFileName(String className) {
        ArrayList<String> rs = new ArrayList<>();

        int index = 0;
        int len = className.length();
        for (int i = 1; i < len; i++) {
            if (Character.isUpperCase(className.charAt(i))) {
                rs.add(className.substring(index, i));
                index = i;
            }
        }
        rs.add(className.substring(index, len));

        StringBuilder res = new StringBuilder();
        for (String path : rs) {
            res.append(path.toLowerCase(Locale.ROOT)).append("_");
        }

        return res.substring(0, res.length() - 1) + ".dart";
    }
}
