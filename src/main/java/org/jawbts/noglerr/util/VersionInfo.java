package org.jawbts.noglerr.util;

import java.net.URL;

public class VersionInfo {
    public String latestVersion;
    public String lowestSafeVersion;
    public URL checkUrl;
    public URL latestDownloadUrl;

    VersionInfo() {

    }
    VersionInfo(String latestVersion, String lowestSafeVersion, URL checkUrl, URL latestDownloadUrl) {
        this.latestVersion = latestVersion;
        this.lowestSafeVersion = lowestSafeVersion;
        this.checkUrl = checkUrl;
        this.latestDownloadUrl = latestDownloadUrl;
    }

    public boolean isOutDated(String curVersion) {
        return versionBigger(latestVersion, curVersion);
    }

    public boolean isNotSafe(String curVersion) {
        return versionBigger(lowestSafeVersion, curVersion);
    }

    public boolean safeVersionReady() {
        return !versionBigger(lowestSafeVersion, latestVersion);
    }

    /**
     * 版本比较, 只支持 (数字).(数字). (...) .(数字) 格式
     * @return 如果 a > b, 为 true , 反之 false
     */
    public static boolean versionBigger(String a, String b) {
        String[] sa = a.split("\\.");
        String[] sb = b.split("\\.");
        int minL = Math.min(sa.length, sb.length);
        for (int i = 0; i < minL; i++) {
            if (Integer.parseInt(sa[i]) > Integer.parseInt(sb[i])) {
                return true;
            }
            if (Integer.parseInt(sa[i]) < Integer.parseInt(sb[i])) {
                return false;
            }
        }
        return sa.length > sb.length;
    }

    @Override
    public String toString() {
        return "LatestVersion: " + latestVersion +
                "\nLowestSafeVersion: " + lowestSafeVersion +
                "\ncheckUrl: " + (checkUrl == null ? "Null" : checkUrl.toString()) +
                "\nlatestDownloadUrl: " + latestDownloadUrl.toString();
    }
}
