package wooteco.subway.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    ResponseEntity<PathResponse> findShortestPath(@RequestParam("source") long source, @RequestParam("target") long target) {
        PathResponse pathResponse = pathService.findShortestPath(source, target);
        return ResponseEntity.ok(pathResponse);
    }
}
