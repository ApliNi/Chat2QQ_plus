package io.github.aplini.chat2qq.listener;

import io.github.aplini.chat2qq.Chat2QQ;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static io.github.aplini.chat2qq.utils.renderGroupMessage.renderMessage1;
import static io.github.aplini.chat2qq.utils.renderGroupMessage.renderMessage2;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class onGroupMessage implements Listener {
    private final Chat2QQ plugin;
    public onGroupMessage(Chat2QQ plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e) {
        // QQID黑名单
        if(plugin.getConfig().getLongList("blacklist.qq").contains(e.getSenderID())) return;

        // 渲染为可见消息
        String [] message = renderMessage1(plugin, e);


        if(! message[2].equals("") &&
                plugin.getConfig().getLongList("bot.bot-accounts").contains(e.getBotID()) &&
                plugin.getConfig().getLongList("general.group-ids").contains(e.getGroupID())){

            // 输出到控制台
            if(plugin.getConfig().getBoolean("aplini.other-format-presets.message-to-log", true)){
                getLogger().info(message[3]);
            }

            // 渲染为JSON消息
            TextComponent formatText = renderMessage2(plugin, message, e);

            // 广播
            getServer().spigot().broadcast(formatText);
        }

    }

}
