package com.furkanozdemir.store.rest;

import com.furkanozdemir.common.VoidResponseUseCaseHandler;
import com.furkanozdemir.store.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final VoidResponseUseCaseHandler<Set<Store>> storeGetAllVoidUseCaseHandler;

    @GetMapping(path = "/all")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Set<Store>> getAllStore() {

        var response = storeGetAllVoidUseCaseHandler.handle();
        return ResponseEntity.ok(response);
    }

}
