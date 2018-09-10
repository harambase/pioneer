package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.RoleDao;
import com.harambase.pioneer.server.dao.repository.RoleRepository;
import com.harambase.pioneer.server.pojo.base.Role;
import com.harambase.pioneer.server.service.RoleServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServerServiceImpl implements RoleServerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RoleRepository roleRepository;

    private final RoleDao roleDao;

    @Autowired
    public RoleServerServiceImpl(RoleRepository roleRepository, RoleDao roleDao) {
        this.roleDao = roleDao;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResultMap get(Integer roleId) {
        try {
            Role role = roleRepository.findByRoleId(roleId);
            return role != null ? ReturnMsgUtil.success(role) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap list(String search, String order, String orderColumn) {
        ResultMap message = new ResultMap();
        try {

            List<Role> roleList = roleDao.getByMapPageSearchOrdered(search, order, orderColumn);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg("1"));
            page.setPageSize(PageUtil.getLimit("10"));
            page.setTotalRows(roleList.size());

            message.setData(roleList);
            message.put("page", page);
            message.setMsg(SystemConst.SUCCESS.getMsg());
            message.setCode(SystemConst.SUCCESS.getCode());
            return message;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
