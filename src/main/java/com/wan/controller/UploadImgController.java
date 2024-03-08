package com.wan.controller;

import com.wan.context.ThreadBaseContext;
import com.wan.entity.User;
import com.wan.result.Result;
import com.wan.server.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/img")
@Slf4j
public class UploadImgController {
    private final String STORAGE_ADDRESS =  "C:\\Users\\123\\Desktop\\MyOnlineShopping\\vue-onlineshopping\\src\\assets\\uploadAvatar\\";
    @Autowired
    private UserService userService;

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
        File dest = new File(STORAGE_ADDRESS + fileName);
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
                new File(STORAGE_ADDRESS + avatar).delete();
            }
            // 然后添加
            userService.update(user);
            return Result.success(fileName, "文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传文件失败");
        }

    }
}
