package com.s14ittalents.insta.exception;

import java.io.File;

public class Constant {
    public static long mb = 1024000;
    //1mb
    public static final String DATA_NOT_FOUND = "Data not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String REMOTE_IP = "REMOTE_IP";
    public static final String POST_NOT_FOUND = "Post not found";
    public static final String PERMISSION_DENIED = "Permission denied";
    public static final String FILE_DOES_NOT_EXIST = "File does not exist";
    public static final String YOU_CAN_ONLY_CHOOSE_10_OR_FEWER_FILES = "You can only choose 10 or fewer files";
    public static final String MAX_SIZE_PER_FILE_IS_10_MB = "Max size per file is 10mb";
    public static final String THE_FILE_ALREADY_EXISTS = "The file already exists";
    public static final int MAX_ALLOWED_FILES_TO_UPLOAD = 10;
    public static final String COMMENT_NOT_FOUND = "Comment not found";
    public static final String CAN_NOT_REPLY_YOURSELF = "Can not reply yourself";
    
    public static final String PATH_TO_STATIC =
            "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator;
    
    public static final String DEFAULT_PROFILE_PICTURE =
            PATH_TO_STATIC + "default_profile_picture" + File.separator + "default_profile_picture.jpg";
}
