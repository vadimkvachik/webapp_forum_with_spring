package com.vpd.courseproject.forum.utils;

import com.vpd.courseproject.forum.utils.api.IFormatter;
import org.springframework.stereotype.Service;

@Service
public class Formatter implements IFormatter {

    public String formatTextForPreview(String text, int size) {
        if (text.length() < size) {
            return text.replace("<br>", " ");
        } else {
            String cutText = text.substring(0, size).replace("<br>", " ");
            int lastSpace = cutText.lastIndexOf(" ");
            if (lastSpace != -1) {
                return cutText.substring(0, lastSpace) + "...";
            } else {
                return cutText + "...";
            }
        }
    }
}


