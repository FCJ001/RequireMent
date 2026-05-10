package com.byd.msp.requirement.userGroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byd.msp.requirement.common.model.PageResult;
import com.byd.msp.requirement.userGroup.dto.AddUserDTO;
import com.byd.msp.requirement.userGroup.dto.UpdateUserDTO;
import com.byd.msp.requirement.userGroup.dto.UserQueryDTO;
import com.byd.msp.requirement.userGroup.entity.SysUser;
import com.byd.msp.requirement.userGroup.mapper.SysUserMapper;
import com.byd.msp.requirement.userGroup.service.SysUserService;
import com.byd.msp.requirement.userGroup.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public PageResult<UserVO> query(UserQueryDTO dto) {
        // 1. 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                // 如果传了username，模糊查询：WHERE username LIKE '%值%'
                .like(StringUtils.isNotBlank(dto.getUsername()), SysUser::getUsername, dto.getUsername())
                // 如果传了status，精确匹配：AND status = '值'
                .eq(StringUtils.isNotBlank(dto.getStatus()), SysUser::getStatus, dto.getStatus())
                // 按创建时间倒序：ORDER BY created_time DESC
                .orderByDesc(SysUser::getCreatedTime);

        // 2. 执行分页查询：SELECT * FROM sys_user WHERE ... LIMIT ? OFFSET ?
        Page<SysUser> page = this.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        // 3. Entity 转 VO：把数据库查出来的对象转成前端需要的格式
        List<UserVO> voList = page.getRecords().stream()
                .map(entity -> BeanUtil.copyProperties(entity, UserVO.class))
                .collect(Collectors.toList());

        // 4. 返回分页结果：数据列表 + 总条数
        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public void add(AddUserDTO dto) {
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        user.setStatus("ACTIVE");
        this.save(user);
    }

    @Override
    public void update(UpdateUserDTO dto) {
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        this.updateById(user);
    }

    @Override
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    public UserVO detail(String id) {
        SysUser user = this.getById(id);
        if (user == null) {
            return null;
        }
        return BeanUtil.copyProperties(user, UserVO.class);
    }
}
