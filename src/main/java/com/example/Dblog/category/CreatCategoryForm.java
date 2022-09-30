package com.example.Dblog.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreatCategoryForm {
    @NotEmpty(message = "카테고리 명은 필수입니다.")
    private String name;

    @NotEmpty(message = "유저 ID는 필수입니다.")
    private Long userid;
}
