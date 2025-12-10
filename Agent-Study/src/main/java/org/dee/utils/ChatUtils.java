package org.dee.utils;

public class ChatUtils {


    public static String buildConversationKey(String conversationId,Long userId){
        return "chat:conversation:" + userId + ":" + conversationId;
    }

    public static String getConversationIdFromKey(String conversationKey){
        return conversationKey.split(":")[3];
    }
    public static String getUserIdFromKey(String conversationKey){
        return conversationKey.split(":")[2];
    }
}
