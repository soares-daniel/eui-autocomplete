package com.sedam.eui.registry;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.sedam.eui.exception.EuiNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EuiComponentResolver {

    private static String euiComponentsPackageUrl = null;
    private static String euiEclPackageUrl = null;

    /**
     * Recursively searches for the `node_modules` directory within the project.
     *
     * @param startDir The starting directory for the search.
     * @return The `node_modules` directory as a VirtualFile, or null if not found.
     */
    private static VirtualFile findNodeModulesRecursively(VirtualFile startDir) {
        if (startDir == null) return null;

        VirtualFile nodeModules = startDir.findChild("node_modules");
        if (nodeModules != null) {
            return nodeModules;
        }

        for (VirtualFile child : startDir.getChildren()) {
            if (child.isDirectory()) {
                VirtualFile result = findNodeModulesRecursively(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Finds the EUI library in the project.
     *
     * @param project The IntelliJ project instance.
     * @return The VirtualFile for the EUI component package, or null if not found.
     */
    public static VirtualFile getEuiVirtualFile(Project project) {
        String basePath = project.getBasePath();

        if (basePath != null) {
            LocalFileSystem localFileSystem = LocalFileSystem.getInstance();
            VirtualFile baseDir = localFileSystem.findFileByPath(basePath);

            if (baseDir != null) {
                VirtualFile nodeModules = findNodeModulesRecursively(baseDir);
                if (nodeModules != null) {
                    return nodeModules.findChild("@eui");

                }
            }
        }

        return null;
    }

    /**
     * Get the EUI components package
     * @param project Project
     */
    public static void findEuiModule(Project project) throws EuiNotFoundException {
        VirtualFile euiModule = getEuiVirtualFile(project);

        if (euiModule == null) {
            throw new EuiNotFoundException();
        }

        for (VirtualFile child : euiModule.getChildren()) {
            if (child.getName().equals("components")) {
                euiComponentsPackageUrl = child.getPath();
            } else if (child.getName().equals("ecl")) {
                euiEclPackageUrl = child.getPath();
            }
        }

        if (euiComponentsPackageUrl == null || euiEclPackageUrl == null) {
            throw new EuiNotFoundException();
        }
    }

    public static List<String> getEuiComponentPackages(Project project) throws EuiNotFoundException {
        findEuiModule(project);
        return new ArrayList<>(Arrays.asList(euiComponentsPackageUrl, euiEclPackageUrl));
    }
}
