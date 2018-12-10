const config = require('../../utils/config.js');
var app = getApp();
Page({
  data: {
    current: '1',
    tab1: true,
    name: '',
    angle: 0,
    userInfo: {},
    user:{},
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    spinShow: true,
    isLike:0,
    isCollection:0
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
    if (detail.key == 1) {
      this.setData({
        tab1: true,
        tab2: false
      })
    } else if (detail.key== 2) {
      this.setData({
        tab1: false,
        tab2: true
      })
    }
  },
  onLoad: function () {
    
  },
  onShow: function () {
    this.getOne()
    let that = this
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
  getLocation:function(e){
    console.log(this.data.user.address)
    wx.request({
      url: `https://apis.map.qq.com/ws/geocoder/v1/?address=${this.data.user.address}&key=${config.key}`,
      success: res => {
         console.log(res)
        wx.openLocation({
          longitude: Number(res.data.result.location.lng),
          latitude: Number(res.data.result.location.lat),
          name: this.data.user.organization,
          address: this.data.user.address
        })
        // console.log(res.data.result.ad_info.city+res.data.result.ad_info.adcode);
        // that.selectCounty();
      }
    })
  },
  addContact:function(){
    wx.addPhoneContact({
      firstName:this.data.user.firstName,
      mobilePhoneNumber: this.data.user.mobilePhoneNumber,
      success() {
        // wx.showToast({
        //   title: '联系人创建成功'
        // })
      },
      fail() {
        // wx.showToast({
        //   title: '联系人创建失败'
        // })
      }
    })
  },
  makePhoneCall() {
    wx.makePhoneCall({
      phoneNumber: this.data.user.mobilePhoneNumber,
      success() {
        console.log('成功拨打电话')
      }
    })
  },
  onShareAppMessage() {
    const url1 = '/pages/cardDetails/cardDetails?id=' + this.data.user.id + '&&isshare=1';
    console.log("分享路径" + url1)
    return {
      withShareTicket:true,
      title: '我是' + this.data.user.firstName + '律师，为您提供法律服务，这是我的名片，望惠存！',
      path: url1
      }
  },
  getOne: function () {
    const data={};
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/getOne",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log("用户信息")
        console.log(res)
        if(res.data.code==200){
          if (res.data.data==null||res.data.data.firstName == null || res.data.data.firstName == ""){
            wx.redirectTo({ url: '/pages/addOrUpdateCard/addOrUpdateCard' })
            return false;
          }
          wx.setNavigationBarTitle({
            title: res.data.data.firstName+'的名片'
          })
          that.setData({
            user:res.data.data,
            field: res.data.data.field?res.data.data.field.split(','):"",
            spinShow:false
          })
        }else{
          app.loginCode();
        }

      }
    })

  },
  copy:function(e){
    var self = this;
    wx.setClipboardData({
      data: self.data.user.wechatNumber,
      success: function (res) {
        // self.setData({copyTip:true}),
        // wx.showModal({
        //   title: '提示',
        //   content: '复制成功',
        //   success: function (res) {
        //     if (res.confirm) {
        //       console.log('确定')
        //     } else if (res.cancel) {
        //       console.log('取消')
        //     }
        //   }
        // })
      }
    });
  },
  isLike: function(){
    const data={};
    data.userId = this.data.user.id;
    var that = this;
    wx.request({
      url: app.globalData.baseService + "like/add",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if(res.data.code==200){
          that.setData({
            isLike:1
          })
          wx.showToast({
            title: res.data.msg
          })
        } else {
          app.loginCode()
        }



      }
    })
  },
  isCollection:function(){
    const data = {};
    data.userId = this.data.user.id;
    var that = this;
    wx.request({
      url: app.globalData.baseService + "collection/add",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log(res)

        if (res.data.code == 200) {
          that.setData({
            isCollection:1
          })
          wx.showToast({
            title: res.data.msg
          })
        } else {
          app.loginCode()
        }

      }
    })
  }
});