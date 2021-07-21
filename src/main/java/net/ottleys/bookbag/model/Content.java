package net.ottleys.bookbag.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.util.UUID;

import net.ottleys.bookbag.utils.FileUtils;

public class Content 
{
    private UUID id ;
    private String name;
    private String mimetype;
    private String path;
    private String checksum;
    private long size;
    private FileTime created;
    private FileTime modified;

    public Content(UUID id, String name, String path, String checksum, long size, FileTime created, FileTime modified){
        this.id = id;
        this.name = name;
        this.path = path;
        this.checksum = checksum;
        this.size = size;
        this.created = created;
        this.modified = modified;
    }

    /**
     * @param id
     * @param path
     * @throws IOException
     */
    public Content(UUID id, Path path) throws IOException {
        this.id = id;
        this.name = path.toFile().getName();
        this.mimetype = FileUtils.getMimetype(path.toFile());//Files.probeContentType(path);
        this.path = path.toString();
        this.size = path.toFile().length();
        this.created = (FileTime) Files.getAttribute(path, "creationTime");
        this.modified = Files.getLastModifiedTime(path);
    }

    /**
     * @param path
     * @throws IOException
     */
   /* public Content(Path path) throws IOException {
        this.name = path.toFile().getName();
        this.mimetype = FileUtils.getMimetype(path.toFile());//Files.probeContentType(path);
        this.path = path.toString();
        this.size = path.toFile().length();
        this.created = (FileTime) Files.getAttribute(path, "creationTime");
        this.modified = Files.getLastModifiedTime(path);
    }*/

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID setId() {
        this.id = UUID.randomUUID();
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getPath() {
        return path;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

}