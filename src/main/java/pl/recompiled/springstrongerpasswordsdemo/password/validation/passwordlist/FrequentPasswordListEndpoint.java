package pl.recompiled.springstrongerpasswordsdemo.password.validation.passwordlist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    private final InternalFrequentPasswordListAdapter passwordListAdapter;

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(OK)
    public void uploadPasswordList(@RequestParam MultipartFile file) throws IOException {
        final InputStream inputStream = file.getInputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            passwordListAdapter.addPassword(line);
        }
    }
}
