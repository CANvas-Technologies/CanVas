package org.canvas.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UploadController {
    // Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "./uploads";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/upload")
    public String singleFileUpload(
            @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            // We use a UUID for the filename, which avoids collisions and mitigates malicious input
            String throwAwayUUID = UUID.randomUUID().toString();
            Files.createDirectories(Paths.get(UPLOADED_FOLDER));
            Path path = Paths.get(UPLOADED_FOLDER + "/" + throwAwayUUID);
            Files.write(path, bytes);

            // Process the file as a trace
            TraceHandle trace = null;

            try {
                trace = UploadHandler.HandleUpload(path);
            } catch (Throwable e) {
                e.printStackTrace();
                // return error
            }
            ;

            if (trace == null) {
                // return error
            }

            redirectAttributes.addFlashAttribute(
                    "message",
                    "You successfully uploaded '"
                            + file.getOriginalFilename()
                            + "' (UUID "
                            + trace.getUUIDString()
                            + ").");

        } catch (IOException e) {

            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
