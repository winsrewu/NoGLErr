package org.jawbts.noglerr.util;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.jawbts.noglerr.tweak.Utils;

import java.util.ArrayList;
import java.util.List;

// 向玩家发送信息
public class PlayerMessageSender {
    // 全局消息 处理在event.OnTick
    static PlayerMessageSender sPlayerMesSender = new PlayerMessageSender();
    private final List<Text> MesList = new ArrayList<>();

    public static PlayerMessageSender getInstance() {
        return sPlayerMesSender;
    }

    public void add(Text text) {
        MesList.add(text);
    }

    public void add(String color, String s) {
        s = StringUtils.translate(s);
        // MesList.add(Text.Serializer.fromJson("{\"color\":\"" + color + "\",\"text\":\"" + s + "\"}"));
        MesList.add(new LiteralText(s).setStyle(Style.EMPTY.withColor(TextColor.parse(color))));
    }

    public void add(String s) {
        s = StringUtils.translate(s);
        MesList.add(Text.of(s));
    }

    public void add(List<?> s) {
        for (Object ss : s) {
            add(Utils.escapeString(ss.toString()));
        }
    }

    public void send(ClientPlayerEntity player) {
        if (!isReady()) {
            return;
        }
        if (MesList.isEmpty()) {
            return;
        }
        for (int i = 0; i < MesList.size(); i++) {
            player.sendMessage(MesList.get(i), false);
        }
        MesList.clear();
    }

    private boolean isReady() {
        return Utils.playerReadyCheck();
    }
}
