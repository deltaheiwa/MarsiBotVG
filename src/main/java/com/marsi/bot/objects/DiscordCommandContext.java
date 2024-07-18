package com.marsi.bot.objects;

import com.marsi.bot.enums.DiscordCommandEventType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;
import java.util.stream.*;

public class DiscordCommandContext {
    private final DiscordCommandEventType type;
    private final MessageReceivedEvent textEvent;
    private final SlashCommandInteractionEvent slashEvent;
    private final List<String> args;

    public DiscordCommandContext(MessageReceivedEvent event, List<String> args) {
        this.textEvent = event;
        this.args = args;
        this.slashEvent = null;
        this.type = DiscordCommandEventType.TEXT;
    }

    public DiscordCommandContext(SlashCommandInteractionEvent event) {
        this.slashEvent = event;
        this.textEvent = null;
        this.args = event.getOptions().stream()
                .map(OptionMapping::getAsString)
                .collect(Collectors.toList());
        this.type = DiscordCommandEventType.SLASH;
    }

    public MessageReceivedEvent getTextEvent() {
        return textEvent;
    }

    public SlashCommandInteractionEvent getSlashEvent() {
        return slashEvent;
    }

    public DiscordCommandEventType getType() {
        return type;
    }

    public Guild getGuild() {
        if (type == DiscordCommandEventType.TEXT) {
            return textEvent.getGuild();
        } else {
            return slashEvent.getGuild();
        }
    }

    public Member getAuthorMember() {
        if (type == DiscordCommandEventType.TEXT) {
            return textEvent.getMember();
        } else {
            return slashEvent.getMember();
        }
    }

    public User getAuthorUser() {
        if (type == DiscordCommandEventType.TEXT) {
            return textEvent.getAuthor();
        } else {
            return slashEvent.getUser();
        }
    }

    public void sendMessage(String msg) {
        if (type == DiscordCommandEventType.TEXT) {
            textEvent.getChannel().sendMessage(msg).queue();
        } else {
            slashEvent.reply(msg).queue();
        }
    }

    public void sendMessage(String msg, boolean ephemeral) {
        if (type == DiscordCommandEventType.TEXT) {
            textEvent.getChannel().sendMessage(msg).queue();
        } else {
            slashEvent.reply(msg).setEphemeral(ephemeral).queue();
        }
    }

    public void sendMessage(String msg, MessageEmbed embed) {
        if (type == DiscordCommandEventType.TEXT) {
            textEvent.getChannel().sendMessage(msg).setEmbeds(embed).queue();
        } else {
            slashEvent.reply(msg).setEmbeds(embed).queue();
        }
    }


    public List<String> getArgs() {
        return args;
    }

    public String getArg(int index) {
        if (index >= args.size()) {
            return null;
        }

        return args.get(index);
    }

    public List<Member> getMentionedMembers() {
        if (type == DiscordCommandEventType.TEXT) {
            return textEvent.getMessage().getMentions().getMembers();
        } else {
            return List.of(slashEvent.getOption("user").getAsMember());
        }
    }
}
