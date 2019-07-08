package com.vpd.courseproject.forum.service.api;

import com.vpd.courseproject.forum.persistence.dto.SectionDto;
import com.vpd.courseproject.forum.persistence.entity.Section;

import java.util.List;

public interface ISectionService {

    String addSectionAndReturnPath(String login, String name, long sectionBlockId);

    Section getSection(long id);

    List<SectionDto> getSectionEntries();

    String renameSectionAndReturnPath(String login, long id, String newName);

    void deleteSection(String login, long id);
}
