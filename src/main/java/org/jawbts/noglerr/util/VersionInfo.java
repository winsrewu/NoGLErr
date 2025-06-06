package org.jawbts.noglerr.util;

import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;

import java.net.URL;

public class VersionInfo {
    public final Version latestVersion;
    public final Version lowestSafeVersion;
    public final URL checkUrl;
    public final URL latestDownloadUrl;

    VersionInfo(String latestVersion, String lowestSafeVersion, URL checkUrl, URL latestDownloadUrl) throws VersionParsingException {
        this.latestVersion = Version.parse(latestVersion);
        this.lowestSafeVersion = Version.parse(lowestSafeVersion);
        this.checkUrl = checkUrl;
        this.latestDownloadUrl = latestDownloadUrl;
    }

    public boolean isOutDated(Version curVersion) {
        return latestVersion.compareTo(curVersion) > 0;
    }

    public boolean isNotSafe(Version curVersion) {
        return lowestSafeVersion.compareTo(curVersion) > 0;
    }

    public boolean safeVersionReady() {
        return lowestSafeVersion.compareTo(latestVersion) <= 0;
    }

    @Override
    public String toString() {
        return "LatestVersion: " + latestVersion +
                "\nLowestSafeVersion: " + lowestSafeVersion +
                "\ncheckUrl: " + (checkUrl == null ? "Null" : checkUrl.toString()) +
                "\nlatestDownloadUrl: " + latestDownloadUrl.toString();
    }

    public static class VersionInfoBuilder {
        private String latestVersion;
        private String lowestSafeVersion;
        private URL checkUrl;
        private URL latestDownloadUrl;

        public VersionInfoBuilder setLatestVersion(String latestVersion) {
            this.latestVersion = latestVersion;
            return this;
        }

        public VersionInfoBuilder setLowestSafeVersion(String lowestSafeVersion) {
            this.lowestSafeVersion = lowestSafeVersion;
            return this;
        }

        public VersionInfoBuilder setCheckUrl(URL checkUrl) {
            this.checkUrl = checkUrl;
            return this;
        }

        public VersionInfoBuilder setLatestDownloadUrl(URL latestDownloadUrl) {
            this.latestDownloadUrl = latestDownloadUrl;
            return this;
        }

        public VersionInfo build() throws VersionParsingException {
            if (latestVersion == null || lowestSafeVersion == null || latestDownloadUrl == null) {
                throw new IllegalArgumentException("latestVersion, lowestSafeVersion, and latestDownloadUrl must be set.");
            }
            return new VersionInfo(latestVersion, lowestSafeVersion, checkUrl, latestDownloadUrl);
        }
    }
}
