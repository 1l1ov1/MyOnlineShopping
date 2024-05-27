package com.wan.dto;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.result.PageResult;
import lombok.Data;

import java.util.function.Function;

@Data
public class PageDTO {
    private Integer page = 1;
    private Integer pageSize = 10;

    private Integer sort = 0; // 1 升序 0 降序

    /**
     * 执行分页查询的方法。此方法通过提供的分页查询接口和目标实体，执行分页查询操作，并返回分页查询结果。
     *
     * @param function 分页查询接口，用于执行具体的分页查询操作。该函数接收一个目标实体作为参数，返回查询结果。
     * @param target     分页查询的目标实体对象。根据目标实体，调用函数进行具体地查询操作。
     * @param <R>        查询结果的数据类型。泛型R表示查询结果的类型，可根据实际查询需求进行指定。
     * @param <E>        分页查询目标实体的数据类型。泛型E表示进行分页查询的目标实体类型，可根据实际需求进行指定。
     * @return 返回分页查询结果，包含总记录数和查询到的数据列表。返回的分页结果包含了本次查询的总记录数和实际查询到的数据列表。
     */
    public <R, E> PageResult executePageQuery(Function<E, R> function, E target) {
        // 开始分页，设置当前页和每页的记录数。利用PageHelper进行分页的初始化配置。
        PageHelper.startPage(page, pageSize);
        // 执行分页查询，应用提供的函数进行具体的分页查询操作。
        Page<R> page = (Page<R>) function.apply(target);

        // 构建并返回分页查询结果。利用PageResult的builder模式设置总记录数和查询数据，构建并返回分页查询结果对象。
        return PageResult.builder()
                .total(page.getTotal()) // 设置总记录数
                .data(page.getResult()) // 设置查询到的数据
                .build();
    }

}
