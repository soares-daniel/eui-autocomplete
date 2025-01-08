package com.sedam.eui;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.sedam.eui.exception.EuiNotFoundException;
import com.sedam.eui.registry.EuiRegistryManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class EuiStartupActivity implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        try {
            EuiRegistryManager.initialize(project);
        } catch (IOException ex) {
            NotificationGroupManager.getInstance().getNotificationGroup("EUI Plugin")
                    .createNotification("Failed to read EUI documentation", "", NotificationType.ERROR)
                    .notify(project);
        } catch (EuiNotFoundException ex) {
            // Ignore, as missing EUI components might not be an error
        }
        return null;
    }
}
