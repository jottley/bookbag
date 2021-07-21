package net.ottleys.bookbag.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import static com.google.common.io.Files.asByteSource;
import static com.google.common.io.Files.getFileExtension;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import net.ottleys.bookbag.model.Content;

@Service
public class ContentService {
    private Logger logger = LoggerFactory.getLogger(ContentService.class);

    private static final HashFunction MURMUR3_128 = Hashing.murmur3_128();


    @Autowired
    TikaConfig tikaConfiguration;

    public String getContentExtension(Content content) {
        return getFileExtension(content.getName());
    }

    /*public Content convertToContent(Path path) throws IOException {
        return new Content(path);
    }*/

    public File getFile(Content content) {
        return new File(content.getPath());

    }

    public String getMimetype(Content content) throws IOException {
        return Files.probeContentType(Paths.get(content.getPath()));
    }


    public Metadata getMetadata(Content content) throws IOException, TikaException, SAXException {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

        Parser parser = new AutoDetectParser(tikaConfiguration);// TODO make spring bean
        BodyContentHandler handler = new BodyContentHandler(-1);// TODO make spring bean
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(getFile(content));
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);

        // getting the list of all meta data elements
        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            logger.info("{}: {}", name, metadata.get(name));
        }

        return metadata;
    }

    /*public Metadata getMetadata(Path path) throws IOException, TikaException, SAXException {
        return getMetadata(new Content(path));
    }*/


    @Async
    public CompletableFuture<List<Content>> generateCheckSum(List<Content> contentList) {
        final long start = System.currentTimeMillis();
        logger.info("Start checksum generation: {}", start);

        for (Content content : contentList) {
            try {
                content.setChecksum(getCheckSum(Paths.get(content.getPath())));
                logger.info("Content: {}, Checksum {}", content.getName(), content.getChecksum());

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        logger.info("Time elapsed: {}", (System.currentTimeMillis() - start));
        return CompletableFuture.completedFuture(contentList);
    }

    public String getCheckSum(Path content) throws IOException {
        HashCode hash = asByteSource(new File(content.toString())).hash(MURMUR3_128);
        return hash.toString().toUpperCase();
    }

    public boolean contentExists(Content content) {
        return Paths.get(content.getPath()).toFile().exists();
    }

    /*public boolean contentExists(Path path) throws IOException {
        return contentExists(new Content(path));
    }*/

}