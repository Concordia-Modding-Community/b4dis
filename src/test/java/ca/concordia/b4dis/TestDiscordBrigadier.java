package ca.concordia.b4dis;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.internal.entities.UserImpl;
import net.dv8tion.jda.internal.requests.restaction.MessageActionImpl;

public class TestDiscordBrigadier {
    public static class Mocking {
        public static final String ADMIN_ROLE = "admininstrator";

        public static String CHAT_RESPONSE = null;

        public static User createNormalUser() {
            UserImpl user = mock(UserImpl.class);

            return user;
        }

        public static MessageAction createMessageAction() {
            MessageActionImpl messageAction = mock(MessageActionImpl.class);

            return messageAction;
        }

        public static TextChannel createTextChannel() {
            TextChannel channel = mock(TextChannel.class);

            when(channel.sendMessage(anyString())).thenAnswer(answer ->  {
                CHAT_RESPONSE = answer.getArgument(0);

                return createMessageAction();
            });

            return channel;
        }

        public static Message createMessage(User user, String text) {
            Message message = mock(Message.class);
    
            when(message.getAuthor()).thenReturn(user);
    
            when(message.getContentDisplay()).thenReturn(text);
    
            when(message.getContentRaw()).thenReturn(text);
    
            when(message.getContentStripped()).thenReturn(text);

            TextChannel channel = createTextChannel();

            when(message.getTextChannel()).thenReturn(channel);
    
            return message;
        }

        public static void sendMessage(DiscordBrigadier discordBrigadier, User user, String text) {
            MessageReceivedEvent event = mock(MessageReceivedEvent.class);
    
            Message message = createMessage(user, text);

            when(event.getAuthor()).thenReturn(user);
    
            when(event.getMessage()).thenReturn(message);

            discordBrigadier.onMessageReceived(event);
        }
    }

    @Before
    public void resetChatResponse() {
        Mocking.CHAT_RESPONSE = null;
    }

    @Test
    public void testPingPong() {
        DiscordBrigadier discordBrigadier = new DiscordBrigadier();

        discordBrigadier.register(DiscordBrigadier.literal("ping").executes(context -> {
            context.getSource().getTextChannel().sendMessage("pong").queue();

            return 1;
        }));

        User user = Mocking.createNormalUser();

        Mocking.sendMessage(discordBrigadier, user, "!ping");
    }
}
