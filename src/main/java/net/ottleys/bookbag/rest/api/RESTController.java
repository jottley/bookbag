package net.ottleys.bookbag.rest.api;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import net.ottleys.bookbag.services.ScannerService;
import net.ottleys.bookbag.services.ContentService;
import net.ottleys.bookbag.services.UnknownContentPathException;

@RestController
public class RESTController {

    private Logger logger = LoggerFactory.getLogger(RESTController.class);

    @Autowired
    ScannerService scannerService;


    @RequestMapping("/scan")
    public String scan() {
        try {
            if (scannerService.validateStakcsRoot()) {
                if (!scannerService.isScanning()) {
                    scannerService.scan();
                } else {
                    return "scanning";
                }
            }
            else {
                throw new UnknownContentPathException(scannerService.getStacksRootPath().toString());
            }
        } catch (UnknownContentPathException | IOException | NoSuchAlgorithmException | TikaException | SAXException e) {
            if (logger.isDebugEnabled())
            {
                e.printStackTrace();
            }
            e.printStackTrace();
            return e.getMessage();
        }

        return "index";
    }
}