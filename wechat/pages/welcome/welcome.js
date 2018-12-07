// pages/welcome/welcome.js
//获取应用实例
var app = getApp();
Page({
  data: {
    remind: '加载中',
    angle: 0,
    userInfo: {},
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    hasUserInfo: false
  },
  goToIndex: function () {
    wx.switchTab({
      url: '/pages/card/card',
    });
  },
  goToRepository: function () {
    wx.switchTab({
      url: '/pages/repository/repository',
    });
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
//     var i=Math.floor(Math.random() * 100 + 1);
// if(i==1){
//   this.goToRepository();
// } else { this.goToIndex();}
  },
  onShow: function () {

    // let that = this
    // let userInfo = wx.getStorageSync('userInfo')
    // if (!userInfo) {
    //   wx.getUserInfo({
    //     success: res => {
    //       app.globalData.userInfo = res.userInfo
    //       this.setData({
    //         userInfo: res.userInfo,
    //       })
    //     }
    //   })
    // } else {
    //   that.setData({
    //     userInfo: userInfo
    //   })
    // }
  },
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
    wx.onAccelerometerChange(function (res) {
      var angle = -(res.x * 30).toFixed(1);
      if (angle > 14) {
        angle = 14;
      } else if (angle < -14) {
        angle = -14;
      }
      if (that.data.angle !== angle) {
        that.setData({
          angle: angle
        });
      }
    });
  },
  getUserInfo: function (e) {
    console.log(e)
    if (e.currentTarget.dataset.id==1){
      this.goToIndex()
    }else{
      this.goToRepository()
    }
    console.log(e.detail.userInfo)
    const data={};
    data.id= app.globalData.userInfo.id; 
    data.nickName = e.detail.userInfo.nickName;
    data.photoFilePath = e.detail.userInfo.avatarUrl;
    data.gender = e.detail.userInfo.gender;
    data.isLaw = e.currentTarget.dataset.id;
     var that=this;
    wx.request({
      url: app.globalData.baseService + "user/update",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log(res)
        that.globalData.userInfo = res.data.data
        console.log(that.globalData.userInfo);

      }
    })
    // this.setData({
    //   userInfo: e.detail.userInfo,
    //   hasUserInfo: true
    // })
  }
});