package net.ottleys.bookbag.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import net.ottleys.bookbag.model.Content;

@Service
public class ScannerService {
    private Logger logger = LoggerFactory.getLogger(ScannerService.class);

    @Value("${stacks.root}")
    public String stacksroot;

    private Integer contentCount = 0;
    private Integer folderCount = 0;
    private boolean scanning = false;

    @Autowired
    ContentService contentService;

    @Autowired
    FileService fileService;

    @Autowired
    ExclusionService exclusionService;

    ConcurrentMap<UUID, File> all = new ConcurrentHashMap<>();

    @Async
    public void scan() throws IOException, NoSuchAlgorithmException, TikaException, SAXException {
        scanning = true;

        try {
            if (stacksroot != null) {
                Path root = convertToPath(stacksroot);
                logger.debug("Root to scan: {}", root);

                logger.info("Scan starting...");
                scanDirectory(root);
                logger.info("...Scan complete");
                logger.info("Folders Found: {}, Content Found: {}", folderCount, contentCount);

                //contentService.generateCheckSum(all.stream().collect(toList()));
            }
        } finally {
            scanning = false;
        }
    }

    @Async
    public void scan(String path) throws IOException, NoSuchAlgorithmException, TikaException, SAXException {
        scanning = true;

        try {
            if (stacksroot != null) {
                Path root = convertToPath(path);
                logger.debug("Path to scan: {}", root);

                logger.info("Scan starting...");
                scanDirectory(root);
                logger.info("...Scan complete");
                logger.info("Folders Found: {}, Content Found: {}",folderCount, contentCount);

                //contentService.generateCheckSum(all.stream().collect(toList()));
            }
        } finally {

            scanning = false;
        }
    }

    protected void scanDirectory(Path path) throws IOException, NoSuchAlgorithmException, TikaException, SAXException {
        if (path != null) {
            logger.info("Scanning {}", path);
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
                for (Path child : ds) {
                    if (Files.isRegularFile(child)) {
                        logger.info("File found: {}", getRelativePath(child));
                        contentCount++;
                        process(child);
                    }
                    else if (Files.isDirectory(child))
                    {
                        logger.info("Directory found: {}", getRelativePath(child));
                        folderCount++;
                        scanDirectory(child);
                    }

                }
            }
        }
    }

    private void process(Path path) throws NoSuchAlgorithmException, IOException, TikaException, SAXException {

        if (Files.isRegularFile(path)) {

            if (!exclusionService.isExcluded(path.toFile()))
            {
                //Content content = contentService.convertToContent(path);
                //contentService.getMetadata(content);
                all.put(UUID.randomUUID(), path.toFile());
                //logger.info("Content added: {}", content.checksum);
                logger.info("Content added");
            }
            else {
                logger.info("Content excluded: {}", path.getFileName());
            }
        }
        else {
            logger.info("Directory skipped.");
        }
        
    }

    private Path convertToPath(String path) {
        if (path != null) {
            return Paths.get(path);
        }

        return null;
    }

    private String getRelativePath(Path path) {
        if (path != null) {
            String working = path.toString();
            return working.replace(stacksroot, "");
        }
        return null;
    }

    public boolean isScanning() {
        return scanning;
    }

    public Path getStacksRootPath(){
        return convertToPath(stacksroot);
    }

    public boolean validateStakcsRoot() {
        return getStacksRootPath().toFile().exists();
    }
}