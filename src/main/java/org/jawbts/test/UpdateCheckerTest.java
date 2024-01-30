package org.jawbts.test;

import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.util.UpdateChecker;

public class UpdateCheckerTest {
    public static void main(String[] args) {
        UpdateChecker uc = new UpdateChecker(false, "http://localhost:3000/version/noglerr");
        while (uc.getVersionInfo() == null && !uc.isFailed());
        if (uc.isFailed()) {
            System.out.println("Update check failed.");
            return;
        }

        System.out.println(uc.getVersionInfo());
        if (uc.getVersionInfo().isOutDated(NoglerrClient.MOD_VERSION)) {
            System.out.println("Out Dated!");
        }
        if (uc.getVersionInfo().isNotSafe(NoglerrClient.MOD_VERSION)) {
            if (uc.getVersionInfo().checkUrl != null) System.out.println("Current version not safe. Please check the url: " +
                    uc.getVersionInfo().checkUrl);
            if (uc.getVersionInfo().safeVersionReady()) {
                System.out.println("DownLoad latest version at " + uc.getVersionInfo().latestDownloadUrl);
            } else {
                System.out.println("Safe version not ready yet. Please disable your mod.");
            }
        }
    }
}