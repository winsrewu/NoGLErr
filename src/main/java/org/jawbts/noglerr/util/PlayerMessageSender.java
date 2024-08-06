package org.jawbts.noglerr.util;

import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.*;
import org.jawbts.noglerr.tweak.Utils;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * 用于向玩家发送消息的工具类
 * <p>所有{@code add()}方法都是把东西加到缓存里头, {@code send()}方法才是真正发送</p>
 */
public class PlayerMessageSender {
    /**
     * 全局消息 处理位置如下
     * {@link org.jawbts.noglerr.event.OnTick#onTick(MinecraftClient)}
     */
    static PlayerMessageSender sPlayerMesSender = new PlayerMessageSender();
    private final List<Text> MesList = Collections.synchronizedList(new ArrayList<>());

    /**
     * 获取内置的全局消息实例
     *
     * @return 那个实例
     */
    public static PlayerMessageSender getInstance() {
        return sPlayerMesSender;
    }

    /**
     * 把内容加入发送缓存
     *
     * @param text 要发送的 Text 对象
     */
    public void add(Text text) {
        MesList.add(text);
    }

    /**
     * 把内容加入发送缓存
     *
     * @param color 颜色, 见 {@link TextColor#parse(String)}
     * @param s     要发送的内容(会经过翻译)
     */
    public void add(String color, String s) {
        s = StringUtils.translate(s.replace("%", "%%"));
        MesList.add(Text.literal(s).setStyle(Style.EMPTY.withColor(TextColor.parse(color).getOrThrow())));
    }

    /**
     * 把内容加入发送缓存
     *
     * @param s 要发送的内容(会经过翻译)
     */
    public void add(String s) {
        // 这里头会过String.format(), 所以要加这个.
        s = StringUtils.translate(s.replace("%", "%%"));
        MesList.add(Text.of(s));
    }

    /**
     * 把内容加入发送缓存
     *
     * @param s 将列表内的所有内容{@code toString()}, 然后加入缓存. 不会翻译.
     */
    public void add(@NotNull List<?> s) {
        for (Object ss : s) {
            add(Utils.escapeString(ss.toString()));
        }
    }

    /**
     * 将缓存内所有内容发给某个玩家, 然后清掉缓存
     *
     * @param player 要发给的玩家
     */
    public void send(ClientPlayerEntity player) {
        if (!isReady()) {
            return;
        }
        if (MesList.isEmpty()) {
            return;
        }
        // MesList可能会被其他线程操作
        List<Text> MesListCopy = List.copyOf(MesList);
        for (Text text : MesListCopy) {
            player.sendMessage(text, false);
        }
        MesList.clear();
    }

    private boolean isReady() {
        return Utils.playerReadyCheck();
    }

    public static class Tools {
        public static Text genUrlOpenText(@NotNull URL url) {
            return Text.literal(url.toString()).setStyle(
                    Style.EMPTY.withClickEvent(
                                    new ClickEvent(ClickEvent.Action.OPEN_URL, url.toString()))
                            .withColor(TextColor.parse("blue").getOrThrow()).withUnderline(true)
            );
        }
    }
}