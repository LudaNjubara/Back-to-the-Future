package utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import model.InitialDirectoriesOptions;
import model.PopulateDirectoriesOptions;
import settings.PluginSettingsState;

public class FeatureUtils {

    /**
     * Generate the feature.
     *
     * @param project     the project
     * @param selectedDir the selected directory
     * @throws Exception if an error occurs
     */
    public static void generateFeature(Project project, PsiDirectory selectedDir) throws Exception {
        if (!DirectoryUtils.isDirectoryEmpty(selectedDir)) {
            throw new Exception("Directory must be empty!");
        }

        PluginSettingsState settings = PluginSettingsState.getInstance();

        if (settings.useSeparateFolders) {
            generateFeatureSeparate(project, selectedDir);
        } else {
            generateFeatureCombined(project, selectedDir);
        }

    }

    private static void generateFeatureSeparate(Project project, PsiDirectory selectedDir) throws Exception {
        InitialDirectoriesOptions idOptions = InitialDirectoriesOptions.builder()
                .project(project)
                .selectedDir(selectedDir)
                .useSeparateFolders(true)
                .build();

        PopulateDirectoriesOptions pdOptions = PopulateDirectoriesOptions.builder()
                .project(project)
                .selectedDir(selectedDir)
                .useSeparateFolders(true)
                .build();

        DirectoryUtils.createInitialDirectories(idOptions);
        DirectoryUtils.populateDirectories(pdOptions);
    }

    private static void generateFeatureCombined(Project project, PsiDirectory selectedDir) throws Exception {
        InitialDirectoriesOptions idOptions = InitialDirectoriesOptions.builder()
                .project(project)
                .selectedDir(selectedDir)
                .useSeparateFolders(false)
                .build();

        PopulateDirectoriesOptions pdOptions = PopulateDirectoriesOptions.builder()
                .project(project)
                .selectedDir(selectedDir)
                .useSeparateFolders(false)
                .build();

        DirectoryUtils.createInitialDirectories(idOptions);
        DirectoryUtils.populateDirectories(pdOptions);
    }
}