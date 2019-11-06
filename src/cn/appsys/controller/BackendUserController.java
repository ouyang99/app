package cn.appsys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.BackendUser.BackendUserService;

@Controller
@RequestMapping("/backenduser")
public class BackendUserController {
	@Resource
	private BackendUserService backendUserService;

	@RequestMapping("dologin")
	public String login(@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userPassword", required = false) String userPassword, HttpSession session,
			Model model) {
		BackendUser user = backendUserService.getUser(userCode, userPassword);
		if (user != null) {
			session.setAttribute("userSession", user);
			return "/backend/main";
		} else {
			return "redirect:/backenduser/ma";
		}

	}

	@RequestMapping("logout")
	public String logou(HttpSession session) {
		session.removeAttribute("user");
		return "backendlogin";
	}

	@RequestMapping("ma")
	public String ma() {
		return "backendlogin";
	}

	@RequestMapping("list")
	public String list(@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) String queryStatus,
			@RequestParam(value = "queryFlatformId", required = false) String queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String queryCategoryLevel3,
			@RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
		int PageNo = 1; // 当前页数
		int PageSize = 5; // 每页显示的数量

		if (pageIndex != null) {
			PageNo = Integer.parseInt(pageIndex);
		}
		List<DataDictionary> statuslist = backendUserService.statuslist(); // 状态
		List<DataDictionary> flatform = backendUserService.flatform(); // 所属平台
		List<AppCategory> Categorylist1 = backendUserService.Categorylist1(); // 一级分类

		String SoftwareName = null; // App名称
		int appstatic = 0; // App状态
		int FlatformId = 0; // 所属平台
		int CategoryLevel1 = 0; // 一级分类
		int level2list = 0; // 两级分类
		int CategoryLevel3 = 0; // 三级分类

		if (querySoftwareName != null && querySoftwareName != "") {
			SoftwareName = querySoftwareName;
		}
		if (queryStatus != null && queryStatus != "") {
			appstatic = Integer.parseInt(queryStatus);
		}
		if (queryFlatformId != null && queryFlatformId != "") {
			FlatformId = Integer.parseInt(queryFlatformId);
		}
		if (queryCategoryLevel1 != null && queryCategoryLevel1 != "") {
			CategoryLevel1 = Integer.parseInt(queryCategoryLevel1);
		}
		if (queryCategoryLevel2 != null && queryCategoryLevel2 != "") {
			level2list = Integer.parseInt(queryCategoryLevel2);
		}
		if (queryCategoryLevel3 != null && queryCategoryLevel3 != "") {
			CategoryLevel3 = Integer.parseInt(queryCategoryLevel3);
		}

		int count = backendUserService.count(SoftwareName, appstatic, FlatformId, CategoryLevel1, level2list,
				CategoryLevel3); // app总数量

		int pageCuontindex = count / PageSize == 0 ? count / PageSize : count / PageSize + 1; // 总页数
		List<AppInfo> list = backendUserService.list(SoftwareName, appstatic, FlatformId, CategoryLevel1, level2list,
				CategoryLevel3, (PageNo - 1) * PageSize, PageSize);

		model.addAttribute("categoryLevel1List", Categorylist1);
		model.addAttribute("flatFormList", flatform);
		model.addAttribute("statusList", statuslist);
		model.addAttribute("currentPageNo", PageNo); // 当前页码
		model.addAttribute("totalPageCount", pageCuontindex); // 总页数
		model.addAttribute("totalCount", count); // 总数量
		model.addAttribute("appInfoList", list);
		model.addAttribute("queryStatus", appstatic);
		model.addAttribute("queryFlatformId", FlatformId);
		return "/backend/applist";
	}

	@RequestMapping("/categorylevellistajax")
	@ResponseBody
	public Object Ajaxcategorylevellist(@RequestParam(value = "pid", required = false) String pid) {
		System.err.println("==========================" + pid);
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist2 = backendUserService.Categorylist2(id); // 二级分类
		return JSONArray.toJSONString(Categorylist2);
	}

	@RequestMapping("/categorylevellistajax2")
	@ResponseBody
	public Object categorylevellistajax2(@RequestParam(value = "pid", required = false) String pid) {
		System.err.println("==========================" + pid);
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist3 = backendUserService.Categorylist3(id); // 三级分类
		return JSONArray.toJSONString(Categorylist3);
	}

	@RequestMapping("/stuctu")
	@ResponseBody
	public Object stuctu() {
		List<DataDictionary> flatform = backendUserService.flatform(); // 所属平台
		return JSONArray.toJSONString(flatform);
	}

	@RequestMapping("check")
	public String check(@RequestParam(value = "aid", required = false) String aid,
			@RequestParam(value = "vid", required = false) String vid, Model model) {
		AppInfo appinfo = backendUserService.viewapp(Integer.parseInt(aid));
		AppVersion appversion = backendUserService.selectversion(Integer.parseInt(vid));
		model.addAttribute("appInfo", appinfo);
		model.addAttribute("appVersion", appversion);
		return "/backend/appcheck";
	}

	@RequestMapping("checksave")
	public String checksave(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "status", required = false) String status) {
		System.err.println(id + "============" + status);
		int i = backendUserService.updatesh(Integer.parseInt(id), Integer.parseInt(status));
		if (i > 0) {
			System.err.println(123465798);
			return "redirect:/backenduser/list";
		}
		System.err.println(123456);
		return "/backend/appcheck";
	}

}
