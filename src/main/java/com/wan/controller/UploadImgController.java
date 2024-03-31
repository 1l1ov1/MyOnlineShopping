package com.wan.controller;

import com.wan.constant.MessageConstant;
import com.wan.context.ThreadBaseContext;
import com.wan.dto.GoodsPageQueryDTO;
import com.wan.entity.Store;
import com.wan.entity.User;
import com.wan.exception.GoodsException;
import com.wan.exception.StoreException;
import com.wan.result.Result;
import com.wan.service.GoodsService;
import com.wan.service.StoreService;
import com.wan.service.UserService;
import com.wan.vo.GoodsPageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/img")
@Slf4j
public class UploadImgController {
    private final String AVATAR_STORAGE_ADDRESS = "C:\\Users\\123\\Desktop\\MyOnlineShopping\\vue-onlineshopping\\src\\assets\\uploadAvatar\\";
    private final String GOODS_STORAGE_ADDRESS = "C:\\Users\\123\\Desktop\\MyOnlineShopping\\vue-onlineshopping\\src\\assets\\uploadGoods\\";

    private final String STORE_STORAGE_ADDRESS = "C:\\Users\\123\\Desktop\\MyOnlineShopping\\vue-onlineshopping\\src\\assets\\uploadStore\\";
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private StoreService storeService;

    @PostMapping("/uploadAvatar")
    public Result<String> upload(@RequestParam(value = "file", required = false) MultipartFile file) {
        log.info("上传图片：{}", file);
        // 判断文件是否为空
        if (file.isEmpty()) {
            return Result.error("上传图片为空");
        }
        // 获取传过来的文件名字
        String originalFileName = file.getOriginalFilename();
        // 为了防止重名， 获取系统时间戳+后缀名
        String fileName = System.currentTimeMillis() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 设置保存地址
        // 后台保存
        File dest = new File(AVATAR_STORAGE_ADDRESS + fileName);
        // 判断文件夹是否存在
        if (!dest.getParentFile().exists()) {
            // 如果不存在就创建一个
            dest.getParentFile().mkdirs();
        }

        try {
            // 后台上传，写入磁盘
            file.transferTo(dest);
            // 写入数据库 （规定了只能自己修改自己的图片 管理员无法修改图片）
            Long userId = ThreadBaseContext.getCurrentId();
            // 先查询
            User user = userService.getUserById(userId);
            // 得到路径
            String avatar = user.getAvatar();
            // 构建新的图片路径
            user = User.builder()
                    .id(userId)
                    .avatar(fileName)
                    .build();
            // 如果有图片
            if (!"".equals(avatar)) {
                // 获取存在的图片并删除
                new File(AVATAR_STORAGE_ADDRESS + avatar).delete();
            }
            // 然后添加
            userService.update(user);
            return Result.success(fileName, "文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传文件失败");
        }

    }

    @PostMapping("/uploadGoods/{id}")
    public Result<String> uploadGoods(@RequestBody @RequestParam(value = "file") MultipartFile file, @PathVariable String id) {
        log.info("上传图片：{} {}", file, id);
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            return Result.error("上传图片为空");
        }
        // 获取传过来的文件名字
        String originalFileName = file.getOriginalFilename();
        // 为了防止重名， 获取系统时间戳+后缀名
        String fileName = System.currentTimeMillis() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 设置保存地址
        // 后台保存
        File dest = new File(GOODS_STORAGE_ADDRESS + fileName);
        // 判断文件夹是否存在
        if (!dest.getParentFile().exists()) {
            // 如果不存在就创建一个
            dest.getParentFile().mkdirs();
        }

        try {
            // 后台上传，写入磁盘
            file.transferTo(dest);
            // 强转
            Long goodsId = Long.valueOf(id);
            // 写入数据库
            GoodsPageQueryVO goodsPageQueryVO = goodsService.getGoodsInfo(goodsId);
            if (goodsPageQueryVO == null) {
                throw new GoodsException(MessageConstant.GOODS_IS_NOT_EXIST);
            }

            // 如果有商品
            // 判断它是否已经有图片
            String coverPic = goodsPageQueryVO.getCoverPic();
            if (coverPic != null && !"".equals(coverPic)) {
                // 获取存在的图片并删除
                new File(GOODS_STORAGE_ADDRESS + coverPic).delete();
            }
            GoodsPageQueryDTO goodsPageQueryDTO = new GoodsPageQueryDTO();
            BeanUtils.copyProperties(goodsPageQueryVO, goodsPageQueryDTO);
            goodsPageQueryDTO.setCoverPic(fileName);
            System.out.println(fileName);
            // 添加图片
            goodsService.updateGoods(goodsPageQueryDTO);
            return Result.success(fileName, "文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传文件失败");
        }

    }

    @PostMapping("/uploadStore")
    @Transactional
    public Result<String> uploadStore(@RequestParam(value = "file", required = false) MultipartFile file) {
        log.info("上传图片：{}", file);
        // 判断文件是否为空
        if (file.isEmpty()) {
            return Result.error("上传图片为空");
        }
        // 获取传过来的文件名字
        String originalFileName = file.getOriginalFilename();
        // 为了防止重名， 获取系统时间戳+后缀名
        String fileName = System.currentTimeMillis() + "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 设置保存地址
        // 后台保存
        File dest = new File(STORE_STORAGE_ADDRESS + fileName);
        // 判断文件夹是否存在
        if (!dest.getParentFile().exists()) {
            // 如果不存在就创建一个
            dest.getParentFile().mkdirs();
        }

        try {
            // 后台上传，写入磁盘
            file.transferTo(dest);
            // 写入数据库 （规定了只能自己修改自己的图片 管理员无法修改图片）
            Long userId = ThreadBaseContext.getCurrentId();
            ThreadBaseContext.removeCurrentId();
            // 查询用户的商店
            Store store = storeService.findStoreByUserId(userId);
            if (store == null) {
                throw new StoreException(MessageConstant.STORE_IS_NOT_EXIST);
            }
            String logo = store.getLogo();
            if (!"".equals(logo)) {
                // 获取存在的图片并删除
                new File(STORE_STORAGE_ADDRESS + logo).delete();
            }
            Store updatingStore = Store.builder()
                    .id(store.getId())
                    .logo(fileName)
                    .build();
            // 添加
            storeService.updateStore(updatingStore);
            return Result.success(fileName, "文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传文件失败");
        }

    }
}
