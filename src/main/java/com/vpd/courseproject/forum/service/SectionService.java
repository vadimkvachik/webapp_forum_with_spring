package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.dao.IMessageDao;
import com.vpd.courseproject.forum.persistence.dao.ISectionBlockDao;
import com.vpd.courseproject.forum.persistence.dao.ISectionDao;
import com.vpd.courseproject.forum.persistence.dao.ITopicDao;
import com.vpd.courseproject.forum.persistence.dto.SectionDto;
import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Section;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;
import com.vpd.courseproject.forum.service.api.ISectionService;
import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SectionService implements ISectionService {
    private static final Logger logger = Logger.getLogger(SectionService.class);
    private ISectionBlockDao sectionBlockDao;
    private ISectionDao sectionDao;
    private ITopicDao topicDao;
    private IMessageDao messageDao;
    private IFormatter formatter;

    public SectionService(ISectionBlockDao sectionBlockDao, ISectionDao sectionDao, ITopicDao topicDao,
                          IMessageDao messageDao, IFormatter formatter) {
        this.sectionBlockDao = sectionBlockDao;
        this.sectionDao = sectionDao;
        this.topicDao = topicDao;
        this.messageDao = messageDao;
        this.formatter = formatter;
    }

    public String addSectionAndReturnPath(String login, String name, long sectionBlockId) {
        SectionBlock sectionBlock = sectionBlockDao.findById(sectionBlockId).orElse(null);
        assert sectionBlock != null;
        Section section = new Section(name, sectionBlock);
        sectionDao.save(section);
        logger.info("User '" + login + "' added the section '" + section.getName()
                + "' in the section block '" + sectionBlock.getName() + "'");
        return "/#section" + section.getId();
    }

    public Section getSection(long id) {
        return sectionDao.findById(id).orElse(null);
    }

    public List<SectionDto> getSectionEntries() {
        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (Section section : sectionDao.findAll()) {
            SectionDto sectionDto = new SectionDto();
            sectionDto.setSection(section);
            sectionDto.setNumberOfTopics(topicDao.countBySectionId(section.getId()));
            sectionDto.setNumberOfMessages(messageDao.countMessagesByTopicSectionId(section.getId()));
            List<Message> lastMessageList = messageDao.findLastMessageBySectionId(section.getId());
            if (lastMessageList.size() > 0) {
                sectionDto.setLastMessage(lastMessageList.get(0));
                sectionDto.setLastMessageTopicNamePreview(formatter.formatTextForPreview(lastMessageList.get(0).getTopic().getName(), 20));
            }
            sectionDtoList.add(sectionDto);
        }
        Collections.sort(sectionDtoList);
        return sectionDtoList;
    }

    public String renameSectionAndReturnPath(String login, long id, String newName) {
        Section section = sectionDao.findById(id).orElse(null);
        assert section != null;
        String oldName = section.getName();
        section.setName(newName);
        sectionDao.save(section);
        logger.info("User '" + login + "' renamed the section '" + oldName + "' to '" + section.getName() + "'");
        return "/#section" + section.getId();
    }

    public void deleteSection(String login, long id) {
        Section section = sectionDao.findById(id).orElse(null);
        assert section != null;
        sectionDao.delete(section);
        logger.info("User '" + login + "' deleted the section '" + section.getName()
                + "' in the section block '" + section.getSectionBlock().getName() + "'");
    }
}
