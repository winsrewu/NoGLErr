package org.jawbts.noglerr.test;

import org.jawbts.noglerr.client.NoglerrClient;
import org.jawbts.noglerr.screen.ConfigScreen;
import org.jawbts.noglerr.util.PlayerMessageSender;


public class Tester {
    static Task[] tasks = new Task[] {
            new Task("Hello, world!") {
                public void run() {
                    System.out.println("Hello, world!");
                }
            },
            new Task("PMS") {
                public void run() {
                    PlayerMessageSender pms = PlayerMessageSender.getInstance();
                    pms.add("Hello, world!");
                    pms.add("red", "Hello, world!");
                }
            },
            new Task("malilib") {
                public void run() {
                    fi.dy.masa.malilib.util.StringUtils.getModVersionString(NoglerrClient.MOD_ID);
                }
            },
            new Task("configScreen") {
                public void run() {
                    new ConfigScreen();
                }
            }
    };

    public static boolean runAll() {
        PlayerMessageSender pms = PlayerMessageSender.getInstance();
        for (Task task : tasks) {
            pms.add("yellow", "Task: " + task.name);
            try {
                task.run();
            } catch (Exception e) {
                return false;
            }
            pms.add("green", "Done: " + task.name);
        }
        pms.add("green", "All tasks done.");
        return true;
    }

    public abstract static class Task {
        public Task(String name) {
            this.name = name;
        }

        public abstract void run();
        public String name;
    }
}