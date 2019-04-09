package com.example.assesment.controller;

import com.example.assesment.dto.stock.StockDTO;
import com.example.assesment.service.StockService;
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
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public List<StockDTO> getMyPortfolio(HttpServletRequest req) {
        return stockService.getPortfolio(userService.whoIs(req))
                .stream().map(stock -> modelMapper.map(stock, StockDTO.class))
                .collect(Collectors.toList());
    }


}
