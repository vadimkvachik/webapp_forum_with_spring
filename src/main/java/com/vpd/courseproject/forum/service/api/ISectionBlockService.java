package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.entity.SectionBlock;

import java.util.List;

public interface ISectionBlockService {

    String addSectionBlockAndReturnPath(String login, String name);

    List<SectionBlock> getAllSectionBlocks();

    String renameSectionBlockAndReturnPath(String login, long id, String newName);

    void deleteSectionBlock(String login, long id);
}
