package finalmission.controller;

import finalmission.annotation.CheckRole;
import finalmission.common.Role;
import finalmission.dto.request.CategoryRequest;
import finalmission.dto.response.CategoryResponse;
import finalmission.service.CategoryService;
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

@Tag(name = "카테고리 API", description = "카테고리 조회, 등록 API")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "전체 카테고리 목록 조회", description = "시스템의 모든 카테고리 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "신규 카테고리 등록 (관리자)", description = "새로운 카테고리를 시스템에 등록합니다. 관리자 권한이 필요합니다.")
    @CheckRole(Role.ADMIN)
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest category) {
        CategoryResponse categoryResponse = categoryService.createCategory(category);
        return ResponseEntity.created(URI.create("/categories/" + categoryResponse.id())).build();
    }

}