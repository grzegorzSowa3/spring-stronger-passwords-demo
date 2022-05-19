package pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/frequent-passwords")
@RequiredArgsConstructor
class FrequentPasswordListEndpoint {

    private final InternalFrequentPasswordListAdapter service;

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(OK)
    public void uploadPasswordList(@RequestBody MultipartFile file) throws IOException {
        final InputStream inputStream = file.getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int added = 0;
        int browsed = 0;
        while ((line = reader.readLine()) != null) {
            browsed = browsed + 1;
            final Boolean passAdded = service.addPassword(line);
            if (passAdded) {
                System.out.println("Browsed: " + browsed + ", Added: " + ++added + ", Password: " + line);
            }
        }
    }
}
