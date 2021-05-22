package wooteco.subway.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.exception.RequiredParameterValidationException;
import wooteco.subway.dto.PathRequest;
import wooteco.subway.dto.PathResponse;
import wooteco.subway.service.PathService;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    ResponseEntity<PathResponse> findShortestPath(@ModelAttribute PathRequest pathRequest, BindingResult bindingResult) {
        validateRequestedParameter(bindingResult);

        long source = pathRequest.getSource();
        long target = pathRequest.getTarget();
        PathResponse pathResponse = pathService.findShortestPath(source, target);
        return ResponseEntity.ok(pathResponse);
    }

    private void validateRequestedParameter(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw RequiredParameterValidationException.from(bindingResult);
        }
    }
}
