package com.s14ittalents.insta.exception;

import java.io.File;
import java.time.LocalDateTime;

public class Constant {
    public static long mb = 1024000;
    //1mb
    public static final String DATA_NOT_FOUND = "Data not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String REMOTE_IP = "REMOTE_IP";
    public static final String POST_NOT_FOUND = "Post not found";
    public static final String STORY_NOT_FOUND = "Story not found";
    public static final String PERMISSION_DENIED = "Permission denied";
    public static final String FILE_DOES_NOT_EXIST = "File does not exist";
    public static final String YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES = "You can only choose 10 or fewer files";
    public static final String MAX_SIZE_PER_FILE_IS_MB = "Max size per file is %dmb";
    public static final String THE_FILE_ALREADY_EXISTS = "The file already exists";
    public static final int MAX_ALLOWED_FILES_TO_UPLOAD = 10;
    public static final String COMMENT_NOT_FOUND = "Comment not found";
    public static final String CAN_NOT_REPLY_YOURSELF = "Can not reply to yourself";


    public static final String CONTENT_NOT_FOUND = "Content not found";
    
    public static final String PATH_TO_STATIC =
            "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator;
    
    public static final String DEFAULT_PROFILE_PICTURE =
            PATH_TO_STATIC + "default_profile_picture" + File.separator + "default_profile_picture.jpg";

    public static final String YOU_ALREADY_REPLY_THIS_COMMENT = "You already replied to this comment";
    public static final String WRONG_CREDENTIALS = "Wrong credentials!";

    public static final String FILE_SHOULD_NOT_BE_EMPTY = "File should not be empty";
    
    public static final String REPLACE_IN_DELETED = ("del-" + LocalDateTime.now()).substring(0, 30);
    public static final String SQL_TO_COUNT_ALL_FROM_SELECTION_FOR_FEED =
                    "SELECT \n" + "    COUNT(*)\n"
                    + "FROM\n"
                    + "    users AS u\n" 
                    + "        LEFT JOIN\n"
                    + "    following "
                    + "AS f ON (u.id = f.user_id)\n"
                    + "        LEFT JOIN\n"
                    + "    post AS p ON (p.user_id = u.id)\n"
                    + "        LEFT JOIN\n"
                    + "    locations AS l ON (p.location_id = l.id)\n"
                    + "WHERE\n"
                    + "    f.follower_id = ? \n"
                    + "        AND u.is_banned = '0'\n"
                    + "        AND u.is_deactivated = '0'\n"
                    + "        AND p.expiration_time IS NULL\n"
                    + "        AND p.is_deleted = '0'\n";
}
