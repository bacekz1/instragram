package com.s14ittalents.insta.util;

import com.s14ittalents.insta.exception.*;
import com.s14ittalents.insta.hashtag.Hashtag;
import com.s14ittalents.insta.post.Post;
import com.s14ittalents.insta.user.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.s14ittalents.insta.exception.Constant.REMOTE_IP;
public final class Helper {
    private final static char hashtag = '#';
    private final static char personTag = '@';
    private final static char space = ' ';

    private final static StringBuilder builder = new StringBuilder();

    private Helper() {
    }

    public static List<String> findHashTags(String text) {
        builder.delete(0,builder.length());
        boolean personTagFlag = false;
        boolean hashtagFlag = false;

        List<String> hashTagList = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char curChar = text.charAt(i);

            if (hashtagFlag) {
                if (curChar == hashtag) {
                    builder.delete(0, builder.length());
                    hashtagFlag = false;
                    continue;
                }
                if (curChar == personTag) {
                    builder.delete(0, builder.length());
                    hashtagFlag = false;
                    continue;
                }
            }

            if (personTagFlag) {
                if (curChar == personTag) {
                    builder.delete(0, builder.length());
                    personTagFlag = false;
                    continue;
                }
                if (curChar == hashtag) {
                    builder.delete(0, builder.length());
                    personTagFlag = false;
                    continue;
                }
            }

            if (curChar == space && hashtagFlag) {
                if (builder.length() > 1) {
                    hashTagList.add(builder.toString());
                }
                builder.delete(0, builder.length());
                hashtagFlag = false;
                continue;
            }

            if (curChar == space && personTagFlag) {
                builder.delete(0, builder.length());
                personTagFlag = false;
                continue;
            }

            if (curChar == hashtag) {
                hashtagFlag = true;
            }

            if (hashtagFlag) {
                builder.append(curChar);
            }

            if (curChar == personTag) {
                personTagFlag = true;
            }

            if (personTagFlag) {
                builder.append(curChar);
            }

        }
        return hashTagList;
    }

    public static List<String> findPersonTags(String text) {
        builder.delete(0,builder.length());
        boolean personTagFlag = false;
        boolean hashtagFlag = false;

        List<String> personTagList = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char curChar = text.charAt(i);

            if (hashtagFlag) {
                if (curChar == hashtag) {
                    builder.delete(0, builder.length());
                    hashtagFlag = false;
                    continue;
                }
                if (curChar == personTag) {
                    builder.delete(0, builder.length());
                    hashtagFlag = false;
                    continue;
                }
            }

            if (personTagFlag) {
                if (curChar == personTag) {
                    builder.delete(0, builder.length());
                    personTagFlag = false;
                    continue;
                }
                if (curChar == hashtag) {
                    builder.delete(0, builder.length());
                    personTagFlag = false;
                    continue;
                }
            }

            if (curChar == space && hashtagFlag) {
                builder.delete(0, builder.length());
                hashtagFlag = false;
                continue;
            }

            if (curChar == space && personTagFlag) {
                if (builder.length() > 1) {
                    personTagList.add(builder.toString());
                }
                builder.delete(0, builder.length());
                personTagFlag = false;
                continue;
            }

            if (curChar == hashtag) {
                hashtagFlag = true;
            }

            if (hashtagFlag) {
                builder.append(curChar);
            }

            if (curChar == personTag) {
                personTagFlag = true;
            }

            if (personTagFlag) {
                builder.append(curChar);
            }
        }
        return personTagList;
    }

}
