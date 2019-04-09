package com.example.assessment.controller;

import com.example.assessment.dto.stock.OrderDTO;
import com.example.assessment.dto.stock.StockDTO;
import com.example.assessment.service.StockService;
import com.example.assessment.service.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@Api(tags = "stocks")
public class StockController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/myPortfolio")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Return's the current user's portfolio.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 403, message = "Expired or invalid JWT token"),//
            @ApiResponse(code = 404, message = "The user doesn't exist") })
    public List<StockDTO> getMyPortfolio(HttpServletRequest req) {
        return stockService.getPortfolio(userService.whoIs(req)).stream().map(stock -> modelMapper.map(stock, StockDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/buy")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Buy a stock, returns true on success.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 403, message = "Expired or invalid JWT token"),//
            @ApiResponse(code = 404, message = "The user doesn't exist"),//
            @ApiResponse(code = 422, message = "Insufficient Balance" )})
    public boolean buyStock(HttpServletRequest req, @ApiParam("Order") @RequestBody OrderDTO order) {
        return stockService.buyStock(userService.whoIs(req), order);
    }

    @PostMapping("/sell")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Sell a stock, returns true on success.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 403, message = "Expired or invalid JWT token"),//
            @ApiResponse(code = 404, message = "The user doesn't exist"),//
            @ApiResponse(code = 422, message = "Insufficient Shares" )})
    public boolean sellStock(HttpServletRequest req, @ApiParam("Order") @RequestBody OrderDTO order) {
        return stockService.sellStock(userService.whoIs(req), order);
    }


}
