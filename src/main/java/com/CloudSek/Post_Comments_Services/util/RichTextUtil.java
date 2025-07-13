package com.CloudSek.Post_Comments_Services.util;

public class RichTextUtil {
    // Convert plain text to simple HTML with basic formatting and it's support Supports: **bold**, *italic*, [link](url)
    public static String convertToRichText(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return plainText;
        }
        
        String richText = plainText;
        // Convert **text** to <strong>text</strong>
        richText = richText.replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>");
        // Convert *text* to <em>text</em>
        richText = richText.replaceAll("\\*(.*?)\\*", "<em>$1</em>");
        // Convert [text](url) to <a href="url">text</a>
        richText = richText.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "<a href=\"$2\">$1</a>");
        // Convert line breaks to <br>
        richText = richText.replaceAll("\n", "<br>");
        return richText;
    }

    // Here we will convert rich text back to plain text
    public static String convertToPlainText(String richText) {
        if (richText == null || richText.isEmpty()) {
            return richText;
        }
        String plainText = richText;
        // Remove HTML tags
        plainText = plainText.replaceAll("<[^>]*>", "");
        // Convert <br> back to line breaks
        plainText = plainText.replaceAll("<br>", "\n");
        return plainText;
    }
} 