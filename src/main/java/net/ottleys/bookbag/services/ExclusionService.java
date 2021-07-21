package net.ottleys.bookbag.services;

import java.io.File;

import com.google.common.io.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ottleys.bookbag.utils.FileUtils;
import net.ottleys.bookbag.config.ExclusionProperties;

@Service
public class ExclusionService {

    @Autowired
    ExclusionProperties exclusionProperties;

    public boolean isDirectory(File file) {
        return file.isDirectory();
    }

    public boolean isFile(File file) {
        return file.isFile();
    }

    public boolean isExtensionExcluded(String extension) {
        boolean excluded = false;

        if (exclusionProperties.getExtension() != null
            && exclusionProperties.getExtension().contains(extension)) {
                excluded = true;
            }

        return excluded;
    }

    public boolean isMimetypeExcluded(String mimetype) {
        boolean excluded = false;

        if (exclusionProperties.getMimetype() != null
            && exclusionProperties.getMimetype().contains(mimetype)) {
                excluded = true;
            }

        return excluded;
    }

    public boolean isNameExcluded(String name) {
        boolean excluded = false;

        if (exclusionProperties.getName() != null
            && exclusionProperties.getName().contains(name)) {
                excluded = true;
            }

        return excluded;
    }

    public boolean isPrefixExcluded(String name) {
        boolean excluded = false;

        if (exclusionProperties.getStartsWith() != null)
        {
            for (String prefix : exclusionProperties.getStartsWith())
            {
                if (name.startsWith(prefix))
                {
                    excluded = true;
                    break;
                }
            }
        }

        return excluded;
    }

    public boolean isStringExcluded(String name) {
        boolean excluded = false;

        if (exclusionProperties.getContains() != null)
        {
            for (String string : exclusionProperties.getContains())
            {
                if (name.contains(string))
                {
                    excluded = true;
                }
            }
        }

        return excluded;
    }

    public boolean isSuffixExcluded(String name) {
        boolean excluded = false;

        if (exclusionProperties.getEndsWith() != null)
        {
            for (String suffix : exclusionProperties.getEndsWith())
            {
                if (name.endsWith(suffix))
                {
                    excluded = true;
                }
            }
        }

        return excluded;
    }

    public boolean isExcluded(File file) {
        boolean isExcluded = false;

        if (isExtensionExcluded(Files.getFileExtension(file.getName()))
            || isMimetypeExcluded(FileUtils.getMimetype(file))
            || isNameExcluded(file.getName())
            || isPrefixExcluded(file.getName())
            || isStringExcluded(file.getName())
            || isSuffixExcluded(file.getName())) {
            isExcluded = true;
        }

        return isExcluded;

    }

    /*public boolean isExcluded(Content content) {

    }*/

    /*public boolean isExcluded(Stack stack) {

    }*/

    
    
}
