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
            String arg1 = context.getArg(0);
            Row lobbyInfo = Launcher.getDatabase().getLobbyByServerId(context.getGuild().getId()).get();
            if (lobbyInfo == null) {
                Launcher.getDatabase().addLobby(context.getGuild().getId(), LobbyStatus.OPEN.asDatabaseId(), 30);
                lobbyInfo = Launcher.getDatabase().getLobbyByServerId(context.getGuild().getId()).get();
            }
            Lobby lobby = new Lobby(
                    (int) lobbyInfo.get("id"),
                    (String) lobbyInfo.get("server_id"),
                    (LobbyStatus) lobbyInfo.get("status"),
                    (int) lobbyInfo.get("max_players")
            );

            HashMap<String, String> houses = new HashMap<>();
            HashMap<String, String> role_channels = new HashMap<>();
            List<Channel> unregisteredChannels = new ArrayList<>();
            context.getGuild().getCategoryCache().forEachUnordered(category -> {
                if (category.getName().toLowerCase().contains("houses") || category.getName().toLowerCase().contains("nighttime")) {
                    for (int hn = 1; hn <= category.getChannels().size(); hn++) {
                        GuildChannel channel = category.getChannels().get(hn - 1);
                        Location house = new Location(String.format("h%s", hn), lobby.getId(),"house-" + hn, channel.getIdLong());
                        if (!channel.getName().toLowerCase().contains("house") && !channel.getName().toLowerCase().contains(String.format("%s", hn))) {
                            channel.getManager().setName("house-" + hn).queue();
                        }
                        houses.put("h" + hn, category.getChannels().get(hn - 1).getId());
                        lobby.addHouse(house);
                    }
                } else if (category.getName().toLowerCase().contains("roles")) {
                    for (int rn = 1; rn <= category.getChannels().size(); rn++) {
                        GuildChannel channel = category.getChannels().get(rn - 1);
                        role_channels.put(channel.getId(), "");
                    }
                } else {
                    category.getChannels().forEach(channel -> {
                        String channelName = channel.getName().toLowerCase().replace("-", " ");
                        // The reason why I have to do this ugly stuff, is because I cant use .contains() with switch statement
                        // and I cant opt out for .equals() because the channel name can contain emojis, etc.
                        if (channelName.contains("rules")) {
                            lobby.setChannelId("rules", channel.getId());
                        } else if (channelName.contains("mechanics")) {
                            lobby.setChannelId("mechanics", channel.getId());
                        } else if (channelName.contains("pre game announcements") || channelName.contains("minor announcements")) {
                            lobby.setChannelId("pg-announcements", channel.getId());
                        } else if (channelName.contains("playerlist") || channelName.contains("player list")) {
                            lobby.setChannelId("player-list", channel.getId());
                        } else if (channelName.contains("announcements")) {
                            lobby.setChannelId("announcements", channel.getId());
                        } else if (channelName.contains("overseer status")) {
                            lobby.setChannelId("overseer-status", channel.getId());
                        } else if (channelName.contains("map")) {
                            lobby.setChannelId("map", channel.getId());
                        } else if (channelName.contains("death reports")) {
                            lobby.setChannelId("death-reports", channel.getId());
                        } else if (channelName.contains("megaphone")) {
                            lobby.setChannelId("megaphone", channel.getId());
                        } else if (channelName.contains("day chat") || channelName.contains("day discussion")) {
                            lobby.setChannelId("day-chat", channel.getId());
                        } else if (channelName.contains("vote channel")) {
                            lobby.setChannelId("vote-channel", channel.getId());
                        } else if (channelName.contains("vote count")) {
                            lobby.setChannelId("vote-count", channel.getId());
                        } else if (channelName.contains("graveyard")) {
                            lobby.setChannelId("graveyard", channel.getId());
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

            if (arg1 != null && arg1.equals("raw")) {
                Launcher.getDatabase().addLocationBulk(lobby.getHouses());
            }

            context.sendMessage(response.toString());
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error while trying to get result from the Future Task", e);
        }
    }

}
