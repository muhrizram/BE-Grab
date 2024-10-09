package com.muhrizram.grabprojectbe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.muhrizram.grabprojectbe.DTOs.responses.ListResponse;
import com.muhrizram.grabprojectbe.models.olaps.Menu;
import com.muhrizram.grabprojectbe.repositories.olaps.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    public ResponseEntity<ListResponse> getDataMenu(int page, int limit, String search,
            String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Menu> menu;

        if (search != null && !search.isEmpty()) {
            menu = menuRepository.findBySearch(search, pageable);
        } else {
            menu = menuRepository.findAll(pageable);
        }

        HttpStatus status = menu.hasContent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = menu.hasContent() ? "Successfully Get Menu" : "Menu Not Found";
        List<Menu> menuData = menu.getContent();
        Long totalData = menu.getTotalElements();
        Integer totalPage = menu.getTotalPages();

        ListResponse bodyResponse = ListResponse.builder()
                .statusCode(status.value())
                .status(status.name())
                .message(message)
                .data(menuData)
                .totalData(totalData)
                .page(page)
                .totalPage(totalPage)
                .limit(limit)
                .build();

        return ResponseEntity.status(status).body(bodyResponse);
    }
}
