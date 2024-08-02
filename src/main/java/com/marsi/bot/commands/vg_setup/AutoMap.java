package com.marsi.bot.commands.vg_setup;

import com.marsi.bot.objects.DiscordCommand;
import com.marsi.bot.objects.DiscordCommandContext;
import com.marsi.vg.Launcher;
import com.marsi.commons.database.Row;
import com.marsi.vg.database.ServerInfoJsonDatabase;
import com.marsi.vg.entities.Lobby;
import com.marsi.vg.entities.Location;
import com.marsi.vg.enums.LobbyStatus;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class AutoMap extends DiscordCommand {
    @Override
    public String getName() {
        return "auto-map";
    }

    @Override
    public String getDescription() {
        return "Automatically tries to map the VG server.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("automap");
    }

    @Override
    public void execute(DiscordCommandContext context) {
        try {
            Row lobbyInfo = Launcher.getDatabase().getLobbyByServerId(context.getGuild().getId()).get();
            if (lobbyInfo == null) {
                Launcher.getDatabase().createLobby(context.getGuild().getId(), LobbyStatus.OPEN.asDatabaseId(), 30);
                lobbyInfo = Launcher.getDatabase().getLobbyByServerId(context.getGuild().getId()).get();
            }
            Lobby lobby = new Lobby(
                    (int) lobbyInfo.get("id"),
                    (String) lobbyInfo.get("server_id"),
                    (LobbyStatus) lobbyInfo.get("status"),
                    (int) lobbyInfo.get("max_players")
            );

            HashMap<String, Long> houses = new HashMap<>();
            HashMap<String, Long> role_channels = new HashMap<>();
            List<Channel> unregisteredChannels = new ArrayList<>();
            context.getGuild().getCategoryCache().forEachUnordered(category -> {
                if (category.getName().toLowerCase().contains("houses") || category.getName().toLowerCase().contains("nighttime")) {
                    for (int hn = 1; hn <= category.getChannels().size(); hn++) {
                        GuildChannel channel = category.getChannels().get(hn - 1);
                        Location house = new Location(String.format("h%s", hn), "house-" + hn, channel.getIdLong());
                        if (!channel.getName().toLowerCase().contains("house") && !channel.getName().toLowerCase().contains(String.format("%s", hn))) {
                            channel.getManager().setName("house-" + hn).queue();
                        }
                        houses.put("house-" + hn, category.getChannels().get(hn - 1).getIdLong());
                    }
                } else if (category.getName().toLowerCase().contains("roles")) {
                    for (int rn = 1; rn <= category.getChannels().size(); rn++) {
                        GuildChannel channel = category.getChannels().get(rn - 1);
                        role_channels.put(String.valueOf(rn), channel.getIdLong());
                    }
                } else {
                    category.getChannels().forEach(channel -> {
                        String channelName = channel.getName().toLowerCase().replace("-", " ");
                        if (channelName.contains("rules")) {
                            lobby.getChannelMap().put("rules", channel.getIdLong());
                        } else if (channelName.contains("mechanics")) {
                            lobby.getChannelMap().put("mechanics", channel.getIdLong());
                        } else if (channelName.contains("pre game announcements") || channelName.contains("minor announcements")) {
                            lobby.getChannelMap().put("pg-announcements", channel.getIdLong());
                        } else if (channelName.contains("playerlist") || channelName.contains("player list")) {
                            lobby.getChannelMap().put("player-list", channel.getIdLong());
                        } else if (channelName.contains("announcements")) {
                            lobby.getChannelMap().put("announcements", channel.getIdLong());
                        } else if (channelName.contains("overseer status")) {
                            lobby.getChannelMap().put("overseer-status", channel.getIdLong());
                        } else if (channelName.contains("map")) {
                            lobby.getChannelMap().put("map", channel.getIdLong());
                        } else if (channelName.contains("death reports")) {
                            lobby.getChannelMap().put("death-reports", channel.getIdLong());
                        } else if (channelName.contains("megaphone")) {
                            lobby.getChannelMap().put("megaphone", channel.getIdLong());
                        } else if (channelName.contains("day chat") || channelName.contains("day discussion")) {
                            lobby.getChannelMap().put("day-chat", channel.getIdLong());
                        } else if (channelName.contains("vote channel")) {
                            lobby.getChannelMap().put("vote-channel", channel.getIdLong());
                        } else if (channelName.contains("vote count")) {
                            lobby.getChannelMap().put("vote-count", channel.getIdLong());
                        } else if (channelName.contains("graveyard")) {
                            lobby.getChannelMap().put("graveyard", channel.getIdLong());
                        }
                        else {
                            unregisteredChannels.add(channel);
                        }
                    });
                }
            });

            ServerInfoJsonDatabase.saveServerInfo(context.getGuild().getId(), lobby.getChannelMap(), role_channels, houses);

            StringBuilder response = new StringBuilder("Server mapped successfully! \n\nUnmapped channels: ");
            for (Channel channel : unregisteredChannels) {
                response.append(channel.getAsMention()).append(", ");
            }

            context.sendMessage(response.toString());
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error while trying to get result from the Future Task", e);
        }
    }

}
