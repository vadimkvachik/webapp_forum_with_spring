package com.vpd.courseproject.forum.persistence.dto;

import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Section;
import org.springframework.stereotype.Component;

@Component
public class SectionDto implements Comparable<SectionDto>{
    private Section section;
    private Message lastMessage;
    private String lastMessageTopicNamePreview;
    private long numberOfTopics;
    private long numberOfMessages;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTopicNamePreview() {
        return lastMessageTopicNamePreview;
    }

    public void setLastMessageTopicNamePreview(String lastMessageTopicNamePreview) {
        this.lastMessageTopicNamePreview = lastMessageTopicNamePreview;
    }

    public long getNumberOfTopics() {
        return numberOfTopics;
    }

    public void setNumberOfTopics(long numberOfTopics) {
        this.numberOfTopics = numberOfTopics;
    }

    public long getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(long numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    @Override
    public int compareTo(SectionDto o) {
        return section.getName().compareTo(o.section.getName());
    }


}
