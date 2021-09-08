package template;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

public class TranslateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project != null) {

            String filePath = FileUtil.getFileExtension(dataContext);

            SettingUtil.fetchSetting();

            edit edit = new edit(filePath);
            edit.setSize(600, 400);
            edit.setLocationRelativeTo(null);
            edit.setVisible(true);
        }
    }
}
