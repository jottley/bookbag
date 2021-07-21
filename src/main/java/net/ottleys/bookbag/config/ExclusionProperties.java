package net.ottleys.bookbag.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ExclusionProperties
 */
@Configuration
@ConfigurationProperties(prefix = "exclusions.content")
public class ExclusionProperties
{
    private List<String> extension;
    private List<String> mimetype;
    private List<String> name;
    private List<String> startsWith;
    private List<String> contains;
    private List<String> endsWith;

    public List<String> getExtension()
    {
        return extension;
    }

    public List<String> getMimetype()
    {
        return  mimetype;
    }

    public List<String> getName()
    {
        return name;
    }

    public List<String> getStartsWith()
    {
        return startsWith;
    }

    public List<String> getContains()
    {
        return contains;
    }

    public List<String> getEndsWith()
    {
        return endsWith;
    }

    public void setExtension(List<String> extension)
    {
        this.extension = extension;
    }

    public void setMimetype(List<String> mimetype)
    {
        this.mimetype = mimetype;
    }

    public void setName(List<String> name)
    {
        this.name = name;
    }

    public void setStartsWith(List<String> startsWith)
    {
        this.startsWith = startsWith;
    }

    public void setContains(List<String> contains)
    {
        this.contains = contains;
    }

    public void setEndsWith(List<String> endsWith)
    {
        this.endsWith = endsWith;
    }
}