package com.vpd.courseproject.forum.service;

import com.vpd.courseproject.forum.persistence.dao.ISectionBlockDao;
import com.vpd.courseproject.forum.persistence.entity.SectionBlock;
import com.vpd.courseproject.forum.service.api.ISectionBlockService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionBlockService implements ISectionBlockService {
    private static final Logger logger = Logger.getLogger(SectionBlockService.class);
    private ISectionBlockDao sectionBlockDao;

    public SectionBlockService(ISectionBlockDao sectionBlockDao) {
        this.sectionBlockDao = sectionBlockDao;
    }

    public String addSectionBlockAndReturnPath(String login, String name) {
        SectionBlock sectionBlock = new SectionBlock(name);
        sectionBlockDao.save(sectionBlock);
        logger.info("User '" + login + "' added the section block '" + sectionBlock.getName() + "'");
        return "/#sectionBlock" + sectionBlock.getId();
    }

    public List<SectionBlock> getAllSectionBlocks() {
        return (List<SectionBlock>) sectionBlockDao.findAll();
    }

    public String renameSectionBlockAndReturnPath(String login, long id, String newName) {
        SectionBlock sectionBlock = sectionBlockDao.findById(id).orElse(null);
        assert sectionBlock != null;
        String oldName = sectionBlock.getName();
        sectionBlock.setName(newName);
        sectionBlockDao.save(sectionBlock);
        logger.info("User '" + login + "' renamed the section block '" + oldName + "' to '" + sectionBlock.getName() + "'");
        return "/#sectionBlock" + sectionBlock.getId();
    }

    public void deleteSectionBlock(String login, long id) {
        SectionBlock sectionBlock = sectionBlockDao.findById(id).orElse(null);
        assert sectionBlock != null;
        sectionBlockDao.delete(sectionBlock);
        logger.info("User '" + login + "' deleted the section block '" + sectionBlock.getName() + "'");
    }

}
