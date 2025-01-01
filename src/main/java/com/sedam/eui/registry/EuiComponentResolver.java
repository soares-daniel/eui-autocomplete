package com.sedam.eui.registry;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.sedam.eui.exception.EuiNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EuiComponentResolver {

    private static String euiComponentsPackageUrl = null;
    private static String euiEclPackageUrl = null;

    /**
     * Get the EUI components package
     * @param project Project
     */
    public static void findEuiModule(Project project) throws EuiNotFoundException {
        ProjectRootManager.getInstance(project).getFileIndex().iterateContent(fileOrDir -> {
            if (fileOrDir.isDirectory() && fileOrDir.getName().equals("node_modules")) {
                VirtualFile[] children = fileOrDir.getChildren();
                for (VirtualFile child : children) {
                    if (child.getName().equals("@eui")) {
                        var componentsFolder = child.findChild("components");
                        if (componentsFolder != null) {
                            euiComponentsPackageUrl = componentsFolder.getPath();
                        }
                        var eclFolder = child.findChild("ecl");
                        if (eclFolder != null) {
                            euiEclPackageUrl = eclFolder.getPath();
                        }
                        return false; // Stop iterating
                    }
                }
            }
            return true;
        });

        if (euiComponentsPackageUrl == null || euiEclPackageUrl == null) {
            throw new EuiNotFoundException();
        }
    }

    public static List<String> getEuiComponentPackages(Project project) throws EuiNotFoundException {
        findEuiModule(project);
        return new ArrayList<>(Arrays.asList(euiComponentsPackageUrl, euiEclPackageUrl));
    }
}
