package com.sedam.eui.action;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.sedam.eui.exception.EuiNotFoundException;
import com.sedam.eui.registry.EuiRegistryManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RefreshEuiRegistryAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project != null) {
            try {
                EuiRegistryManager.rebuild(project);
                NotificationGroupManager.getInstance().getNotificationGroup("EUI Plugin")
                        .createNotification("EUI registry has been refreshed", NotificationType.INFORMATION)
                        .notify(project);
            } catch (IOException ex) {
                NotificationGroupManager.getInstance().getNotificationGroup("EUI Plugin")
                        .createNotification("Failed to read EUI documentation", "", NotificationType.ERROR)
                        .notify(project);
            } catch (EuiNotFoundException ex) {
                NotificationGroupManager.getInstance().getNotificationGroup("EUI Plugin")
                        .createNotification("EUI is not used in this project.", "", NotificationType.ERROR)
                        .notify(project);
            }
        }
    }
}
