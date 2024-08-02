package com.marsi.bot;

import com.marsi.bot.commands.admin.*;
import com.marsi.bot.commands.info.*;
import com.marsi.bot.commands.vg_setup.*;
import com.marsi.bot.listeners.*;
import com.marsi.bot.objects.DiscordCommand;
import com.marsi.console.ConsoleThread;
import com.marsi.database.PrefixDatabase;
import com.marsi.vg.Launcher;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Marsi {
    private final static Logger logger = LoggerFactory.getLogger("Marsi");
    private final Dotenv config;
    private static Marsi instance = null;

    private boolean isRunning = false;
    private final ShardManager shardManager;

    private final PrefixDatabase prefixDatabase;

    private final List<DiscordCommand> commands;

    private Marsi() {
        config = Dotenv.configure().load();
        commands = new ArrayList<>();
        registerCommands();
        String token = config.get("DISCORD_TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new GeneralBotListener());
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Stargate"));
        builder.setEnabledIntents(getIntents());
        builder.enableCache(getCacheFlags());
        shardManager = builder.build(false);

        prefixDatabase = new PrefixDatabase("jdbc:sqlite:./src/main/resources/databases/prefixes.db");
        prefixDatabase.createTables();

        Launcher.initialize();
    }

    private EnumSet<GatewayIntent> getIntents() {
        EnumSet<GatewayIntent> intents = GatewayIntent.getIntents(GatewayIntent.DEFAULT);
        intents.add(GatewayIntent.MESSAGE_CONTENT);
        intents.add(GatewayIntent.GUILD_MEMBERS);
        intents.add(GatewayIntent.GUILD_PRESENCES);
        return intents;
    }

    private EnumSet<CacheFlag> getCacheFlags() {
        return EnumSet.of(CacheFlag.VOICE_STATE, CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS);
    }

    public Logger getLogger() {
        return logger;
    }

    private void registerCommands() {
        commands.add(new SetPrefix());
        commands.add(new Sync());
        commands.add(new Ping());

        commands.add(new AutoMap());
        commands.add(new MapChannel());
    }

    public PrefixDatabase getPrefixDatabase() {
        return prefixDatabase;
    }

    public static Marsi getInstance() {
        if (instance == null) {
            instance = new Marsi();
        }
        return instance;
    }

    public Dotenv getConfig() {
        return config;
    }

    public List<DiscordCommand> getCommands() {
        return commands;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public void run() {
        if (isRunning) {
            logger.warn("Tokra is already running");
            return;
        }
        try {
            shardManager.login();
            isRunning = true;
        } catch (InvalidTokenException e) {
            logger.error("Invalid token provided", e);
        }
        logger.info("I have logged in successfully as " + shardManager.getShards().get(0).getSelfUser().getName());
    }
}
