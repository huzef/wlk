//app.js
import wxValidate from 'utils/wxValidate'
App({
  wxValidate: (rules, messages) => new wxValidate(rules, messages),
  onLaunch: function(e) {

    this.userLogin(); //初始化登录
    this.globalData.isshare = e.query.isshare ? e.query.isshare : 0
    // 获取用户信息
    wx.getSetting({
      lang: "zh_CN",
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          //已经授权
        } else {
          // 没有授权，重定向到 loading 启动页
          // wx.navigateTo({
          //   url: '/pages/welcome/welcome'
          // })
        }
      }
    })
  },
  globalData: {
    isshare: 0, //是否是分享页面
    isPath: 0, //用来定位路径是否是在缓存失效回去的页面
    userInfo: null,
    selectCity: false,
    defaultCity: '深圳市',
    defaultCounty: '',
    // baseService:"http://192.168.1.106:8087/wlk/",
    //baseService: "http://192.168.0.44:8087/wlk/",
    baseService: "https://wechat.innovationbaba.com/wlk/",
  },
  login: function(code) {
    const data = {
      code: code
    }

    var that = this;
    wx.request({
      url: this.globalData.baseService + "login",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "POST",
      data: data,
      complete: function(res) {
        if (res.data.code == 200) {

          that.globalData.userInfo = res.data.data
          console.log(that.globalData.userInfo);
          wx.setStorageSync("cookie", res.header['Set-Cookie'])
          wx.setStorageSync('loginUser', res.data.data);
          that.reloadTo();
        } else {
          that.loginCode();
        }
      }
    })

  },
  // 登录
  userLogin: function() {
    console.log("登录了===============")
    wx.login({
      success: res => {
        this.login(res.code)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  reloadTo: function() {
    if (this.globalData.isshare == 1) {
      console.log("分享页面" + this.globalData.isshare)
      return false;
    }
    if (this.globalData.isPath == 1) {
      wx.reLaunch({
        url: this.getCurrentPageUrl()
      })
      return false;
    }
    if (this.globalData.userInfo.isLaw && this.globalData.userInfo.isLaw == 1) {
      if (this.globalData.userInfo.firstName != null && this.globalData.userInfo.firstName != "") {
        wx.switchTab({
          url: '/pages/card/card',
        });
      } else {
        wx.reLaunch({
          url: '/pages/addOrUpdateCard/addOrUpdateCard'
        })
      }
    } else if (this.globalData.userInfo.isLaw && this.globalData.userInfo.isLaw == 2) {
      wx.switchTab({
        url: '/pages/repository/repository',
      });
    }
  },
  //定时任务，每隔20分钟刷新一次
  refresh: function() {
    var that = this;
    setInterval(that.userLogin, 20 * 60 * 1000);
  },
  //全局状态同一处理强制刷新
  loginCode: function(e) {
    console.log("缓存失效==获取页面路径")
    this.globalData.isPath = 1;
    console.log(this.getCurrentPageUrl())
    wx.clearStorageSync()
    this.userLogin();
  },
  //获取当前页面 登录失效后重新登录要返回的页面
  getCurrentPageUrl: function() {
    const pages = getCurrentPages()
    const currentPage = pages[pages.length - 1]
    const url = `/${currentPage.route}`
    return url
  }

})