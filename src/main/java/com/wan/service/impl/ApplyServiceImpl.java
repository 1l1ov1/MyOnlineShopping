package com.wan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wan.constant.ApplyConstant;
import com.wan.constant.MessageConstant;
import com.wan.dto.ApplyDTO;
import com.wan.entity.*;
import com.wan.exception.*;
import com.wan.mapper.ApplyMapper;
import com.wan.mapper.StoreMapper;
import com.wan.mapper.UserMapper;
import com.wan.result.PageResult;
import com.wan.result.ValidationResult;
import com.wan.service.ApplyService;
import com.wan.vo.ApplyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StoreMapper storeMapper;

    @Override
    @Transactional()
    public void addApply(ApplyDTO applyDTO) {
        // 判断是否已经发送请求但还没通过了或者已经通过的
        Apply apply = applyMapper.findApprovedOrUnderApply(applyDTO.getUsername());
        // 如果还在审核或者审核通过
        if (apply != null) {
            throw new ApplyException(MessageConstant.APPLY_IS_EXIST);
        }
        // 如果没有发送过或者审核被拒绝过
        // 添加申请
        applyDTO.setStatus(ApplyConstant.UNDER_REVIEW);
        // 由于事务的原因当插入地址的时候，数据库中还没有apply数据
        // 所以需要修改事务的级别。
        applyDTO.setPassword(getMD5Password(applyDTO.getPassword()));
        applyMapper.insertApply(applyDTO);
        Long id = applyDTO.getId();
        if (id != null) {
            applyMapper.insertApplyAddress(applyDTO.getAddress(), id);
        } else {
            throw new ApplyException(MessageConstant.APPLY_IS_NOT_EXIST);
        }
    }

    @Override
    public void checkApply(ApplyDTO applyDTO) {
        ValidationResult valid = isValid(applyDTO);
        // 如果有异常
        if (valid.getExceptionsErrors().size() > 0) {
            // 抛出去
            throw valid.getExceptionsErrors().get(0);
        }
    }

    /**
     * 查询用户的申请是否通过
     *
     * @param username
     * @return
     */
    @Override
    public Apply findApply(String username) {
        if (username == null || "".equals(username)) {
            throw new AccountNotFountException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 先根据用户名查询所有的申请
        List<Apply> allApplies = applyMapper.findUserAllApplies(username);
        if (allApplies == null || allApplies.size() == 0) {
            // 如果没有发送过申请
            return null;
        }
        // 如果发送过申请
        return applyMapper.findApprovedOrUnderApply(username);
    }


    /**
     * 分页查询
     *
     * @param applyDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ApplyDTO applyDTO) {
        // PageHelper.startPage(applyDTO.getPage(), applyDTO.getPageSize());
        // Page<ApplyVO> page = applyMapper.pageQuery(applyDTO);
        // return PageResult.builder()
        //         .total(page.getTotal())
        //         .data(page.getResult())
        //         .build();

        return applyDTO.executePageQuery(applyMapper::pageQuery, applyDTO);
    }

    /**
     * 修改申请
     *
     * @param applyDTO
     */
    @Override
    @Transactional
    public void update(ApplyDTO applyDTO) {
        Integer status = applyDTO.getStatus();
        if (applyDTO.getId() == null) {
            throw new OrdersException(MessageConstant.APPLY_IS_NOT_EXIST);
        }

        if (status == null
                || status > 2
                || status < 0) {
            throw new OrdersException(MessageConstant.APPLY_STATUS_IS_WRONG);
        }
        Store store = storeMapper.findStoreByStoreName(applyDTO.getStoreName());
        if (store != null) {
            throw new StoreException(MessageConstant.STORE_EXIST);
        }

        // 修改申请
        applyMapper.updateApply(applyDTO);
    }


    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void batchDeleteApply(List<Long> ids) {
        // 检查传入的ID列表是否为空
        if (ids == null || ids.isEmpty()) {
            throw new ApplyException(MessageConstant.APPLY_IS_NOT_EXIST);
        }
        // 查找订单，确保所有ID都存在
        List<Apply> applyList = applyMapper.findAppliesById(ids);
        if (applyList == null || applyList.size() != ids.size()) {
            throw new ApplyException(MessageConstant.APPLY_IS_NOT_EXIST);
        }
        // 批量删除订单
        applyMapper.batchDeleteApplies(ids);
    }

    /**
     * 得到申请的信息
     *
     * @param id
     * @return
     */
    @Override
    public ApplyVO getDetail(Long id) {
        if (id == null) {
            throw new ApplyException(MessageConstant.APPLY_IS_NOT_EXIST);
        }
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<Apply> applies = applyMapper.findAppliesById(ids);
        if (applies == null || applies.size() == 0) {
            throw new ApplyException(MessageConstant.APPLY_IS_NOT_EXIST);
        }

        ApplyVO applyVO = new ApplyVO();
        BeanUtils.copyProperties(applies.get(0), applyVO);
        return applyVO;
    }

    private ValidationResult isValid(ApplyDTO applyDTO) {
        ValidationResult result = new ValidationResult();

        // Validate user
        User user = userMapper.getByUsername(applyDTO.getUsername());
        if (user == null) {
            result.addErrorException(new AccountExistException(MessageConstant.ACCOUNT_NOT_FOUND));
        } else if (!user.getPassword().equals(getMD5Password(applyDTO.getPassword()))) {
            result.addErrorException(new PasswordErrorException(MessageConstant.PASSWORD_ERROR));
        }

        // Validate store
        Store store = storeMapper.findStoreByStoreName(applyDTO.getStoreName());
        if (store != null) {
            result.addErrorException(new StoreException(MessageConstant.STORE_EXIST));
        }
        // Validate address
        if (!checkAddressNotEmpty(applyDTO.getAddress())) {
            result.addErrorException(new StoreException(MessageConstant.STORE_ADDRESS_IS_NOT_ALLOWED_TO_BE_EMPTY));
        }

        return result;
    }

    private String getMD5Password(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    private boolean checkAddressNotEmpty(Address address) {
        if (address == null) {
            return false;
        }
        String provinceCode = address.getProvinceCode();
        String provinceName = address.getProvinceName();
        String cityCode = address.getCityCode();
        String cityName = address.getCityName();
        String districtCode = address.getDistrictCode();
        String districtName = address.getDistrictName();

        return provinceCode != null && provinceName != null
                && cityCode != null && cityName != null
                && districtCode != null && districtName != null;
    }

}
