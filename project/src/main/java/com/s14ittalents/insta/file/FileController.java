package com.s14ittalents.insta.file;

import com.s14ittalents.insta.exception.DataNotFoundException;
import com.s14ittalents.insta.exception.FileException;
import com.s14ittalents.insta.util.AbstractController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.s14ittalents.insta.exception.Constant.FILE_DOES_NOT_EXIST;

@RestController
public class FileController extends AbstractController {


    @GetMapping("images/{filePath}")
    public void download(@PathVariable String filePath) {
        File f = new File("uploads" + File.separator + filePath);
        if (!f.exists()) {
            throw new DataNotFoundException(FILE_DOES_NOT_EXIST);
        }
        try {
            response.setContentType(Files.probeContentType(f.toPath()));
            Files.copy(f.toPath(), response.getOutputStream());
        } catch (IOException e) {
            throw new FileException("ups");
        }
    }

}
