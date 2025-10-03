package fast.retire.integration.application.fxrates;

import fast.retire.integration.api.fxrates.FxRateFetcher;
import fast.retire.integration.api.fxrates.FxRateRequest;
import fast.retire.integration.api.fxrates.FxRateResponse;
import fast.retire.integration.api.fxrates.FxRateSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integration/fxrates")
@Log4j2
@RequiredArgsConstructor
public class FxRateController {

    private final FxRateFetcher fxRateFetcher;
    private final FxRateSaver fxRateSaver;


    @PostMapping
    public void synchronize(@RequestBody FxRateRequest request) throws Exception {
        FxRateResponse response = fxRateFetcher.fetch(request);
        fxRateSaver.save(response);
    }

}