package com.wan.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.dto.UserPageQueryDTO;
import com.wan.entity.User;
import com.wan.mapper.ManagerMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.server.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());
        Page<User> pages = managerMapper.pageQuery(userPageQueryDTO);
        long total = pages.getTotal();
        return PageResult.builder()
                .total(total)
                .data(pages.getResult())
                .build();
    }


    /**
     * 启用和禁用用户账号
     * @param accountStatus
     * @param id
     */
    @Override
    public void startOrStop(Integer accountStatus, Long id) {
        User user = User.builder()
                .accountStatus(accountStatus)
                .id(id)
                .build();
        userMapper.update(user);
    }

    @Override
    public void patchDelete(List<Long> ids) {
        userMapper.deleteByIds(ids);
    }
}
