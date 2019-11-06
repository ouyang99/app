package cn.appsys.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.BackendUser.BackendUserService;
import cn.appsys.service.DevUser.DevUserService;

@Controller
@RequestMapping("/dev")
public class DevUserController {
	@Resource
	private DevUserService devuserService;

	@RequestMapping("dologin")
	public String login(@RequestParam(value = "devCode", required = false) String userCode,
			@RequestParam(value = "devPassword", required = false) String userPassword, HttpSession session) {
		DevUser user = devuserService.getUser(userCode, userPassword);
		if (user != null) {
			session.setAttribute("devUserSession", user);
			return "/developer/main";
		}
		return "redirect:/dev/ma";
	}

	@RequestMapping("ma")
	public String ma() {
		return "devlogin";
	}

	@RequestMapping("logout")
	public String logou(HttpSession session) {
		session.removeAttribute("user");
		return "devlogin";
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
		List<DataDictionary> statuslist = devuserService.statuslist(); // 状态
		List<DataDictionary> flatform = devuserService.flatform(); // 所属平台
		List<AppCategory> Categorylist1 = devuserService.Categorylist1(); // 一级分类

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

		int count = devuserService.count(SoftwareName, appstatic, FlatformId, CategoryLevel1, level2list,
				CategoryLevel3); // app总数量

		int pageCuontindex = count / PageSize == 0 ? count / PageSize : count / PageSize + 1; // 总页数
		List<AppInfo> list = devuserService.list(SoftwareName, appstatic, FlatformId, CategoryLevel1, level2list,
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
		return "/developer/appinfolist";
	}

	@RequestMapping("/categorylevellistajax")
	@ResponseBody
	public Object Ajaxcategorylevellist(@RequestParam(value = "pid", required = false) String pid) {
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist2 = devuserService.Categorylist2(id); // 二级分类
		return JSONArray.toJSONString(Categorylist2);
	}

	@RequestMapping("/categorylevellistajax2")
	@ResponseBody
	public Object categorylevellistajax2(@RequestParam(value = "pid", required = false) String pid) {
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist3 = devuserService.Categorylist3(id); // 三级分类
		return JSONArray.toJSONString(Categorylist3);
	}

	@RequestMapping("/stuctu")
	@ResponseBody
	public Object stuctu() {
		List<DataDictionary> flatform = devuserService.flatform(); // 所属平台
		return JSONArray.toJSONString(flatform);
	}

	@RequestMapping("/categorylevellist1")
	@ResponseBody
	public Object categorylevellist1() {
		List<AppCategory> Categorylist1 = devuserService.Categorylist1(); // 一级分类
		return JSONArray.toJSONString(Categorylist1);
	}

	@RequestMapping("/categorylevellist2")
	@ResponseBody
	public Object categorylevellist2(@RequestParam(value = "pid", required = false) String pid) {
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist2 = devuserService.Categorylist2(id); // 二级分类
		return JSONArray.toJSONString(Categorylist2);
	}

	@RequestMapping("/categorylevellist3")
	@ResponseBody
	public Object categorylevellist3(@RequestParam(value = "pid", required = false) String pid) {
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist3 = devuserService.Categorylist3(id); // 三级分类
		return JSONArray.toJSONString(Categorylist3);
	}

	@RequestMapping("/apkexist")
	@ResponseBody
	public Object apkexist(@RequestParam(value = "APKName", required = false) String APKName) {
		AppInfo appinfo = devuserService.getAPK(APKName);
		HashMap<String, String> map = new HashMap<String, String>();
		if (appinfo == null) {
			map.put("APKName", "noexist");
		} else if (APKName == null || APKName == "") {
			map.put("APKName", "empty");
		} else {
			map.put("APKName", "exist");
		}
		return map;
	}

	@RequestMapping("/appinfoaddsave")
	public String appinfoaddsave(@RequestParam(value = "softwareName", required = false) String softwareName,
			@RequestParam(value = "APKName", required = false) String APKName,
			@RequestParam(value = "supportROM", required = false) String supportROM,
			@RequestParam(value = "interfaceLanguage", required = false) String interfaceLanguage,
			@RequestParam(value = "softwareSize", required = false) String softwareSize,
			@RequestParam(value = "downloads", required = false) String downloads,
			@RequestParam(value = "flatformId", required = false) String flatformId,
			@RequestParam(value = "categoryLevel1", required = false) String categoryLevel1,
			@RequestParam(value = "categoryLevel2", required = false) String categoryLevel2,
			@RequestParam(value = "categoryLevel3", required = false) String categoryLevel3,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "appInfo", required = false) String appInfo,
			@RequestParam(value = "a_logoPicPath", required = false) MultipartFile attach, HttpServletRequest request) {
		AppInfo appinfo = new AppInfo();
		appinfo.setSoftwareName(softwareName);
		appinfo.setAPKName(APKName);
		appinfo.setSupportROM(supportROM);
		appinfo.setInterfaceLanguage(interfaceLanguage);
		BigDecimal bi = new BigDecimal(softwareSize);
		appinfo.setSoftwareSize(bi);
		appinfo.setDownloads(Integer.parseInt(downloads));
		appinfo.setFlatformId(Integer.parseInt(flatformId));
		appinfo.setCategoryLevel1(Integer.parseInt(categoryLevel1));
		appinfo.setCategoryLevel2(Integer.parseInt(categoryLevel2));
		appinfo.setCategoryLevel3(Integer.parseInt(categoryLevel3));
		appinfo.setStatus(Integer.parseInt(status));
		appinfo.setAppInfo(appInfo);
		DevUser user = (DevUser) request.getSession().getAttribute("devUserSession");
		appinfo.setCreatedBy(user.getId());
		appinfo.setDevId(user.getId());
		appinfo.setCreationDate(new Date());
		String idPicPath = null;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			int filesize = 500000;
			if (attach.getSize() > filesize) {
				System.err.println("上传大小不能超过500KB");
			}
			String fileName = System.currentTimeMillis() + ".jgp";
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				attach.transferTo(targetFile);
				;
			} catch (Exception e) {
				e.printStackTrace();
			}
			idPicPath = path + File.separator + fileName;
		}
		appinfo.setLogoPicPath(idPicPath);

		int i = devuserService.Appinsert(appinfo);
		if (i > 0) {
			return "redirect:/dev/list";
		} else {
			return "appinfoadd";
		}

	}

	// 修改查询
	@RequestMapping("/appinfupdate")
	public String appinfomodify(@RequestParam(value = "id", required = false) String id, Model model) {
		int aid = Integer.parseInt(id);
		AppInfo appinfo = devuserService.selectid(aid);
		model.addAttribute("appInfo", appinfo);
		return "/developer/appinfomodify";
	}

	@RequestMapping("categoryLevel1")
	@ResponseBody
	public Object fenglei1(@RequestParam(value = "pid", required = false) String pid) {
		List<AppCategory> Categorylist1 = devuserService.Categorylist1(); // 一级分类
		System.err.println(Categorylist1.get(0).getCategoryName());
		return JSONArray.toJSONString(Categorylist1);
	}

	@RequestMapping("categoryLevel2")
	@ResponseBody
	public Object fenglei2(@RequestParam(value = "pid", required = false) String pid) {
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist2 = devuserService.Categorylist2(id); // 二级分类
		System.err.println(Categorylist2.get(0).getCategoryName());
		return JSONArray.toJSONString(Categorylist2);
	}

	@RequestMapping("categoryLevel3")
	@ResponseBody
	public Object fenglei3(@RequestParam(value = "pid", required = false) String pid) {
		int id = Integer.parseInt(pid);
		List<AppCategory> Categorylist3 = devuserService.Categorylist3(id); // 三级分类
		System.err.println(Categorylist3.get(0).getCategoryName());
		return JSONArray.toJSONString(Categorylist3);
	}

	@RequestMapping("stuctAjax")
	@ResponseBody
	public Object stuctAjax() {
		List<DataDictionary> flatform = devuserService.flatform(); // 所属平台
		return JSONArray.toJSONString(flatform);
	}

	// 修改
	@RequestMapping("/appinfupdata")
	public String appinfupdata(@RequestParam(value = "softwareName", required = false) String softwareName,
			@RequestParam(value = "APKName", required = false) String APKName,
			@RequestParam(value = "supportROM", required = false) String supportROM,
			@RequestParam(value = "interfaceLanguage", required = false) String interfaceLanguage,
			@RequestParam(value = "softwareSize", required = false) String softwareSize,
			@RequestParam(value = "downloads", required = false) String downloads,
			@RequestParam(value = "flatformId", required = false) String flatformId,
			@RequestParam(value = "categoryLevel1", required = false) String categoryLevel1,
			@RequestParam(value = "categoryLevel2", required = false) String categoryLevel2,
			@RequestParam(value = "categoryLevel3", required = false) String categoryLevel3,
			@RequestParam(value = "statusName", required = false) String statusName,
			@RequestParam(value = "appInfo", required = false) String appInfo,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "logoPicPath", required = false) String attach, HttpServletRequest request) {
		DataDictionary dataDictionary = devuserService.sele(statusName); // 查询状态ValueId值
		int valueid = dataDictionary.getValueId();
		AppInfo appinfo = new AppInfo();
		appinfo.setSoftwareName(softwareName);
		appinfo.setAPKName(APKName);
		appinfo.setSupportROM(supportROM);
		appinfo.setInterfaceLanguage(interfaceLanguage);
		BigDecimal bi = new BigDecimal(softwareSize);
		appinfo.setSoftwareSize(bi);
		appinfo.setDownloads(Integer.parseInt(downloads));
		appinfo.setFlatformId(Integer.parseInt(flatformId));
		appinfo.setCategoryLevel1(Integer.parseInt(categoryLevel1));
		appinfo.setCategoryLevel2(Integer.parseInt(categoryLevel2));
		appinfo.setCategoryLevel3(Integer.parseInt(categoryLevel3));
		appinfo.setStatus(valueid); // 状态iD
		appinfo.setAppInfo(appInfo);
		DevUser user = (DevUser) request.getSession().getAttribute("devUserSession");
		appinfo.setModifyBy(user.getId()); // 更新者
		appinfo.setModifyDate(new Date()); // 更新时间
		appinfo.setUpdateDate(new Date()); // 更新时间
		appinfo.setId(Integer.parseInt(id)); // id
		appinfo.setLogoPicPath(attach);// 图片

		int i = devuserService.updataid(appinfo);
		if (i > 0) {
			return "redirect:/dev/list";
		} else {
			return "/developer/appinfomodify";
		}

	}

	@RequestMapping("versionadd")
	public String versionadd(@RequestParam(value = "id", required = false) String id, Model model,
			HttpServletRequest request) {
		int appid = Integer.parseInt(id);
		List<AppVersion> versionlist = devuserService.versionlist(appid);
		model.addAttribute("appVersionList", versionlist);
		model.addAttribute("aa", id);
		return "/developer/appversionadd";
	}

	@RequestMapping("addversion")
	public String addversion(@RequestParam(value = "versionNo", required = false) String versionNo,
			@RequestParam(value = "b", required = false) String b,
			@RequestParam(value = "versionSize", required = false) String versionSize,
			@RequestParam(value = "publishStatus", required = false) String publishStatus,
			@RequestParam(value = "versionInfo", required = false) String versionInfo,
			@RequestParam(value = "a_downloadLink", required = false) MultipartFile attach, HttpServletRequest request,
			Model model) {
		AppVersion appversion = new AppVersion();
		appversion.setVersionNo(versionNo);
		appversion.setAppId(Integer.parseInt(b)); // AppId
		BigDecimal size = new BigDecimal(versionSize);
		appversion.setVersionSize(size);
		appversion.setPublishStatus(Integer.parseInt(publishStatus));
		System.err.println(Integer.parseInt(b));
		AppInfo appinfo = devuserService.seleAppInfo(Integer.parseInt(b));
		appversion.setApkFileName(appinfo.getAPKName() + "-" + versionNo);
		appversion.setVersionInfo(versionInfo);
		DevUser user = (DevUser) request.getSession().getAttribute("devUserSession");
		appversion.setCreatedBy(user.getId()); // 创建者
		appversion.setCreationDate(new Date()); // 创建时间

		String idPicPath = null;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			int filesize = 0;
			if (attach.getSize() < filesize) {
				System.err.println("上传大小不能小于过0KB");
			}
			String fileName = System.currentTimeMillis() + appinfo.getAPKName() + "-" + versionNo;
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				attach.transferTo(targetFile);
				;
			} catch (Exception e) {
				e.printStackTrace();
			}
			idPicPath = path + File.separator + fileName;
		}
		appversion.setDownloadLink(idPicPath);
		appversion.setApkLocPath(idPicPath);
		int i = devuserService.versionadd(appversion);
		if (i > 0) {
			devuserService.updateversion(Integer.parseInt(b), appversion.getId());
			return "redirect:/dev/list";
		} else {
			return "/developer/appversionadd";
		}

	}

	@RequestMapping("versionmodify")
	public String versionmodify(@RequestParam(value = "vid", required = false) String vid,
			@RequestParam(value = "aid", required = false) String aid, Model model) {
		List<AppVersion> vlist = devuserService.versionlist(Integer.parseInt(aid));
		AppVersion alist = devuserService.selectversion(Integer.parseInt(vid));
		model.addAttribute("appVersionList", vlist);
		model.addAttribute("appVersion", alist);
		return "/developer/appversionmodify";
	}

	@RequestMapping("appversionmodi")
	public String appversionmodi(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "appId", required = false) String appId,
			@RequestParam(value = "versionNo", required = false) String versionNo,
			@RequestParam(value = "versionSize", required = false) String versionSize,
			@RequestParam(value = "publishStatus", required = false) String publishStatus,
			@RequestParam(value = "attach", required = false) MultipartFile attach,
			@RequestParam(value = "apkLocPath", required = false) String apkLocPath,
			@RequestParam(value = "apkFileName", required = false) String apkFileName,
			@RequestParam(value = "versionInfo", required = false) String versionInfo, HttpServletRequest request) {
		AppVersion appversion = new AppVersion();
		appversion.setId(Integer.parseInt(id));
		appversion.setAppId(Integer.parseInt(appId));
		appversion.setVersionNo(versionNo);
		BigDecimal size = new BigDecimal(versionSize);
		appversion.setVersionSize(size);
		appversion.setPublishStatus(Integer.parseInt(publishStatus));
		appversion.setVersionInfo(versionInfo);
		DevUser user = (DevUser) request.getSession().getAttribute("devUserSession");
		appversion.setModifyBy(user.getId());
		appversion.setModifyDate(new Date());
		String idPicPath = null;
		String loadLink = null;
		if (!attach.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			int filesize = 0;
			if (attach.getSize() < filesize) {
				System.err.println("上传大小不能小于过0KB");
			}
			String fileName = System.currentTimeMillis() + ".apk";
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				attach.transferTo(targetFile);
				;
			} catch (Exception e) {
				e.printStackTrace();
			}
			idPicPath = path + File.separator + fileName;
			loadLink = File.separator + fileName;
		}

		AppInfo app = devuserService.selectid(Integer.parseInt(appId));
		appversion.setApkFileName(app.getAPKName() + "-" + versionNo + ".apk");
		appversion.setApkLocPath(idPicPath);
		appversion.setDownloadLink(loadLink);
		int i = devuserService.appmodify(appversion);
		if (i > 0) {
			return "redirect:/dev/list";
		} else {
			return "/developer/appversionmodify";
		}

	}

	@RequestMapping("delfile")
	@ResponseBody
	public Object delfile(@RequestParam(value = "id", required = false) String id) {
		System.err.println("=====================" + id);
		AppVersion appversion = new AppVersion();
		appversion.setId(Integer.parseInt(id));
		int i = devuserService.appvermodify(appversion);
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("result", "success");

		} else {
			map.put("result", "failed");
		}
		return JSONArray.toJSONString(map);
	}

	@RequestMapping("appview")
	public String appview(@RequestParam(value = "id", required = false) String id, Model model) {
		AppInfo appinfo = devuserService.viewapp(Integer.parseInt(id));
		model.addAttribute("appInfo", appinfo);
		List<AppVersion> list = devuserService.versionlist(Integer.parseInt(id));
		model.addAttribute("appVersionList", list);
		return "/developer/appinfoview";
	}

	@RequestMapping("delapp")
	@ResponseBody
	public Object delapp(@RequestParam(value = "id", required = false) String id) {
		int i = devuserService.delapp(Integer.parseInt(id));
		HashMap<String, String> map = new HashMap<String, String>();
		if (i > 0) {
			map.put("delResult", "true");
		} else {
			map.put("delResult", "false");
		}
		return JSONArray.toJSONString(map);
	}

	@RequestMapping("sxajax")
	@ResponseBody
	public Object sxajax(@RequestParam(value = "appId", required = false) String appId) {
		int id = Integer.parseInt(appId);
		AppInfo appinfo = devuserService.seleAppInfo(id);
		HashMap<String, String> map = new HashMap<String, String>();
		int i = 0;
		if (appinfo.getStatus() == 4) {
			i = devuserService.appajaxupdate(id, 5);
			map.put("resultMsg", "success");
		} else if (appinfo.getStatus() == 5) {
			map.put("resultMsg", "success");
			i = devuserService.appajaxupdate(id, 4);
		}

		if (i > 0) {
			map.put("errorCode", "0");
		}

		return JSONArray.toJSONString(map);
	}

}
