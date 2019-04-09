package com.example.assesment.controller;

import com.example.assesment.dto.transaction.TransactionDTO;
import com.example.assesment.service.TransactionService;
import com.example.assesment.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@Api(tags = "transactions")
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService txnService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/myTransactions")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Return's all of the current user's transactions.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 403, message = "Expired or invalid JWT token"),//
            @ApiResponse(code = 404, message = "The user doesn't exist") })
    public List<TransactionDTO> getMyTransactions(HttpServletRequest req) {
        return txnService.getAllTransactions(userService.whoIs(req))
                .stream().map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .collect(Collectors.toList());
    }


}
