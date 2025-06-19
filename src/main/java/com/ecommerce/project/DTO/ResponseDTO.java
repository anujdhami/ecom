package com.ecommerce.project.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    List<T> content;
    int pageNumber;
    int pageSize;
    long totalElements;
    boolean lastPage;
    long totalPages;
}
