package org.ydc.system.system.controller.Organization;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.ydc.config.R;
import org.ydc.system.system.controller.AbstractController;
import org.ydc.system.system.entity.Organization;
import org.ydc.system.system.entity.SysRole;
import org.ydc.system.system.service.OrganizationService;
import org.ydc.system.system.shiro.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *@RequestBody接收前端传过来的多个参数
 * @author testjava
 * @since 2020-08-31
 */
@Controller
@RequestMapping("/system/organization")
public class OrganizationController extends AbstractController {

    @Autowired
    OrganizationService organizationService;
    private String prefix = "/system/organization";
    //创建公司
    @PostMapping("addMainCompany")
    @RequiresPermissions("system:organ:add")
    public R addMainCompany(@RequestBody Organization Organization) {
        boolean save = organizationService.save(Organization);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

     //查询所有公司
    @ResponseBody
    @PostMapping("getAllMainCompanyList")
    @RequiresPermissions("system:organ:view")
    public List<Organization> getAllMainCompany(HttpServletRequest request,@RequestBody(required = false) Organization organization){
      try {
          QueryWrapper<Organization> wrapper = new QueryWrapper<>();
           wrapper.orderByAsc("seq");
          if(!StringUtils.isEmpty(organization.getName()))
              wrapper.eq("name",organization.getName());
          if(!StringUtils.isEmpty(organization.getAddress()))
               wrapper.eq("address",organization.getAddress());

          List<Organization> list = organizationService.list(wrapper);
          return list;
      }catch (Exception e){
          logError(request, "[查询公司失败]", e);
          return null;
      }
    }

     @GetMapping("getAllMainCompany")
     @RequiresPermissions("system:organ:view")
     public String getAllMainCompany(){
        return "/organization/organizationShow";
     }


//    //查询所有公司分页
//    @ResponseBody
//    @PostMapping("getAllMainCompanyPage/{current}/{limit}")
//    @RequiresPermissions("system:organ:view")
//    public R getAllMainCompanyPage(HttpServletRequest request, @PathVariable Long current, @PathVariable Long limit,@RequestBody(required = false) Organization organization){
//        try {
//            Page<Organization> sysRolePage= new Page<>(current,limit);
//            QueryWrapper<Organization> wrapper = new QueryWrapper<>();
//            wrapper.orderByAsc("seq");
//            if(!StringUtils.isEmpty(organization.getName()))
//                wrapper.eq("name",organization.getName());
//            if(!StringUtils.isEmpty(organization.getAddress()))
//                wrapper.eq("address",organization.getAddress());
//            organizationService.page(sysRolePage,wrapper);
//
//            long total = sysRolePage.getTotal();//总记录数
//            List<Organization> records = sysRolePage.getRecords();//数据
//            return R.ok().data("ListOrganization",records).data("total",total);
//        }catch (Exception e){
//            logError(request, "[查询公司失败]", e);
//            return null;
//        }
//    }


    //根据id查询公司信息
    @ResponseBody
    @GetMapping("getByIdOrganization/{id}")
    @RequiresPermissions("system:organ:view")
    public Organization getByIdOrganization(HttpServletRequest request, @PathVariable String id){
      try {
          Organization byId = organizationService.getById(id);
          return byId;
      }catch (Exception e){
          logError(request, "[查询公司信息失败]", e);
          return null;
      }
    }

    //根据id修改公司信息
    @PostMapping("updateOrganization")
    @RequiresPermissions("system:organ:update")
    public ModelAndView updateOrganization(@RequestBody Organization organization,HttpServletRequest request){
        try{
            System.out.println(organization+"************************************&&&&&&&&&&&&&&&&&&&&&&&&^^^^^^^^^^^^^^^^^%%%%%%%%%%");
            organizationService.updateById(organization);
            return new ModelAndView("redirect:"+prefix+"/getAllMainCompany");//修改公司信息成功了
        }catch (Exception e) {
            logError(request, "[修改公司失败]", e);
            return new ModelAndView("redirect:"+prefix+"/getAllMainCompany");//修改公司信息失败了
        }
    }

    //============递归删除组织机构==================================

    @GetMapping("deleteMainCompany/{id}")
    @RequiresPermissions("system:organ:delete")
    public ModelAndView  deleteMainCompany(@PathVariable Long id,HttpServletRequest request){
      try {
          organizationService.removeChildById(id);
          return new ModelAndView("redirect:"+prefix+"/getAllMainCompany");//递归删除成功了了
      }catch (Exception e){
          logError(request, "[修改公司失败]", e);
          return new ModelAndView("redirect:"+prefix+"/getAllMainCompany");//递归删除失败了
      }
    }

    //查询所有公司
    @ResponseBody
    @GetMapping("AjaxgetAllMainCompany")
    @RequiresPermissions("system:organ:view")
    public List<Organization> AjaxgetAllMainCompany(Model model,HttpServletRequest request){
        try {
            QueryWrapper<Organization> wrapper = new QueryWrapper<>();
            wrapper
                    .orderByAsc("seq");
            List<Organization> list = organizationService.list(wrapper);
            return list;
        }catch (Exception e){
            logError(request, "[查询公司失败]", e);
            return null;
        }
    }

    @ResponseBody
    @GetMapping("delete/{id}")
    @RequiresPermissions("system:organ:delete")
    public int  delete(@PathVariable Long id,HttpServletRequest request){
        return organizationService.GODeletePid(id);
    }
}

