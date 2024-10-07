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
import com.muhrizram.grabprojectbe.models.olaps.OlapTransaction;
import com.muhrizram.grabprojectbe.repositories.olaps.OlapTransactionRepository;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    OlapTransactionRepository olapTransactionRepository;

    public ResponseEntity<ListResponse> getDataHistoryOrders(int page, int limit, String search,
            String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<OlapTransaction> historyOrders;

        if (search != null && !search.isEmpty()) {
            historyOrders = olapTransactionRepository.findBySearch(search, pageable);
        } else {
            historyOrders = olapTransactionRepository.findAll(pageable);
        }

        HttpStatus status = historyOrders.hasContent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = historyOrders.hasContent() ? "Successfully Get Histories" : "Histories Not Found";
        List<OlapTransaction> histories = historyOrders.getContent();
        Long totalData = historyOrders.getTotalElements();
        Integer totalPage = historyOrders.getTotalPages();

        ListResponse bodyResponse = ListResponse.builder()
                .statusCode(status.value())
                .status(status.name())
                .message(message)
                .data(histories)
                .totalData(totalData)
                .page(page)
                .totalPage(totalPage)
                .limit(limit)
                .build();

        return ResponseEntity.status(status).body(bodyResponse);
    }
}
