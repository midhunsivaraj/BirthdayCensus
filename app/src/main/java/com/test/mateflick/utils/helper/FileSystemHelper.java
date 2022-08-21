package com.test.mateflick.utils.helper;


import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class FileSystemHelper {

    public static final String BASE_DIR = "BD";
    public static final String IMAGES_FOLDER = "Images";
    public static final String COVER_IMAGE = "cover.jpg";
    public static final String PROFILE_PIC = "profile.jpg";

    private static boolean createFolderIfNotExists() {
        boolean exists;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File baseDir = new File(Environment.getExternalStorageDirectory()
                    + File.separator
                    + BASE_DIR
                    + File.separator
                    + IMAGES_FOLDER);
            exists = checkFolderExists(baseDir) || baseDir.mkdirs();
        } else {
            exists = false;
        }
        return exists;
    }

    public static boolean checkFolderExists(File path) {
        return path.exists();
    }


    /**
     * Copies profile pic and cover pic to our directory,
     * Will be useful if the user deleted the original image after the picture is set
     *
     * @param sourcePath the source image
     * @param destName   destination file name,
     *                   (should  be either {@link #PROFILE_PIC} or {@link #COVER_IMAGE} )
     */
    public static void copyImage(File sourcePath, String destName) throws IOException {
        //return if the destination folder does not exists
        if (!createFolderIfNotExists()) return;

        FileChannel source;
        FileChannel dest;

        //Create a copy of the image
        File destinationFile = new File(Environment.getExternalStorageDirectory()
                + File.separator
                + BASE_DIR
                + File.separator
                + IMAGES_FOLDER
                + File.separator
                + destName);

        FileInputStream sourceOutPutStream = new FileInputStream(sourcePath);
        FileOutputStream destOutPutStream = new FileOutputStream(destinationFile);

        source = sourceOutPutStream.getChannel();
        dest = destOutPutStream.getChannel();

        // copy the created file to the destination folder
        dest.transferFrom(source, 0, source.size());

        //close all the streams
        sourceOutPutStream.close();
        destOutPutStream.close();

    }

}
