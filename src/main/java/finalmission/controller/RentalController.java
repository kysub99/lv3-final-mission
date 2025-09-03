package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.common.Role;
import finalmission.dto.request.RentalRequest;
import finalmission.dto.response.RentalResponse;
import finalmission.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "대여 API", description = "도서 대여 및 전체 대여 내역 조회 API")
@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "전체 대여 내역 조회 (관리자)", description = "시스템의 모든 대여 기록을 조회합니다. 관리자 권한이 필요합니다.")
    @CheckRole(Role.ADMIN)
    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getRentals());
    }

    @Operation(summary = "도서 대여", description = "사용자가 특정 도서를 대여합니다.")
    @PostMapping
    public ResponseEntity<RentalResponse> createRental(@RequestBody RentalRequest rentalRequest) {
        RentalResponse rental = rentalService.createRental(rentalRequest);
        return ResponseEntity.created(URI.create("/rentals/" + rental.id())).build();
    }
}