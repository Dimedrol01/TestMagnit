package application.model;

import java.io.File;
import java.nio.file.Paths;

public abstract class Constants {
    private static final String ORIGINAL_FILE = "1.xml";
    public static final String XSLT_FILE = "custom_style_for_first_file.xsl";
    private static final String RESULT_FILE = "2.xml";
    public static final String ROOT_ELEMENT = "entries";
    public static final String TEXT_FILE_XSLT = "<?xml version=\"1.0\"?><xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\"><xsl:template match=\"entries\"><entries><xsl:apply-templates/></entries></xsl:template><xsl:template match=\"entry\"><entry field=\"{field}\"/></xsl:template></xsl:stylesheet>";
    public static final String FIELD = "field";
    public static final String ENTRY = "entry";
    public static final String WORK_DIR_ORIGINAL_FILE = Paths.get("").toAbsolutePath().toString() + File.separator + ORIGINAL_FILE;
    public static final String WORK_DIR_RESULT_FILE = Paths.get("").toAbsolutePath().toString() + File.separator + RESULT_FILE;
    public static final String WORK_DIR_XSLT_FILE = Paths.get("").toAbsolutePath().toString() + File.separator + XSLT_FILE;
}
