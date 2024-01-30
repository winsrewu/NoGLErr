package org.jawbts.noglerr.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jawbts.noglerr.client.NoglerrClient;

import java.net.URL;

public class UpdateChecker {
    private final String apiUrl;
    private final boolean silent;
    private boolean noticed;
    volatile private VersionInfo versionInfo = null;
    volatile private boolean failed = false;

    public UpdateChecker(boolean silent, String apiUrl) {
        this.apiUrl = apiUrl;
        this.silent = silent;
        UpdateCheckThread checkThread = new UpdateCheckThread();
        NoglerrClient.LOGGER.info("Noglerr update check start." + (silent ? " Silent Mode." : ""));
        checkThread.start();
        noticed = false;
    }

    public boolean isFailed() {
        return failed;
    }

    private void setFailed() {
        failed = true;
    }

    public boolean isNoticed() {
        return noticed;
    }

    public void noticed() {
        noticed = true;
    }

    public VersionInfo getVersionInfo() {
        return versionInfo;
    }

    private void setVersionInfo(VersionInfo vi) {
        versionInfo = vi;
    }

    public boolean isSilent() {
        return silent;
    }

    private class UpdateCheckThread extends Thread {
        private Thread thread;

        @Override
        public void start() {
            if (thread == null) {
                thread = new Thread(this, "noglerr-update-check-thread");
                thread.start();
            }
        }

        @Override
        public void run() {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(apiUrl);
            JsonObject jsonObject;
            try {
                HttpResponse res = client.execute(httpGet);
                String entityString = EntityUtils.toString(res.getEntity());
                if (!silent) NoglerrClient.LOGGER.info(entityString
                        .replaceAll("\n", "").replaceAll(" ", ""));

                if (res.getStatusLine().getStatusCode() >= 200 && res.getStatusLine().getStatusCode() < 300) {
                    JsonParser parser = new JsonParser();
                    jsonObject = parser.parse(entityString).getAsJsonObject();
                    VersionInfo vi = new VersionInfo();
                    jsonObject = jsonObject.get("data").getAsJsonObject();

                    vi.lowestSafeVersion = jsonObject.get("lowestSafeVersion").getAsString();
                    if (jsonObject.has("checkUrl")) {
                        vi.checkUrl = new URL(jsonObject.get("checkUrl").getAsString());
                    }
                    String branchId = NoglerrClient.MOD_BRANCH_ID;
                    jsonObject = jsonObject.get("branches").getAsJsonObject().get(branchId).getAsJsonObject();
                    vi.latestVersion = jsonObject.get("latestVersion").getAsString();
                    vi.latestDownloadUrl = new URL(jsonObject.get("downloadUrl").getAsString());

                    setVersionInfo(vi);
                } else {
                    setFailed();
                    if (!silent) {
                        NoglerrClient.LOGGER.error("Noglerr update check failed. Wrong status code (" +
                                res.getStatusLine().getStatusCode() + ").");
                    }
                }
            } catch (Exception e) {
                setFailed();
                NoglerrClient.LOGGER.error("Noglerr update check failed. " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
