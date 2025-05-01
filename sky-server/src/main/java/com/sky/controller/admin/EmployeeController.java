package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "Employee related APIs")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "Employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "Employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("Add new employee")
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("New employee {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     *
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Paging query")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("Page query {}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Enable or disable the selected employee
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Enable or disable employee")
    public Result enableOrDisable(@PathVariable Integer status, Long id) {
        log.info("Enable or disable employee {}", id);
        employeeService.enableOrDisable(status, id);
        return Result.success();
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Get employee properties by ID")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("Get employee {}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("Update employee info")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Update employee {}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
































}
