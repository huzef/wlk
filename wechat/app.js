//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        console.log("========================")
        console.log(res)
        this.login(res.code)
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    // wx.getSetting({
    //   lang: "zh_CN",
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       if (this.globalData.userInfo!=null && this.globalData.userInfo.isLaw==1){
    //         wx.navigateTo({
    //           url: '/pages/card/card'
    //         })
    //       } else if (this.globalData.userInfo != null && this.globalData.userInfo.isLaw == 2){
    //         wx.navigateTo({
    //           url: '/pages/repository/repository'
    //         })            

    //       }else{
    //         // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //         wx.getUserInfo({
    //           success: res => {
    //             // 可以将 res 发送给后台解码出 unionId
    //            // this.globalData.userInfo = res.userInfo

    //             // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //             // 所以此处加入 callback 以防止这种情况
    //             if (this.userInfoReadyCallback) {
    //               this.userInfoReadyCallback(res)
    //             }
    //           }
    //         })
    //       }

    //     }else{
    //       // 没有授权，重定向到 loading 启动页
    //       // wx.navigateTo({
    //       //   url: '/pages/welcome/welcome'
    //       // })
    //     }
    //   }
    // })
  },
  globalData: {
    userInfo: null,
    defaultCity: '深圳市',
    defaultCounty: '',
    baseService:"http://192.168.1.106:8087/wlk/",
    header: {
      Cookie: null
    }
  },
  login:function(code){
    const data = { code: code }
    var that = this;
    wx.request({
      url: this.globalData.baseService + "login",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log(res)

        that.globalData.userInfo=res.data.data
        console.log(that.globalData.userInfo);
        if (that.globalData.userInfo.isLaw && that.globalData.userInfo.isLaw==1){
          if (that.globalData.userInfo.firstName != null && that.globalData.userInfo.firstName!=""){
          wx.switchTab({ url: '/pages/card/card',});
          }else{
            wx.reLaunch({url:'/pages/addOrUpdateCard/addOrUpdateCard'})
          }
        } else if (that.globalData.userInfo.isLaw && that.globalData.userInfo.isLaw == 2){
          wx.switchTab({
            url: '/pages/repository/repository',
          });          
        }
        // console.log("login code secc");
        // console.log(res);
        // var jss = res.header['Set-Cookie'];
        // this.globalData.header.Cookie = jss.substring(0, jss.indexOf(";"));
        // //写到本地        
        // wx.setStorageSync('Cookie', this.globalData.header.Cookie);
        //wx.setStorageSync('CookieTime', new Date().getTime());
      }
    })

  }
})