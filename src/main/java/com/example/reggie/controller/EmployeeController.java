package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie.common.R;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author yangyibufeng
 * @date 2023/4/21
 */
@Slf4j
@RestController //@RestController 是一个 Spring Boot 的注解，用于标识一个类是 RESTful Web 服务的控制器，
                // 它的作用相当于同时使用了 @Controller 和 @ResponseBody 注解。
                //具体来说，@Controller 用于标识一个类是控制器，它通常用于处理视图逻辑，即返回一个视图（如 JSP 页面）。
                // 而 @ResponseBody 用于标识一个方法返回的是数据，而不是一个视图。
                // 使用了 @RestController 注解后，控制器中的所有方法都默认返回数据，而不是视图。
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * @param request:  如果登录成功，会将相应的用户信息通过request存入到session方便后期读取
     * @param employee: 注释@RequestBody可以将前端返回回来的json数据直接封装成为相对应的实体类
     * @return com.yangyi.reggie.common.R<com.yangyi.reggie.entity.Employee>:
     * @author yangyibufeng
     * @description 员工登录
     * @date 2023/4/21 17:42
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
//        1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

//        3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登陆失败，没有该用户的信息");
        }

//        4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登陆失败，密码错误");
        }

//        5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("登录失败，该账号已被禁用");
        }

//        6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * @param request:
     * @return com.yangyi.reggie.common.R<java.lang.String>:
     * @author yangyibufeng
     * @description 员工退出
     * @date 2023/4/21 18:50
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("成功退出");
    }

    /**
     * @param employee:
     * @description 新增员工
     * @date 2023/4/22 19:49
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工信息：{}",employee.toString());
        //设置初始密码123456,需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        //employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);

    return R.success("新增员工成功");
    }

    /**
     * 员工信息的分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,lambdaQueryWrapper);


        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        //employee.setUpdateTime(LocalDateTime.now());
        Object employee1 = request.getSession().getAttribute("employee");
        employee.setUpdateUser((Long) employee1);
        //employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据员工Id来查询数据库...");
        Employee employee=employeeService.getById(id);
        return R.success(employee);
    }
}